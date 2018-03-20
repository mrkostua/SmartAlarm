package com.mrkostua.mathalarm.alarms.mathAlarm.HistoryOfAlarms;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mrkostua.mathalarm.alarms.mathAlarm.MainAlarmActivity;
import com.mrkostua.mathalarm.R;
import com.mrkostua.mathalarm.data.sqlDB.AlarmDBAdapter;
import com.mrkostua.mathalarm.data.sqlDB.AlarmDBValues;
import com.mrkostua.mathalarm.ShowLogsOld;

public class SetAlarmFromHistory extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // TODO: 19.11.2017 delete alarms from history after 7 days without using. It depends if this func will be available in the release 2.0.
    private AlarmDBAdapter alarmDBAdapter;
    private ListView lvAllSetAlarms;

    private ImageButton ibDeleteChosenRow, ibHistoryDeleteAllAlarms;

    private Cursor cursor;
    private String[] alarmComplexityList, musicList, deepSleepMusicList;

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

    private void PopulateListViewAlarmDB() {
        if (ShowLogsOld.LOG_STATUS)
            ShowLogsOld.i("SetAlarmFromHistory " + "PopulateListViewAlarmDB");
        cursor = alarmDBAdapter.GetAllRowsAlarmDB();

        CursorAdapterOverrider cursorAdapterOverrider = new CursorAdapterOverrider(SetAlarmFromHistory.this, cursor);
        lvAllSetAlarms.setAdapter(cursorAdapterOverrider);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (ShowLogsOld.LOG_STATUS)
            ShowLogsOld.i("SetAlarmFromHistory " + "OnClickListViewMethod -" + id);
        AlarmDataPreviewAlertDialog_Method(id);
    }

    private int hour, minute, ringtoneName, complexityLevel, deepSleepMusicStatus = 0;
    private String messageText = "";

    private void ReplaceCursorDataToValues(long id) {
        cursor = alarmDBAdapter.GetRowAlarmDB(id);
        //if cursor is not empty get data from it
        if (cursor.moveToFirst()) {
            hour = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_HOUR));
            minute = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_MINUTE));
            ringtoneName = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_RINGTONE_NAME));
            complexityLevel = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_COMPLEXITY_LEVEL));
            deepSleepMusicStatus = cursor.getInt(cursor.getColumnIndex(AlarmDBValues.COLUMN_DEEP_SLEEP_MUSIC_STATUS));
            messageText = cursor.getString(cursor.getColumnIndex(AlarmDBValues.COLUMN_MESSAGE_TEXT));
        }
    }

    private void AlarmDataPreviewAlertDialog_Method(final long id) {
        ReplaceCursorDataToValues(id);
        final CharSequence[] alarmSettingsItems = {hour + " : " + minute, musicList[ringtoneName],
                alarmComplexityList[complexityLevel], "\"" + messageText + "\"", deepSleepMusicList[deepSleepMusicStatus]};

        AlertDialog.Builder alertDialogAlarmPreview = new AlertDialog.Builder(SetAlarmFromHistory.this,
                R.style.AlertDialogCustomStyle);
        alertDialogAlarmPreview.setTitle("Preview")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Class for setting alarm with specific alarm data
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
                        //0 timePicker , 1 alarmMusic, 2 alarmComplexity, 3 alarmMessage, 4 alarmDeepSleepMusic
                        intentUpdateRow.putExtra("alertDialogKey", which);
                        startActivity(intentUpdateRow);
                    }
                }).create().show();
    }

    private Intent UpdateRowStartMainMathAlarm(long rowIdToUpdate) {
        Intent intentUpdateRow = new Intent(SetAlarmFromHistory.this, MainAlarmActivity.class);
        intentUpdateRow.putExtra("updateKey", true);
        intentUpdateRow.putExtra("rowIdToUpdate", rowIdToUpdate);
        intentUpdateRow.putExtra("hour", hour);
        intentUpdateRow.putExtra("minute", minute);
        intentUpdateRow.putExtra("ringtoneName", ringtoneName);
        intentUpdateRow.putExtra("complexityLevel", complexityLevel);
        intentUpdateRow.putExtra("messageText", messageText);
        intentUpdateRow.putExtra("deepSleepMusicStatus", deepSleepMusicStatus);
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

    private void DeleteChosenRow(long id) {
        alarmDBAdapter.DeleteRowAlarmDB(id);
        Toast.makeText(this, "row - " + id + " deleted", Toast.LENGTH_SHORT).show();
        PopulateListViewAlarmDB();
    }

    private void HideAllViewsMethod(boolean state) {
        state = !state;
        ibHistoryDeleteAllAlarms.setEnabled(state);
        ibDeleteChosenRow.setEnabled(state);
        if (!state)
            lvAllSetAlarms.setBackgroundColor(getResources().getColor(R.color.colorListViewBackgroundDeleteRow));
        else
            lvAllSetAlarms.setBackgroundColor(getResources().getColor(R.color.colorListViewBackground));
    }

    public void imHistoryDeleteAllAlarms_OnClickMethod(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetAlarmFromHistory.this, R.style.AlertDialogCustomStyle);
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
                .setNegativeButton("Back", null).create().show();
    }

    private void openAlarmDB() {
        alarmDBAdapter = new AlarmDBAdapter(SetAlarmFromHistory.this);
        alarmDBAdapter.OpenAlarmDB();
    }
}
