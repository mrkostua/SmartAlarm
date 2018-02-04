package com.mrkostua.mathalarm.DatabaseUsingRoom

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mrkostua.mathalarm.Tools.SingletonHolder

/**
 * @author Kostiantyn Prysiazhnyi on 2018-02-04.
 */
@Database(entities = [(RingtoneObject::class)], version = 1)
abstract class AlarmDB : RoomDatabase() {
    abstract fun getRingtoneDao(): RingtoneDAO

    companion object : SingletonHolder<AlarmDB, Context>({
        Room.databaseBuilder(it.applicationContext,
                AlarmDB::class.java, "Ringtones.db")
                .build()
    })
}