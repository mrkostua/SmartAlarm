package com.mrkostua.mathalarm.DatabaseUsingRoom

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.net.Uri


/**
 * @author Kostiantyn Prysiazhnyi on 18.01.2018.
 */
@Entity
data class RingtoneObject(
        @PrimaryKey
        val name: String,
        var rating: Int = 0,
        var isPlaying: Boolean = false,
        var isChecked: Boolean = false,
        val uri: Uri? = null)