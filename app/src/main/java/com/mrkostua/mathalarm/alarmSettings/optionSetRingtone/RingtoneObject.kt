package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone


import android.net.Uri


/**
 * @author Kostiantyn Prysiazhnyi on 18.01.2018.
 */
 class RingtoneObject(
        val name: String,
        var rating: Int = 0,
        var isPlaying: Boolean = false,
        var isChecked: Boolean = false,
        val uri: Uri? = null)