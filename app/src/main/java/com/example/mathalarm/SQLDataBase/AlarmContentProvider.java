package com.example.mathalarm.SQLDataBase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Администратор on 02.03.2017.
 */

//manage database creation and version management.
public class AlarmContentProvider extends ContentProvider{
    private SQLiteOpenHelper sqLiteOpenHelper;


    private static class DataBaseHelper extends SQLiteOpenHelper{

        public DataBaseHelper(Context context) {
            super(context, AlarmDataValues.TABLE_NAME, null, AlarmDataValues.DATA_BASE_VERSION);
        }
        //run when the database file did not exist and was just created
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + AlarmDataValues.TABLE_NAME +
                    " ( " + AlarmDataValues.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    AlarmDataValues.COLUMN_HOUR + " INTEGER, " +
                    AlarmDataValues.COLUMN_MINUTE + " INTEGER, "+
                    AlarmDataValues.COLUMN_RINGTONE_NAME + " INTEGER, "+
                    AlarmDataValues.COLUMN_MESSAGE_TEXT + " TEXT, "+
                    AlarmDataValues.COLUMN_COMPLEXITY_LEVEL + " INTEGER, "+
                    AlarmDataValues.COLUMN_DEEP_SLEEP_MUSIC_STATUS + " INTEGER );");
        }

        //Called when the database file exists but the stored version number is different tan requested in constructor
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + AlarmDataValues.TABLE_NAME);
            onCreate(db);
        }

    }
    @Override
    public boolean onCreate() {
        sqLiteOpenHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
