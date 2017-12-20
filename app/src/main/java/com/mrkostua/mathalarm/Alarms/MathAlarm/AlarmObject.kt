package com.mrkostua.mathalarm.Alarms.MathAlarm

import com.mrkostua.mathalarm.Tools.PreferencesConstants

/**
 * @author Kostiantyn on 21.11.2017.
 */

class AlarmObject() {
    var hours = PreferencesConstants.ALARM_HOURS.getDefaultIntValue()
    var minutes = PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()
    var textMessage = PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage
    var ringtoneResId = PreferencesConstants.ALARM_RINGTONE_RES_ID.getDefaultIntValue()


    constructor(hours: Int, minutes: Int) : this() {
        this.hours = hours
        this.minutes = minutes
    }

    constructor(textMessage: String) : this() {
        this.textMessage = textMessage
    }

}