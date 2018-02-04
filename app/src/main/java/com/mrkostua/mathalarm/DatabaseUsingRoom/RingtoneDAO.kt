package com.mrkostua.mathalarm.DatabaseUsingRoom

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable

/**
 * @author Kostiantyn Prysizhnyi on 2018-02-02.
 */

interface RingtoneDAO {

    @Query("SELECT * FROM ringtoneObject")
    fun getAllRingtones(): Flowable<List<RingtoneObject>>

    @Insert
    fun insertAllRingtones(vararg ringtoneObjects: RingtoneObject)

    @Update
    fun updateRingtoneObject(ringtoneToUpdate: RingtoneObject, newRingtone: RingtoneObject)

    @Delete
    fun deleteRingtoneObject(ringtoneObjects: RingtoneObject)
}
