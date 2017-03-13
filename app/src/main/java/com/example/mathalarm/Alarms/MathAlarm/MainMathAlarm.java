package com.example.mathalarm.Alarms.MathAlarm;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.mathalarm.R;
import com.example.mathalarm.SQLDataBase.AlarmDBAdapter;

import java.util.Calendar;

public class MainMathAlarm extends AppCompatActivity {

    public static final String ALARM_SNOOZE_ACTION = "alarm_snooze";
    public static final String ALARM_DISMISS_ACTION = "alarm_dismiss";
    public static final String ALARM_START_NEW = "alarm_start_new";
    public static final String TAG = "AlarmProcess";

    private TableRow trChangeTime, trChooseMusic, trChangeMessage, trChangeComplexity, trDeepSleepMusic;
    private TextView  tvChooseMusic, tvChangeAlarmComplexity, tvDeepSleepMusic;
    private EditText etGetAlarmMessageText;
    //values for setting on alarm
    private int pickedHour, pickedMinute =0;
    private boolean timePickerStatus = false;
    //values for setting music
    private int selectedMusic = 0;
    private String[] musicList;
    private boolean mpIsPlaying = false;
    private MediaPlayer mediaPlayer;

    private String alarmMessageText = "Good morning";

    private String[] alarmComplexityList;
    private int selectedComplexityLevel = 0;

    private int selectedDeepSleepMusic = 0;
    private String[] deepSleepMusicList;

