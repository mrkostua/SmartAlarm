package com.example.mathalarm.SQLDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mathalarm.ShowLogs;

//manage database creation and version management.
public class AlarmDBAdapter {
    private SQLiteDatabase sqLiteDatabase;
    private DataBaseHelper dataBaseHelper;


    public AlarmDBAdapter(Context context){
        dataBaseHelper = new DataBaseHelper(context);
    }

    private  int hour,minute,ringtoneName,complexityLevel,deepSleepMusicStatus;
    private String messageText;
    public void GetDataToSaveAlarmDB(int hour, int minute, int ringtoneName,String messageText,
                                     int complexityLevel,int deepSleepMusicStatus){
        this.hour = hour;
        this.minute = minute;
        this.ringtoneName = ringtoneName;
        this.messageText = messageText;
        this.complexityLevel = complexityLevel;
        this.deepSleepMusicStatus = deepSleepMusicStatus;
    }


    private static class DataBaseHelper extends SQLiteOpenHelper{
        private DataBaseHelper(Context context) {
            super(context, AlarmDBValues.DATABASE_NAME, null, AlarmDBValues.DATA_BASE_VERSION);
        }
        //run when the database file did not exist and was just created
        @Override
        public void onCreate(SQLiteDatabase db) {
            String DB_CREATE_TABLE = "CREATE TABLE " + AlarmDBValues.TABLE_NAME +
                    " ( " + AlarmDBValues._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AlarmDBValues.COLUMN_HOUR + " INTEGER, " +
                    AlarmDBValues.COLUMN_MINUTE + " INTEGER, "+
                    AlarmDBValues.COLUMN_RINGTONE_NAME + " INTEGER, "+
                    AlarmDBValues.COLUMN_MESSAGE_TEXT + " TEXT, "+
                    AlarmDBValues.COLUMN_COMPLEXITY_LEVEL + " INTEGER, "+
                    AlarmDBValues.COLUMN_DEEP_SLEEP_MUSIC_STATUS + " INTEGER );";
            db.execSQL(DB_CREATE_TABLE);
        }

        //Called when the database file exists but the stored version number is different tan requested in constructor
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            ShowLogs.i(" AlarmDBAdapter OnUpgrade");
            String DB_DROP_TABLE = "DROP TABLE IF EXISTS " + AlarmDBValues.TABLE_NAME;

            db.execSQL(DB_DROP_TABLE);
            //recreate new database (if version changed)
            onCreate(db);
        }
    }

    public AlarmDBAdapter OpenAlarmDB(){
        if(ShowLogs.LOG_STATUS)ShowLogs.i("AlarmDBAdapter "+"OpenAlarmDB");
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public long InsertRowAlarmDB(){
        if(ShowLogs.LOG_STATUS)ShowLogs.i("AlarmDBAdapter "+"InsertRowAlarmDB");
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmDBValues.COLUMN_HOUR,hour);
        contentValues.put(AlarmDBValues.COLUMN_MINUTE,minute);
        contentValues.put(AlarmDBValues.COLUMN_RINGTONE_NAME,ringtoneName);
        contentValues.put(AlarmDBValues.COLUMN_MESSAGE_TEXT,messageText);
        contentValues.put(AlarmDBValues.COLUMN_COMPLEXITY_LEVEL,complexityLevel);
        contentValues.put(AlarmDBValues.COLUMN_DEEP_SLEEP_MUSIC_STATUS,deepSleepMusicStatus);

        //Return the row ID of the newly inserted row, or -1 if an error occurred
        return sqLiteDatabase.insert(AlarmDBValues.TABLE_NAME,null,contentValues);
    }

    private String where = null;
    public boolean DeleteRowAlarmDB(long rowIDtoDelete){
        ShowLogs.i("AlarmDBAdapter "+"DeleteAllRowsAlarmDB");
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        where = AlarmDBValues._ID + "=" + rowIDtoDelete;
        return sqLiteDatabase.delete(AlarmDBValues.TABLE_NAME, where,null) !=0;
    }

    public void DeleteAllRowsAlarmDB(){
        if(ShowLogs.LOG_STATUS)ShowLogs.i("AlarmDBAdapter "+"DeleteAllRowsAlarmDB");
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(AlarmDBValues.TABLE_NAME, null,null);
    }

    public Cursor GetAllRowsAlarmDB(){
        if(ShowLogs.LOG_STATUS)ShowLogs.i("AlarmDBAdapter "+"GetAllRowsAlarmDB");
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(true, AlarmDBValues.TABLE_NAME, AlarmDBValues.ALL_COLUMNS_KEYS,
                null,null,null,null,null,null);

        if(cursor!= null)
            cursor.moveToFirst();

        return cursor;
    }
    public Cursor GetRowAlarmDB(long rowIdToGet){
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        where = AlarmDBValues._ID + "=" + rowIdToGet;
        Cursor cursor = sqLiteDatabase.query(true, AlarmDBValues.TABLE_NAME, AlarmDBValues.ALL_COLUMNS_KEYS,
                where,null,null,null,null,null);
        if(cursor !=null)
            cursor.moveToFirst();

        return cursor;
    }

    public boolean UpdateRowAlarmDB(long rowIdToUpdate){
        if(ShowLogs.LOG_STATUS)ShowLogs.i("AlarmDBAdapter "+"UpdateRowAlarmDB");
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        where = AlarmDBValues._ID + "=" + rowIdToUpdate;

        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmDBValues.COLUMN_HOUR,hour);
        contentValues.put(AlarmDBValues.COLUMN_MINUTE,minute);
        contentValues.put(AlarmDBValues.COLUMN_RINGTONE_NAME,ringtoneName);
        contentValues.put(AlarmDBValues.COLUMN_MESSAGE_TEXT,messageText);
        contentValues.put(AlarmDBValues.COLUMN_COMPLEXITY_LEVEL,complexityLevel);
        contentValues.put(AlarmDBValues.COLUMN_DEEP_SLEEP_MUSIC_STATUS,deepSleepMusicStatus);

        return sqLiteDatabase.update(AlarmDBValues.TABLE_NAME,contentValues,where,null) != 0;
    }




    public void closeAlarmDB(){
        if(ShowLogs.LOG_STATUS)ShowLogs.i("AlarmDBAdapter "+"closeAlarmDB");
        dataBaseHelper.close();
    }

}
