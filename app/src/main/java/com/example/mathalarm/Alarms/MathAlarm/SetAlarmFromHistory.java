package com.example.mathalarm.Alarms.MathAlarm;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.mathalarm.R;
import com.example.mathalarm.SQLDataBase.AlarmDBAdapter;
import com.example.mathalarm.SQLDataBase.AlarmDBValues;

public class SetAlarmFromHistory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AlarmDBAdapter alarmDBAdapter;
    private ListView lvAllSetAlarms;

    private ImageButton ibDeleteChosenRow,ibHistoryDeleteAllAlarms;

    private Cursor cursor;
    private String[] alarmComplexityList,musicList,deepSleepMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_from_history);
        ibDeleteChosenRow = (ImageButton) findViewById(R.id.ibDeleteChosenRow);
        ibHistoryDeleteAllAlarms = (ImageButton) findViewById(R.id.ibHistoryDeleteAllAlarms);

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
    private void ReplaceCursorDataToValues(long id) {
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

    private void AlarmDataPreviewAlertDialog_Method(final long id){
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
                .setNegativeButton("Back", null)
                .setNeutralButton("UpDate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentUpdateRow = UpdateRowStartMainMathAlarm(id);
                        startActivity(intentUpdateRow);
                    }
                })

        .setItems(alarmSettingsItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentUpdateRow = UpdateRowStartMainMathAlarm(id);
                         switch (which) {
                            case 0:
                                intentUpdateRow.putExtra("alertDialogKey",0);
                                break;
                             case 1:
                                intentUpdateRow.putExtra("alertDialogKey",1);
                                break;
                            case 2:
                                intentUpdateRow.putExtra("alertDialogKey",2);
                                break;
                            case 3:
                                intentUpdateRow.putExtra("alertDialogKey",3);
                                break;
                            case 4:
                                intentUpdateRow.putExtra("alertDialogKey",4);
                                break;
                        }
                        startActivity(intentUpdateRow);
                    }
                }).create().show();
    }

    private Intent UpdateRowStartMainMathAlarm(long rowIdToUpdate){
        Intent intentUpdateRow = new Intent(SetAlarmFromHistory.this,MainMathAlarm.class);
        intentUpdateRow.putExtra("updateKey",true);
        intentUpdateRow.putExtra("rowIdToUpdate",rowIdToUpdate);
        intentUpdateRow.putExtra("hour",hour);
        intentUpdateRow.putExtra("minute",minute);
        intentUpdateRow.putExtra("ringtoneName",ringtoneName);
        intentUpdateRow.putExtra("complexityLevel",complexityLevel);
        intentUpdateRow.putExtra("messageText",messageText);
        intentUpdateRow.putExtra("deepSleepMusicList",deepSleepMusicList);
        return intentUpdateRow;
    }






    public void imDeleteChosenRow_OnClickListener(View view) {
        HideAllViewsMethod(true);
        lvAllSetAlarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteChosenRow(id);
                HideAllViewsMethod(false);
                lvAllSetAlarms.setOnItemClickListener(SetAlarmFromHistory.this);
            }
        });
    }
    private void DeleteChosenRow(long id){
        alarmDBAdapter.DeleteRowAlarmDB(id);
        Toast.makeText(this, "row - " +id + " deleted", Toast.LENGTH_SHORT).show();
        PopulateListViewAlarmDB();
    }
    private void HideAllViewsMethod(boolean state){
        state = !state;
        ibHistoryDeleteAllAlarms.setEnabled(state);
        ibDeleteChosenRow.setEnabled(state);
        if(!state)
            lvAllSetAlarms.setBackgroundColor(getResources().getColor(R.color.colorListViewBackgroundDeleteRow));
        else
            lvAllSetAlarms.setBackgroundColor(getResources().getColor(R.color.colorListViewBackground));
        }

    public void imHistoryDeleteAllAlarms_OnClickMethod(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmFromHistory.this,R.style.alertDialogMainMathAlarmStyle);
        builder.setTitle("Checkup")
                .setMessage("Are you sure, you want to delete all the records")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SetAlarmFromHistory.this, "all records deleted", Toast.LENGTH_SHORT).show();
                        alarmDBAdapter.DeleteAllRowsAlarmDB();
                        PopulateListViewAlarmDB();
                    }
                })
                .setNegativeButton("Back",null).create().show();
    }

    private void openAlarmDB(){
        alarmDBAdapter = new AlarmDBAdapter(SetAlarmFromHistory.this);
        alarmDBAdapter.OpenAlarmDB();
    }
}