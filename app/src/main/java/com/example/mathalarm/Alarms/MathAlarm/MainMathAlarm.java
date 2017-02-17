package com.example.mathalarm.Alarms.MathAlarm;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mathalarm.R;

import java.util.Calendar;

public class MainMathAlarm extends AppCompatActivity {
Button b1Line,b2Line,b3Line,b4Line;
TextView tvPickedTime,tvChooseMusic, tvAlarmMessageText, tvChangeAlarmComplexity;
EditText etGetAlarmMessageText;

    public static final String ALARM_SNOOZE_ACTION = "alarm_snooze";
    public static final String ALARM_SNOOZE_DISMISS = "alarm_dismiss";
    public static final String ALARM_START_NEW = "alarm_start_new";


private int currentHour, currentMinute;
private int pickedHour,pickedMinute;
private boolean timePickerStatus =false;

private int selectedMusic=0;
private String[] musicList;
private boolean mpIsPlaying = false;
private MediaPlayer mediaPlayer;
private int defaultMusicNumber =0;

private String alarmMessageText;

private String[] alarmComplexityList;
    private int selectedComplexityLevel =0;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_math_alarm);
    }

        public void bSetAlarmTime_ClickMethod(View view) {
    timePickerStatus=false;
    tvPickedTime = (TextView) findViewById(R.id.tvSetTime);
    b1Line = (Button) findViewById(R.id.b1Line);

    //refresh an instant of calendar to get the exact hour
    final Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    //Get current time
    currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    currentMinute = calendar.get(Calendar.MINUTE);

    //Launch TimePicker Dialog
    TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener()
    {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //convert minute from 2:5 to  2:05
            if (minute < 10) {
                tvPickedTime.setText("hour: " + hourOfDay + " minute: " + "0" + String.valueOf(minute));
                }
            else{
                tvPickedTime.setText("hour: " + hourOfDay + " minute: " + minute);
                }

            pickedHour=hourOfDay;
            pickedMinute=minute;
            timePickerStatus=true;
            b1Line.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
        }
    },currentHour,currentMinute,false);
    timePickerDialog.show();
}

        public void bChooseMusic_ClickMethod (View view) {
        musicList = getResources().getStringArray(R.array.music_list);
        tvChooseMusic = (TextView) findViewById(R.id.tvChooseMusic);
        b2Line = (Button) findViewById(R.id.b2Line);


        AlertDialog.Builder alertDialog_ChooseMusic = new AlertDialog.Builder(MainMathAlarm.this);
        alertDialog_ChooseMusic.setTitle("Choose music for Alarm")
                .setSingleChoiceItems(R.array.music_list,0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        selectedMusic = which;
                        Context getContext = getApplicationContext();
                        int musicPackedName = getContext.getResources().getIdentifier(musicList[which], "raw", getContext.getPackageName());

                        if(!mpIsPlaying) {
                            mpIsPlaying = true;
                            mediaPlayer = MediaPlayer.create(MainMathAlarm.this, musicPackedName);
                            mediaPlayer.start();
                        }
                        else {
                            mpIsPlaying = true;
                            mediaPlayer.stop();
                            mediaPlayer.reset();

                            mediaPlayer = MediaPlayer.create(MainMathAlarm.this, musicPackedName);
                            mediaPlayer.start();
                        }
                    }
                })
                .setPositiveButton("Good", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        if(mpIsPlaying) {
                            mediaPlayer.stop();
                            //after reset(mediaPlayer need to be initialize again be setting data source...
                            mediaPlayer.reset();
                            tvChooseMusic.setText(musicList[selectedMusic]);
                        }
                        else
                            tvChooseMusic.setText(musicList[defaultMusicNumber]);
                        b2Line.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));


                    }
                }).create().show();
    }

        public void bAlarmMessageText_OnClickListener(View view) {
        b3Line = (Button) findViewById(R.id.b3Line);
        tvAlarmMessageText = (TextView) findViewById(R.id.tvChangeMessage);
        AlertDialog.Builder adBuilder_GetMessageForAlarm = new AlertDialog.Builder(MainMathAlarm.this);

        RelativeLayout relativeLayoutView = (RelativeLayout) getLayoutInflater().inflate(R.layout.custom_layout_bchange_text,null);
        etGetAlarmMessageText = (EditText) relativeLayoutView.findViewById(R.id.etGetAlarmMessageText);

        adBuilder_GetMessageForAlarm.setView(relativeLayoutView)
                .setTitle("Write a message for yourself")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                         alarmMessageText = etGetAlarmMessageText.getText().toString();
                        String defaultAlarmMessageText = "Good morning sir";
                         if(!alarmMessageText.equals(""))
                         {
                             if(alarmMessageText.length()<=15)
                             tvAlarmMessageText.setText(alarmMessageText);
                             else
                                 tvAlarmMessageText.setText(alarmMessageText.substring(0,12)+"...");
                         } else {
                             tvAlarmMessageText.setText(defaultAlarmMessageText);
                        b3Line.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));

                         }
                    }
                }).create().show();
    }

        public void bChangeAlarmComplexity_ClickMethod(View view) {
            tvChangeAlarmComplexity = (TextView) findViewById(R.id.tvChangeComplexity);
            b4Line = (Button) findViewById(R.id.b4Line);
            alarmComplexityList = getResources().getStringArray(R.array.alarm_complexity_list);

            AlertDialog.Builder alarmComplexity_alertDialog = new AlertDialog.Builder(MainMathAlarm.this);
            alarmComplexity_alertDialog.setTitle("complexity of the task")
                    .setSingleChoiceItems(R.array.alarm_complexity_list, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            selectedComplexityLevel = which;
                        }
                    })
                    .setPositiveButton("Perfect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            tvChangeAlarmComplexity.setText(alarmComplexityList[selectedComplexityLevel]);
                            b4Line.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
                        }
                    }).create().show();
        }

            public void bCreateMathAlarm_ClickMethod(View view) {
            //Check if the time was Picked by user
            if(timePickerStatus)
            {
//                alarmMessageText = "\"" + alarmMessageText + "\"";
                Intent intent = new Intent(this, MathAlarmPreview.class);
                intent.putExtra("pickedHour", pickedHour)
                        .putExtra("pickedMinute", pickedMinute)
                        .putExtra("selectedMusic",selectedMusic)
                        .putExtra("alarmMessageText",alarmMessageText)
                        .putExtra("selectedComplexityLevel",selectedComplexityLevel);
                startActivity(intent);
            }
            else
                Toast.makeText(MainMathAlarm.this,"To create alarm please first set the alarm time",Toast.LENGTH_LONG).show();
        }
}
