package com.mrkostua.mathalarm

import com.mrkostua.mathalarm.Tools.PreferencesConstants

/**
 * @author Kostiantyn on 21.11.2017.
 */

class AlarmObject() {
    var hours = PreferencesConstants.ALARM_HOURS.getDefaultIntValue()
    var minutes = PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()
    var textMessage = ""
    var ringtoneResId = PreferencesConstants.ALARM_RINGTONE_RES_ID.getDefaultIntValue()


    constructor(hours: Int, minutes: Int) : this() {
        this.hours = hours
        this.minutes = minutes
    }

    constructor(textMessage: String) : this() {
        this.textMessage = textMessage
    }

}
