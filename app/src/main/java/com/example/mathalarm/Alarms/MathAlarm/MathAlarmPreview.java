package com.example.mathalarm.Alarms.MathAlarm;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathalarm.CountsTimeToAlarmStart;
import com.example.mathalarm.R;
import com.example.mathalarm.firstsScreens.MainActivity;

import java.util.Calendar;


public class MathAlarmPreview extends AppCompatActivity {
    private int pickedHour, pickedMinute;
    private int selectedMusic;
    private String alarmMessageText;
    private int alarmComplexityLevel;
    private int hoursToAlarmBoom, minutesToAlarmBoom;
    private int currentHour , currentMinute;
    private Calendar calendar;
    private static final String TAG = "AlarmProcess";

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"MathAlarmPreview "+"onDestroy");
    }
    private void ShowToast() {
        Toast Toast_timeLeftToAlarmStart = Toast.makeText(MathAlarmPreview.this, "   Until boom    "
                + hoursToAlarmBoom + " hours :" + minutesToAlarmBoom + "  minutes", Toast.LENGTH_LONG);
        Toast_timeLeftToAlarmStart.setGravity(Gravity.TOP | Gravity.START, 0, 0);
        Toast_timeLeftToAlarmStart.show();
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
        Log.i(TAG, "MathAlarmPreview "+"MathAlarmButtonOnClickListener start");

        final OnOffAlarm onOffAlarm = new OnOffAlarm(this,pickedHour,pickedMinute,alarmComplexityLevel,selectedMusic,true,alarmMessageText);
        onOffAlarm.SetNewAlarm();

        ShowToast();
        LastActions();
    }

    private void LastActions() {
        //start service with PartialWakeLock
        Intent wakeLockIntent = new Intent(getBaseContext(),WakeLockService.class);
        String time = pickedHour +" " + pickedMinute;
        wakeLockIntent.putExtra("alarmTimeKey",time);
        startService(wakeLockIntent);

        //after confirming alarm configurations, user will be moved to main activity
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}

