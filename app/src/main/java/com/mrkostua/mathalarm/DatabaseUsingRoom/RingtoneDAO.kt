package com.mrkostua.mathalarm.DatabaseUsingRoom

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
    fun insertRingtoneObject(ringtoneObject: RingtoneObject)
}
