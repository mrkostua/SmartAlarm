package com.example.mathalarm.Alarms.MathAlarm;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.mathalarm.R;

import java.io.IOException;
import java.util.Calendar;

public class MainMathAlarm extends AppCompatActivity {

    public static final String ALARM_SNOOZE_ACTION = "alarm_snooze";
    public static final String ALARM_DISMISS_ACTION = "alarm_dismiss";
    public static final String ALARM_START_NEW = "alarm_start_new";
    public static final String TAG = "AlarmProcess";

   private Button b1Line,b2Line,b3Line,b4Line;
   private TextView tvPickedTime,tvChooseMusic, tvAlarmMessageText, tvChangeAlarmComplexity;
   private EditText etGetAlarmMessageText;
//values for setting on alarm
private int pickedHour,pickedMinute;
private boolean timePickerStatus =false;
//values for setting music
private int selectedMusic=0;
private String[] musicList ;
private boolean mpIsPlaying = false;
private MediaPlayer mediaPlayer;

private String alarmMessageText ="\"" +"Good morning sir" +"\"";

private String[] alarmComplexityList ;
    private int selectedComplexityLevel =0;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_math_alarm);
        musicList =  getResources().getStringArray(R.array.music_list);
        alarmComplexityList = getResources().getStringArray(R.array.alarm_complexity_list);
    }

    /**
     * @param view public methods for onClick events
     */
        public void bSetAlarmTime_ClickMethod(View view) {
    timePickerStatus=false;
    tvPickedTime = (TextView) findViewById(R.id.tvSetTime);
    b1Line = (Button) findViewById(R.id.b1Line);
    //show timePickerDialog
    TimePickerMethod().show();
}

        public void bChooseMusic_ClickMethod (View view) {
        tvChooseMusic = (TextView) findViewById(R.id.tvChooseMusic);
        b2Line = (Button) findViewById(R.id.b2Line);
            //show alertDialog with music list
            AlarmMusic_AlertDialogBuilder().create().show();
    }

        public void bAlarmMessageText_OnClickListener(View view) {
        b3Line = (Button) findViewById(R.id.b3Line);
        tvAlarmMessageText = (TextView) findViewById(R.id.tvChangeMessage);

            AlarmMessageText_AlertDialogBuilder().create().show();
    }

        public void bChangeAlarmComplexity_ClickMethod(View view) {
            tvChangeAlarmComplexity = (TextView) findViewById(R.id.tvChangeComplexity);
            b4Line = (Button) findViewById(R.id.b4Line);

            AlarmComplexity_AlertDialogBuilder().create().show();
        }

        public void bCreateMathAlarm_ClickMethod(View view) {
        final CharSequence[] alarmSettingsItems = {pickedHour +" : " + pickedMinute, musicList[selectedMusic],
                alarmComplexityList[selectedComplexityLevel],alarmMessageText};
        //Check if the time was Picked by user
        if(timePickerStatus) {
            AlertDialog.Builder alertDialogAlarmPreview = new AlertDialog.Builder(this);
            alertDialogAlarmPreview.setTitle("Preview")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MathAlarmPreview mathAlarmPreview = new MathAlarmPreview(MainMathAlarm.this,pickedHour,pickedMinute,selectedMusic,
                                    selectedComplexityLevel,alarmMessageText);
                            mathAlarmPreview.ConfirmAlarmPreview_Method();
                        }
                    })
                    .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setItems(alarmSettingsItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //close alertDialog to clean display area for another( to show another alertDialog)
                            dialog.dismiss();

                            switch (which) {
                                case 0:
                                    TimePickerMethod().show();
                                    break;
                                case 1:
                                    AlarmMusic_AlertDialogBuilder().create().show();
                                    break;
                                case 2:
                                    AlarmComplexity_AlertDialogBuilder().create().show();
                                    break;
                                case 3:
                                    AlarmMessageText_AlertDialogBuilder().create().show();
                                    break;
                                default:
                                    Toast.makeText(MainMathAlarm.this,"how it is possible",Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }).create().show();
        }
        else
            Toast.makeText(MainMathAlarm.this,"To create alarm please first set the alarm time",Toast.LENGTH_LONG).show();
    }
    /**
     * @return values of AlertDialog.Builder and TimePickerDialog
     */
    private TimePickerDialog TimePickerMethod() {
        //refresh an instant of calendar to get the exact hour
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //Get current time
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        //Launch TimePicker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //convert minute from 2:5 to  2:05
                // change text of tvPickedTime to show picked time
                if (minute < 10) {
                    tvPickedTime.setText("hour: " + hourOfDay + " minute: " + "0" + String.valueOf(minute));
                } else{
                    tvPickedTime.setText("hour: " + hourOfDay + " minute: " + minute);
                }
                pickedHour=hourOfDay;
                pickedMinute=minute;
                timePickerStatus=true;
                b1Line.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
            }
        },currentHour,currentMinute,false);
        return timePickerDialog;
    }

    private AlertDialog.Builder AlarmComplexity_AlertDialogBuilder(){
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
                });
        return alarmComplexity_alertDialog;
    }

    private AlertDialog.Builder AlarmMessageText_AlertDialogBuilder(){
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
                        String defaultAlarmMessageText = "\"" +"Good morning sir" +"\"";
                        if(!alarmMessageText.equals("")) {
                            if(alarmMessageText.length()<=15)
                                tvAlarmMessageText.setText(alarmMessageText);
                            else
                                tvAlarmMessageText.setText(alarmMessageText.substring(0,12)+"...");
                        } else {
                            tvAlarmMessageText.setText(alarmMessageText=defaultAlarmMessageText);
                            b3Line.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
                        }
                    }
                });
        return adBuilder_GetMessageForAlarm;
    }

    private AlertDialog.Builder AlarmMusic_AlertDialogBuilder() {
        AlertDialog.Builder alertDialog_ChooseMusic = new AlertDialog.Builder(MainMathAlarm.this);
        alertDialog_ChooseMusic.setTitle("Choose music for Alarm")
                .setSingleChoiceItems(R.array.music_list,0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        selectedMusic = which;
                        Context getContext = getApplicationContext();
                        int musicPackedName = getContext.getResources().getIdentifier(musicList[which], "raw",
                                getContext.getPackageName());
                        try {
                            if (!mpIsPlaying) {
                                mpIsPlaying = true;
                                mediaPlayer = MediaPlayer.create(MainMathAlarm.this, musicPackedName);
                                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                                    public boolean onError(MediaPlayer mp, int what, int extra) {
                                        Log.i(MainMathAlarm.TAG,"MathAlarmService Error occurred while playing audio.");
                                        mp.stop();
                                        mp.release();
                                        mediaPlayer = null;
                                        return true;
                                    }});
                                mediaPlayer.start();
                            } else {
                                mpIsPlaying = true;
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer = MediaPlayer.create(MainMathAlarm.this, musicPackedName);
                                mediaPlayer.start();
                            }
                        }catch (Exception e) {
                            Log.i(MainMathAlarm.TAG, "MathAlarmService " + " AlarmStartPlayingMusic error" + e.getMessage());
                        }
                    }
                }).setPositiveButton("Good", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            try {
                if (mpIsPlaying) {
                    mediaPlayer.stop();
                    //after reset(mediaPlayer need to be initialize again be setting data source...
                    mediaPlayer.reset();
                    tvChooseMusic.setText(musicList[selectedMusic]);
                } else {
                    int defaultMusicNumber = 0;
                    tvChooseMusic.setText(musicList[defaultMusicNumber]);
                }
                b2Line.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
            }catch (Exception e) {
                Log.i(MainMathAlarm.TAG, "MathAlarmService " + " AlarmStartPlayingMusic error" + e.getMessage());
            }
            }
        });

        return alertDialog_ChooseMusic;
    }
}
