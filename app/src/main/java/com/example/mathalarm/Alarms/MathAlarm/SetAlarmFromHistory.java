package com.example.mathalarm.Alarms.MathAlarm;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.mathalarm.R;
import com.example.mathalarm.SQLDataBase.AlarmDBAdapter;
import com.example.mathalarm.SQLDataBase.AlarmDBValues;

public class SetAlarmFromHistory extends AppCompatActivity {

    private AlarmDBAdapter alarmDBAdapter;
    private ListView lvAllSetAlarms;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_from_history);

        lvAllSetAlarms = (ListView) findViewById(R.id.lvAllSetAlarms);
        openAlarmDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //PopulateListViewAlarmDB();
    }

    private void PopulateListViewAlarmDB(){
        cursor = alarmDBAdapter.GetAllRowsAlarmDB();
        String [] fromColumns = {AlarmDBValues._ID,AlarmDBValues.COLUMN_HOUR,AlarmDBValues.COLUMN_MINUTE};
        int [] toViews = {R.id.tvHistoryAlarmName,R.id.tvHistoryAlarmHour,R.id.tvHistoryAlarmMinute};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(SetAlarmFromHistory.this,R.layout.custom_layout_bchange_text,cursor,fromColumns,toViews,0);
        lvAllSetAlarms.setAdapter(simpleCursorAdapter);
    }

    private void openAlarmDB(){
        alarmDBAdapter = new AlarmDBAdapter(SetAlarmFromHistory.this);
        alarmDBAdapter.OpenAlarmDB();
    }

    public void imHistoryDeleteAllAlarms_OnClickMethod(View view){
        alarmDBAdapter.DeleteAllRowsAlarmDB();
    }
}
