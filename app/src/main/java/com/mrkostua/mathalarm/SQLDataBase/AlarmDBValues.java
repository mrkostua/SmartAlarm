package com.mrkostua.mathalarm.SQLDataBase;

import android.provider.BaseColumns;

/** @author Konstantyn
 * Created by Администратор on 02.03.2017.
 */

public final class AlarmDBValues implements BaseColumns{

    //to prevent from instantiating
    private AlarmDBValues(){}
         static final String DATABASE_NAME = "dbAlarms";
         static final String TABLE_NAME = "alarms";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_MINUTE = "minute";
        public static final String COLUMN_RINGTONE_NAME = "ringtone_name";
        public static final String COLUMN_MESSAGE_TEXT = "message_text";
        public static final String COLUMN_COMPLEXITY_LEVEL = "complexity_level";
        public static final String COLUMN_DEEP_SLEEP_MUSIC_STATUS= "deep_sleep_music_status";
         static final int DATA_BASE_VERSION = 1;
         static final String[] ALL_COLUMNS_KEYS = {_ID,COLUMN_HOUR,COLUMN_MINUTE,COLUMN_RINGTONE_NAME,
            COLUMN_MESSAGE_TEXT,COLUMN_COMPLEXITY_LEVEL,COLUMN_DEEP_SLEEP_MUSIC_STATUS};
    }
