package com.example.mathalarm.Alarms.MathAlarm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.mathalarm.R;
import com.example.mathalarm.SQLDataBase.AlarmDBValues;

/**
 * @author Konstantyn
 * Created by Администратор on 13.03.2017.
 */

 class CursorAdapterOverrider extends CursorAdapter {
    private LayoutInflater layoutInflater;
     CursorAdapterOverrider(Context context, Cursor c) {
        super(context, c, 0);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.custom_items_set_alarm_from_history,parent,false);
    }

    private TextView tvHistoryAlarmName;
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
         TextView tvHistoryAlarmHour,tvHistoryAlarmMinute;
         int hour,minute;
         String name;

         tvHistoryAlarmHour = (TextView) view.findViewById(R.id.tvHistoryAlarmHour);
         tvHistoryAlarmMinute = (TextView) view.findViewById(R.id.tvHistoryAlarmMinute);
         tvHistoryAlarmName = (TextView) view.findViewById(R.id.tvHistoryAlarmName);

         name = "\"" + cursor.getString(cursor.getColumnIndexOrThrow(AlarmDBValues.COLUMN_MESSAGE_TEXT)) + "\"";
          hour = cursor.getInt(cursor.getColumnIndexOrThrow(AlarmDBValues.COLUMN_HOUR));
         minute =cursor.getInt(cursor.getColumnIndexOrThrow(AlarmDBValues.COLUMN_MINUTE));
        if(minute<10)
            tvHistoryAlarmMinute.setText(": "+"0"+minute);
        else
            tvHistoryAlarmMinute.setText(": "+minute+"");
        tvAlarmMessageTextSetTextMethod(name);

        String sHour = String.valueOf(hour)+" ";
        tvHistoryAlarmHour.setText(sHour);
    }

    private void tvAlarmMessageTextSetTextMethod(String text){
        if (!text.equals("")) {
            if (text.length() <= 14)
                tvHistoryAlarmName.setText(text);
            else
                tvHistoryAlarmName.setText(text.substring(0, 10) + ".."+"\"");
        }
    }
}
