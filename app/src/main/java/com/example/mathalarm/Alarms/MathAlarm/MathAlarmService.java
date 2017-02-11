package com.example.mathalarm.Alarms.MathAlarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.mathalarm.R;
import java.io.IOException;

public class MathAlarmService extends Service {
    private static final int ALARM_TIMEOUT_MILLISECONDS = 5 * 60 * 1000;
    private static final String TAG = "AlarmProcess";
    private int alarmComplexityLevel;
    private String musicResourceID;
    private String alarmMessageText;
    private MediaPlayer mediaPlayer;
    private int initialCallState;
    private TelephonyManager telephonyManager;

    private static final int KILLER_HANDLE_MESSAGE = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == KILLER_HANDLE_MESSAGE) {
                Log.i(TAG, "stopSelf, stopped music after 5 min without response");
                stopSelf();
            }
        }
    };
    //Phone state listener for situation where alarm triggered in time of call
    // so we will save and snooze our alarm
    private PhoneStateListener phoneStateListener = new PhoneStateListener()
    {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // The user might already be in a call when the alarm fires. When
            // we register onCallStateChanged, we get the initial in-call state
            // which kills the alarm. Check against the initial call state so
            // we don't kill the alarm during a call.
            if(state != TelephonyManager.CALL_STATE_IDLE && state !=initialCallState) {
                //method to save alarm
                // and than snooze
                stopSelf();
            }
        }
    };
    @Override
    public void onCreate() {
        Log.i(TAG, "MathAlarmService " + " onCreate");
        super.onCreate();
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Registers a listener object to receive notification of changes in specified telephony states.
        // @PhoneStateListener.LISTEN_CALL_STATE The telephony state(s) of interest to the listener
        telephonyManager.listen(
                phoneStateListener,
                phoneStateListener.LISTEN_CALL_STATE);
        //AlarmPartialWakeLock.acquireCpuWakeLock(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "MathAlarmService " + " onStartCommand");
        alarmComplexityLevel = intent.getExtras().getInt("alarmComplexityLevel", 0);

        Boolean alarmCondition = intent.getExtras().getBoolean("alarmCondition", false);

        String defaultAlarmMessageText = "\"" + "Good morning sir." + "\"";
        alarmMessageText = intent.getExtras().getString("alarmMessageText", defaultAlarmMessageText);

        int selectedMusic = intent.getExtras().getInt("selectedMusic", 0);
        String[] musicList = getResources().getStringArray(R.array.music_list);
        musicResourceID = getPackageName() + "/raw/" + musicList[selectedMusic];
        if (alarmCondition) {
            AlarmStartPlayingMusic();
            Start_DisplayAlarmActivity();
        }
        initialCallState = telephonyManager.getCallState();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MathAlarmService " + " onDestroy");
            //stop listen for incoming calls
            //To unregister a listener, pass the listener object and set the events argument to LISTEN_NONE (0).
            telephonyManager.listen(phoneStateListener,0);
            //AlarmPartialWakeLock.releaseCpuWakeLock();
        DisableServiceSilentKiller();
        AlarmStopPlayingMusic();
    }

    private void AlarmStartPlayingMusic() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //FLAG_SHOW_UI Show a toast containing the current volume.
            if (!audioManager.isVolumeFixed())
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM,
                        audioManager.getStreamVolume(AudioManager.STREAM_ALARM), AudioManager.FLAG_SHOW_UI);
        } else
                /*other method to increase volume for API < LOLLIPOP 21*/
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM,
                    audioManager.getStreamVolume(AudioManager.STREAM_ALARM),
                    AudioManager.FLAG_SHOW_UI);
        try {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.i(TAG,"MathAlarmService Error occurred while playing audio.");
                    mp.stop();
                    mp.release();
                    mediaPlayer = null;
                    return true;
                }});
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            //setDataSource must be placed in try/catch
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + musicResourceID));
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
            EnableServiceSilentKiller();
            Log.i(TAG, "MathAlarmService " + " AlarmStartPlayingMusic isPlaying() =" + mediaPlayer.isPlaying());
        } catch (IOException e) {
            Log.i(TAG, "MathAlarmService " + " AlarmStartPlayingMusic error" + e.getMessage());
        }
    }

    private void AlarmStopPlayingMusic() {
        if (mediaPlayer.isPlaying()) {
                try {
                    mediaPlayer.stop();
                    Log.i(TAG, "MathAlarmService " + "AlarmStopPlayingMusic " + "isPlaying=" + mediaPlayer.isPlaying());
                    mediaPlayer.release();
                    mediaPlayer = null;
                } catch (Exception e) {
                    Log.i(TAG, "MathAlarmService " + "AlarmStopPlayingMusic error " + e.getMessage());
                }
        } else
            Log.i(TAG, "MathAlarmService " + "AlarmStopPlayingMusic isPlaying()=" + mediaPlayer.isPlaying());
    }

    private void Start_DisplayAlarmActivity() {
        Intent dialogIntent = new Intent(this, DisplayAlarmActivity.class);
        dialogIntent.putExtra("AlarmCreatedKey", true)
                .putExtra("alarmMessageText", alarmMessageText)
                .putExtra("alarmComplexityLevel", alarmComplexityLevel)
                //If set, this activity will become the start of a new task on this history stack.
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //  .addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        startActivity(dialogIntent);
    }

    private void EnableServiceSilentKiller() {
        handler.sendMessageDelayed(handler.obtainMessage(KILLER_HANDLE_MESSAGE),
                ALARM_TIMEOUT_MILLISECONDS);
        Log.i(TAG, "MathAlarmService " + "EnableServiceSilentKiller");
    }

    private void DisableServiceSilentKiller() {
        handler.removeMessages(KILLER_HANDLE_MESSAGE);
        Log.i(TAG, "MathAlarmService " + "DisableServiceSilentKiller");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}