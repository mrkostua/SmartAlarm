package com.example.mathalarm.Alarms.MathAlarm;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathalarm.CountsTimeToAlarmStart;
import com.example.mathalarm.R;

import java.util.Calendar;

public class MathAlarmPreview extends AppCompatActivity
{
    private int pickedHour, pickedMinute;

    private int selectedMusic;

    private String alarmMessageText;

    private int alarmComplexityLevel;
    private int hoursToAlarmBoom, minutesToAlarmBoom;
    private int currentHour , currentMinute;

    private Calendar calendar;
    private static final String TAG = "AlarmProcess";
    private boolean OnOffAlarm_Remember = false;

    private AlarmManager alarmManager;
    private Intent alarmReceiverIntent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG,"MathAlarmPreview "+"onCreate");
        setContentView(R.layout.activity_math_alarm_preview);

        //get values pickedMinute and pickedHour from MainMathAlarm
        Intent intentGetValuesFrom_MathAlarm = getIntent();
        pickedHour = intentGetValuesFrom_MathAlarm.getIntExtra("pickedHour", 0);
        pickedMinute = intentGetValuesFrom_MathAlarm.getExtras().getInt("pickedMinute", 0);
        selectedMusic = intentGetValuesFrom_MathAlarm.getExtras().getInt("selectedMusic",0);
        String defaultAlarmMessageText = "\"" +"Good morning sir" +"\"";
        alarmMessageText = intentGetValuesFrom_MathAlarm.getExtras().getString("alarmMessageText",defaultAlarmMessageText);
        alarmComplexityLevel = intentGetValuesFrom_MathAlarm.getExtras().getInt("selectedComplexityLevel",0);

        //refresh an instant of calendar to get the exact hour
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
         currentHour = calendar.get(Calendar.HOUR_OF_DAY);
         currentMinute = calendar.get(Calendar.MINUTE);

        DisplayAllViews();
    }

    //grab a partial wake lock when the device goes into the background (which is done in the onPause method)
    @Override
     protected void onPause() {
        super.onPause();
        Log.i(TAG,"MathAlarmPreview " + "onPause");
        Intent wakeLockIntent = new Intent(getBaseContext(),WakeLockService.class);
        wakeLockIntent.putExtra("wakeKey",true);
        startService(wakeLockIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"MathAlarmPreview "+"onDestroy");
    }

    private void DisplayAllViews() {
        // Method that shows calculate - How much time left until alarm start
        CountsTimeToAlarmStart countsTimeToAlarmStart = new CountsTimeToAlarmStart();
        countsTimeToAlarmStart.HowMuchTimeToStart(currentHour, currentMinute, pickedHour, pickedMinute);
        hoursToAlarmBoom = countsTimeToAlarmStart.getResultHours();
        minutesToAlarmBoom = countsTimeToAlarmStart.getResultMinutes();

        tvTimeToAlarmBoom_TextEditorMethod();
        tvAlarmTimeInformation_TextEditorMethod();
        tvMusicName_TextEditorMethod();
        tvPreviewAlarmMessageText_TextEditorMethod();
        tvPReviewSelectedAlarmComplexity_TextEditorMethod();
    }
    //method thar changes the text in the TextView tvActions
        private void tvAlarmTimeInformation_TextEditorMethod() {
        String sMinute = String.valueOf(pickedMinute);
        //convert minute from 2:5 to  2:05
        if (pickedMinute < 10)
            sMinute = "0" + String.valueOf(pickedMinute);

        TextView tvAlarmTimeInformation = (TextView) findViewById(R.id.tvAlarmTimeInformation);
        assert tvAlarmTimeInformation != null;
        tvAlarmTimeInformation.setText("Alarm ON " + pickedHour + ":" + sMinute);
    }

        private void tvTimeToAlarmBoom_TextEditorMethod() {
        TextView tvTimeToAlarmBoom = (TextView) findViewById(R.id.tvTimeToAlarmBoom);
        tvTimeToAlarmBoom.setText("Until boom "
                + hoursToAlarmBoom + " : " + minutesToAlarmBoom);
    }

        private void tvMusicName_TextEditorMethod() {
        String[] musicList = getResources().getStringArray(R.array.music_list);

        TextView tvMusicInformation = (TextView) findViewById(R.id.tvMusicInformation);
        tvMusicInformation.setText(musicList[selectedMusic]);
    }

        private void tvPreviewAlarmMessageText_TextEditorMethod() {
        TextView tvPreviewAlarmMessageText = (TextView) findViewById(R.id.tvPreviewAlarmMessageText);
        tvPreviewAlarmMessageText.setText(alarmMessageText);
    }

    private void tvPReviewSelectedAlarmComplexity_TextEditorMethod() {
            TextView tvPreviewComplexityLevel = (TextView) findViewById(R.id.tvPreviewComplexityLevel);

            String[] complexityList = getResources().getStringArray(R.array.alarm_complexity_list);
            tvPreviewComplexityLevel.setText("Level: " +complexityList[alarmComplexityLevel]);
        }

    //OnClick Method for Button - confirm (set Alarm on picked hour and minute)
    public void MathAlarmButtonOnClickListener(View v) {
        OnOffAlarm_Remember =true;
        //set the alarm for pickedHour and pickedMinute
        AlarmOnMethod(pickedHour, pickedMinute);
    }
    /**
     * @param hour   - current hour picked on the TimePicker
     * @param minute - current hour minute on the TimePicker
     */
    private void AlarmOnMethod(int hour, int minute) {
        Log.i(TAG, "MathAlarmPreview "+"AlarmOnMethod start");
        //create an Intent to the  Alarm_Receiver class
        alarmReceiverIntent = new Intent(MathAlarmPreview.this,Alarm_Receiver.class);

        //initialize alarmManager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        /**duplicate in the OnCreate Method (but it is necessary to refresh an instant of calendar
         * and when setting alarm on choose right type of alarm(what is based on the current and
         * picked time)
         */
         calendar = Calendar.getInstance();
         calendar.setTimeInMillis(System.currentTimeMillis());
         currentHour = calendar.get(Calendar.HOUR_OF_DAY);
         currentMinute = calendar.get(Calendar.MINUTE);

        //setting calendar instance with the hour and minute that we picked on the timerPicker
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Toast Toast_timeLeftToAlarmStart = Toast.makeText(MathAlarmPreview.this, "   Until boom    "
                + hoursToAlarmBoom + " hours :" + minutesToAlarmBoom + "  minutes", Toast.LENGTH_LONG);
        Toast_timeLeftToAlarmStart.setGravity(Gravity.TOP | Gravity.START, 0, 0);
        Toast_timeLeftToAlarmStart.show();

        // sending the condition of alarm if true - alarm on , if false - alarm off
        alarmReceiverIntent.putExtra("alarmCondition", true);

        //sending the type of alarm 2(math)
        alarmReceiverIntent.putExtra("Alarm name", 2)
        .putExtra("selectedMusic",selectedMusic)
        .putExtra("alarmMessageText",alarmMessageText)
        .putExtra("alarmComplexityLevel",alarmComplexityLevel);


        //pendingIntent that delays the intent until the specified calendar time
        pendingIntent = PendingIntent.getBroadcast(MathAlarmPreview.this, 0, alarmReceiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // If the hour picked in the TimePicker longer than hour of Current time or equal
        //and if a minute is longer or equal than the  current minute (because hour may be same , but the minute less )
        if (hour > currentHour)
        {
            Log.i(TAG, "MathAlarmPreview "+"h current: " + currentHour + " alarm hour: " + hour + "  Today");
            Log.i(TAG, "MathAlarmPreview "+"min current: " + currentMinute + " alarm min: " + minute + "  Today");
            //set the alarm manager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        //All other cases  alarmManager.setInexactRepeating  AlarmManager.INTERVAL_DAY
        if (hour < currentHour) {
            Log.i(TAG, "MathAlarmPreview "+"h current: " + currentHour + " alarm hour: " + hour + "Next Day");
            Log.i(TAG, "MathAlarmPreview "+"min current: " + currentMinute + " alarm min: " + minute + "  Next Day");

            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        if (hour == currentHour) {
            if (minute < currentMinute) {
                Log.i(TAG, "MathAlarmPreview "+"h current: " + currentHour + " alarm hour: " + hour + " Next Day ");
                Log.i(TAG, "MathAlarmPreview "+"min current: " + currentMinute + " alarm min: " + minute + "  Next Day");

                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + 1);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                Log.i(TAG, "MathAlarmPreview "+"h current: " + currentHour + " alarm hour: " + hour + "  Today");
                Log.i(TAG, "MathAlarmPreview "+"min current: " + currentMinute + " alarm min: " + minute + "  Today");
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }

    //Alarm Off private method sending pendingIntent to receiver (to stop creating service)
    public void MathAlarmOff_Method() {
        Log.i(TAG,"Alarm is :" + OnOffAlarm_Remember);
        if(OnOffAlarm_Remember)
        {
            // sending the condition of alarm if true - alarm on , if false - alarm off
            alarmReceiverIntent.putExtra("Alarm condition", false);
            alarmReceiverIntent.putExtra("Alarm name", 2);
            sendBroadcast(alarmReceiverIntent);
            //Cancel the alarm
            alarmManager.cancel(pendingIntent);
        }
    }

}

