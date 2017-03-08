package com.example.mathalarm.Alarms.MathAlarm;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.mathalarm.R;
import com.example.mathalarm.SQLDataBase.AlarmDBAdapter;
import com.example.mathalarm.SQLDataBase.AlarmDBValues;

public class SetAlarmFromHistory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AlarmDBAdapter alarmDBAdapter;
    private ListView lvAllSetAlarms;
    private Cursor cursor;
    private String[] alarmComplexityList,musicList,deepSleepMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_from_history);

        lvAllSetAlarms = (ListView) findViewById(R.id.lvAllSetAlarms);
        lvAllSetAlarms.setOnItemClickListener(SetAlarmFromHistory.this);
        openAlarmDB();

       musicList = getResources().getStringArray(R.array.music_list);
       alarmComplexityList = getResources().getStringArray(R.array.alarm_complexity_list);
        deepSleepMusicList = getResources().getStringArray(R.array.deepSleepMusic_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PopulateListViewAlarmDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarmDBAdapter.closeAlarmDB();
        cursor.close();
    }

    private void PopulateListViewAlarmDB(){
        Log.i(MainMathAlarm.TAG,"SetAlarmFromHistory "+"PopulateListViewAlarmDB");
        cursor = alarmDBAdapter.GetAllRowsAlarmDB();
        String [] fromColumns = {AlarmDBValues._ID,AlarmDBValues.COLUMN_HOUR,AlarmDBValues.COLUMN_MINUTE};
        int [] toViews = {R.id.tvHistoryAlarmName,R.id.tvHistoryAlarmHour,R.id.tvHistoryAlarmMinute};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(SetAlarmFromHistory.this,R.layout.custom_items_set_alarm_from_history,cursor,fromColumns,toViews,0);
        lvAllSetAlarms.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(MainMathAlarm.TAG,"SetAlarmFromHistory " + "OnClickListViewMethod -" + id);
        AlarmDataPreviewAlertDialog_Method(id);

    }
    
    private  int hour,minute,ringtoneName,complexityLevel,deepSleepMusicStatus= 0;
    private String messageText ="";
    private void ReplaceCursorDataToValues(long id){
         cursor =alarmDBAdapter.GetRowAlarmDB(id);

        //if cursor is not empty get data from it
        if(cursor.moveToFirst()) {
            hour = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_HOUR));
            minute = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_MINUTE));
            ringtoneName = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_RINGTONE_NAME));
            complexityLevel = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_COMPLEXITY_LEVEL));
            deepSleepMusicStatus = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_DEEP_SLEEP_MUSIC_STATUS));
            messageText = cursor.getString(cursor.getColumnIndex(AlarmDBValues.COLUMN_MESSAGE_TEXT));
        }
    }

    private void AlarmDataPreviewAlertDialog_Method(long id){

        ReplaceCursorDataToValues(id);
        final CharSequence[] alarmSettingsItems = {hour + " : " + minute, musicList[ringtoneName],
                alarmComplexityList[complexityLevel], messageText, deepSleepMusicList[deepSleepMusicStatus]};

        AlertDialog.Builder alertDialogAlarmPreview = new AlertDialog.Builder(SetAlarmFromHistory.this,
                R.style.alertDialogMainMathAlarmStyle);
        alertDialogAlarmPreview.setTitle("Preview")
        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Class for setting alarm with specific alarm data
                        MathAlarmPreview mathAlarmPreview = new MathAlarmPreview(SetAlarmFromHistory.this, hour, minute, ringtoneName,
                                complexityLevel, messageText, deepSleepMusicStatus);
                        mathAlarmPreview.ConfirmAlarmPreview_Method();
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("UpDate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })

        .setItems(alarmSettingsItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                TimePickerMethod().show();
//                                break;
//                            case 1:
//                                AlarmMusic_AlertDialogBuilder().create().show();
//                                break;
//                            case 2:
//                                AlarmComplexity_AlertDialogBuilder().create().show();
//                                break;
//                            case 3:
//                                AlarmMessageText_AlertDialogBuilder().create().show();
//                                break;
//                            case 4:
//                                AlarmDeepSleepMusic_AlertDialogBuilder().create().show();
//                                break;
//                            //close alertDialog to clean display area for another( to show another alertDialog)
//                            dialog.dismiss();
//                        }
                    }
                }).create().show();
    }


    private void openAlarmDB(){
        alarmDBAdapter = new AlarmDBAdapter(SetAlarmFromHistory.this);
        alarmDBAdapter.OpenAlarmDB();
    }

    public void imHistoryDeleteAllAlarms_OnClickMethod(View view){
        alarmDBAdapter.DeleteAllRowsAlarmDB();
    }
}
