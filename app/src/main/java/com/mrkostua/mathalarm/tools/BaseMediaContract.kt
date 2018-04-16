package com.mrkostua.mathalarm.tools

import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneObject

/**
 * @author Kostiantyn Prysiazhnyi on 4/16/2018.
 */
interface BaseMediaContract {
    var ringtoneObject: RingtoneObject
    fun playRingtone()
    fun stopPlayingRingtone()
    fun releaseObjects()

}