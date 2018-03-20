package com.mrkostua.mathalarm.alarms.mathAlarm

/**
 * @author Kostiantyn on 21.11.2017.
 */

class AlarmObject(var hours: Int, var minutes: Int, var textMessage: String,
                  var ringtoneName: String, var complexityLevel: Int = 0, var isDeepSleepMusicOn: Boolean = false)