    private  AlarmDBAdapter alarmDBAdapter ;
    private long rowIdToUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_math_alarm);

        tvChooseMusic = (TextView) findViewById(R.id.tvChooseMusic);
        tvChangeAlarmComplexity = (TextView) findViewById(R.id.tvChangeComplexity);
        tvDeepSleepMusic = (TextView) findViewById(R.id.tvDeepSleepMusic);

        trChangeTime = (TableRow) findViewById(R.id.trChangeTime);
        trChooseMusic = (TableRow) findViewById(R.id.trChooseMusic);
        trChangeMessage = (TableRow) findViewById(R.id.trChangeMessage);
        trChangeComplexity = (TableRow) findViewById(R.id.trChangeComplexity);
        trDeepSleepMusic = (TableRow) findViewById(R.id.trDeepSleepMusic);

        deepSleepMusicList = getResources().getStringArray(R.array.deepSleepMusic_list);
        musicList = getResources().getStringArray(R.array.music_list);
        alarmComplexityList = getResources().getStringArray(R.array.alarm_complexity_list);

        openAlarmDB();
        if(UpdateRow_Method()){
            timePickerStatus=true;
            TvTimePickerSetTextMethod(pickedMinute,pickedHour);
            tvChooseMusic.setText(musicList[selectedMusic]);
            tvAlarmMessageTextSetTextMethod(alarmMessageText);
            tvChangeAlarmComplexity.setText(alarmComplexityList[selectedComplexityLevel]);
            tvDeepSleepMusic.setText(deepSleepMusicList[selectedDeepSleepMusic]);
            TableRowChangeColorMethod(R.color.green_correct_choose);

            //if user touched some of items in SetAlarmFromHistory open selected alertDialog
            int whichAlertDialogOpen;
            if((whichAlertDialogOpen=getIntent().getIntExtra("alertDialogKey",-1)) != -1){
                OpenAlertDialogToUpdate(whichAlertDialogOpen);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        TableRowChangeColorMethod(R.color.black);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarmDBAdapter.closeAlarmDB();
    }

    /**
     * @param view public methods for onClick events
     */
    public void bSetAlarmTime_ClickMethod(View view) {
        //show timePickerDialog
        TimePickerMethod().show();
    }

    public void bChooseMusic_ClickMethod(View view) {
        //show alertDialog with music list
        AlarmMusic_AlertDialogBuilder().create().show();
    }

    public void bAlarmMessageText_OnClickListener(View view) {
        AlarmMessageText_AlertDialogBuilder().create().show();
    }

    public void bChangeAlarmComplexity_ClickMethod(View view) {
        AlarmComplexity_AlertDialogBuilder().create().show();
    }

    public void bDeepSleepMusic_ClickMethod(View view) {
        AlarmDeepSleepMusic_AlertDialogBuilder().create().show();
    }

    public void bCreateMathAlarm_ClickMethod(View view) {
        final CharSequence[] alarmSettingsItems = {pickedHour + " : " + pickedMinute, musicList[selectedMusic],
                alarmComplexityList[selectedComplexityLevel],"\"" + alarmMessageText + "\"", deepSleepMusicList[selectedDeepSleepMusic]};
        //Check if the time was Picked by user
        if (timePickerStatus) {
            AlertDialog.Builder alertDialogAlarmPreview = new AlertDialog.Builder(this,R.style.alertDialogMainMathAlarmStyle);
            alertDialogAlarmPreview.setTitle("Preview")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Class for setting alarm with specific alarm data
                            MathAlarmPreview mathAlarmPreview = new MathAlarmPreview(MainMathAlarm.this, pickedHour, pickedMinute, selectedMusic,
                                    selectedComplexityLevel, alarmMessageText, selectedDeepSleepMusic);
                            mathAlarmPreview.ConfirmAlarmPreview_Method();

                            //every new set alarm will be saved in SQL DB
                            alarmDBAdapter.GetDataToSaveAlarmDB(pickedHour,pickedMinute,selectedMusic,alarmMessageText,selectedComplexityLevel,selectedDeepSleepMusic);
                            if(getIntent().getBooleanExtra("updateKey",false)) {
                                Boolean updateState = alarmDBAdapter.UpdateRowAlarmDB(rowIdToUpdate);
                                Log.i(TAG,"MainMathAlarm  UpdateRow id=" + updateState);
                            }
                            else
                                alarmDBAdapter.InsertRowAlarmDB();

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

                            OpenAlertDialogToUpdate(which);
                        }
                    }).create().show();
        } else
            Toast.makeText(MainMathAlarm.this, "To create alarm please first set the alarm time", Toast.LENGTH_LONG).show();
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

                TvTimePickerSetTextMethod(minute,hourOfDay);
                pickedHour = hourOfDay;
                pickedMinute = minute;
                timePickerStatus = true;

                trChangeTime.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
            }
        }, currentHour, currentMinute, false);
        return timePickerDialog;
    }

    private AlertDialog.Builder AlarmComplexity_AlertDialogBuilder() {
        AlertDialog.Builder alarmComplexity_alertDialog = new AlertDialog.Builder(this,R.style.alertDialogMainMathAlarmStyle);
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
                        trChangeComplexity.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
                    }
                });
        return alarmComplexity_alertDialog;
    }

    private AlertDialog.Builder AlarmMessageText_AlertDialogBuilder() {
        AlertDialog.Builder adBuilder_GetMessageForAlarm = new AlertDialog.Builder(this,R.style.alertDialogMainMathAlarmStyle);

        RelativeLayout relativeLayoutView = (RelativeLayout) getLayoutInflater().inflate(R.layout.custom_layout_bchange_text, null);
        etGetAlarmMessageText = (EditText) relativeLayoutView.findViewById(R.id.etGetAlarmMessageText);

        adBuilder_GetMessageForAlarm.setView(relativeLayoutView)
                .setTitle("Write a message for yourself")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alarmMessageText = etGetAlarmMessageText.getText().toString();
                        tvAlarmMessageTextSetTextMethod(alarmMessageText);
                    }
                });
        return adBuilder_GetMessageForAlarm;
    }

    private AlertDialog.Builder AlarmMusic_AlertDialogBuilder() {
        AlertDialog.Builder alertDialog_ChooseMusic = new AlertDialog.Builder(this,R.style.alertDialogMainMathAlarmStyle);
        alertDialog_ChooseMusic.setTitle("Choose music for Alarm")
                .setSingleChoiceItems(R.array.music_list, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                                        Log.i(MainMathAlarm.TAG, "MathAlarmService Error occurred while playing audio.");
                                        mp.stop();
                                        mp.release();
                                        mediaPlayer = null;
                                        return true;
                                    }
                                });
                                mediaPlayer.start();
                            } else {
                                mpIsPlaying = true;
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer = MediaPlayer.create(MainMathAlarm.this, musicPackedName);
                                mediaPlayer.start();
                            }
                        } catch (Exception e) {
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
                        trChooseMusic.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
                    } else {
                        int defaultMusicNumber = 0;
                        tvChooseMusic.setText(musicList[defaultMusicNumber]);
                    }
                } catch (Exception e) {
                    Log.i(MainMathAlarm.TAG, "MathAlarmService " + " AlarmStartPlayingMusic error" + e.getMessage());
                }
            }
        });
        return alertDialog_ChooseMusic;
    }

    private AlertDialog.Builder AlarmDeepSleepMusic_AlertDialogBuilder() {
        AlertDialog.Builder alertDialog_DeepSleepMusic = new AlertDialog.Builder(this,R.style.alertDialogMainMathAlarmStyle);
        alertDialog_DeepSleepMusic.setTitle("Choose music for Alarm")
                .setSingleChoiceItems(R.array.deepSleepMusic_list, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDeepSleepMusic=which;
                    }
                }).setPositiveButton("Good", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvDeepSleepMusic.setText(deepSleepMusicList[selectedDeepSleepMusic]);
                trDeepSleepMusic.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
            }
        });
        return alertDialog_DeepSleepMusic;
    }


    private void openAlarmDB(){
         alarmDBAdapter = new AlarmDBAdapter(MainMathAlarm.this);
            alarmDBAdapter.OpenAlarmDB();
    }

    private boolean UpdateRow_Method(){
        Intent intent = getIntent();
        if(intent.getBooleanExtra("updateKey",false)){
            rowIdToUpdate = intent.getLongExtra("rowIdToUpdate",0);
            pickedHour = intent.getIntExtra("hour",0);
            pickedMinute = intent.getIntExtra("minute",0);
            selectedMusic = intent.getIntExtra("ringtoneName",0);
            selectedComplexityLevel = intent.getIntExtra("complexityLevel",0);
            selectedDeepSleepMusic = intent.getIntExtra("deepSleepMusicList",0);
            alarmMessageText = intent.getStringExtra("messageText");
            return true;
        }
        return false;
    }

    private void TvTimePickerSetTextMethod(int minute,int hourOfDay){
        TextView tvPickedTime;
        tvPickedTime = (TextView) findViewById(R.id.tvSetTime);
        //convert minute from 2:5 to  2:05
        // change text of tvPickedTime to show picked time
        if (minute < 10) {
            tvPickedTime.setText("hour: " + hourOfDay + " minute: " + "0" + String.valueOf(minute));
        } else {
            tvPickedTime.setText("hour: " + hourOfDay + " minute: " + minute);
        }
    }

    private void tvAlarmMessageTextSetTextMethod(String text){
        TextView tvAlarmMessageText = (TextView) findViewById(R.id.tvChangeMessage);
        String defaultAlarmMessageText =  "Good morning" ;
        if (!text.equals("")) {
            if (text.length() <= 15)
                tvAlarmMessageText.setText(text);
            else
                tvAlarmMessageText.setText(text.substring(0, 12) + "...");
            trChangeMessage.setBackgroundColor(getResources().getColor(R.color.green_correct_choose));
        } else {
            tvAlarmMessageText.setText("\"" + defaultAlarmMessageText + "\"");
            alarmMessageText = defaultAlarmMessageText;
        }
    }

    private void TableRowChangeColorMethod(int color){
        trChangeComplexity.setBackgroundColor(getResources().getColor(color));
        trChangeTime.setBackgroundColor(getResources().getColor(color));
        trChangeMessage.setBackgroundColor(getResources().getColor(color));
        trChooseMusic.setBackgroundColor(getResources().getColor(color));
        trDeepSleepMusic.setBackgroundColor(getResources().getColor(color));
    }

    private void OpenAlertDialogToUpdate(int itemSelected){
        switch (itemSelected) {
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
            case 4:
                AlarmDeepSleepMusic_AlertDialogBuilder().create().show();
                break;
            default:
                Log.i(TAG,"MainMathAlarm OpenAlertDialogToUpdate, error 5th argument");
        }
    }}

