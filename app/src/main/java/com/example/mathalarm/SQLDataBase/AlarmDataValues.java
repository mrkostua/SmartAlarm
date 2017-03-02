package com.example.mathalarm.SQLDataBase;

import android.provider.BaseColumns;

/**
 * Created by Администратор on 02.03.2017.
 */

public final class AlarmDataValues implements BaseColumns{

    //to prevent from instantiating
    private AlarmDataValues(){}

        public static final String TABLE_NAME = "alarms";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_MINUTE = "minute";
        public static final String COLUMN_RINGTONE_NAME = "ringtone_name";
        public static final String COLUMN_MESSAGE_TEXT = "message_text";
        public static final String COLUMN_COMPLEXITY_LEVEL = "complexity_level";
        public static final String COLUMN_DEEP_SLEEP_MUSIC_STATUS= "deep_sleep_music_status";
        public static final int DATA_BASE_VERSION = 1;
        public static final String    COLUMN_ID ="_id";


    }
