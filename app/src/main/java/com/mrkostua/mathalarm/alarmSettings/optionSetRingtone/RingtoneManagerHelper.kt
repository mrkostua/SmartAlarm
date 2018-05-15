package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.content.Context
import android.media.RingtoneManager
import com.mrkostua.mathalarm.injections.annotation.ApplicationContext

/**
 * @author Kostiantyn Prysiazhnyi on 23.01.2018.
 */
class RingtoneManagerHelper constructor(@ApplicationContext private val context: Context) {
    fun getDefaultAlarmRingtonesList(): ArrayList<RingtoneObject> {
        val ringtoneManager = RingtoneManager(context)
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM)
        val ringtonesCursor = ringtoneManager.cursor
        val cursorSize = ringtonesCursor.count

        return if (cursorSize != 0 && ringtonesCursor.moveToFirst()) {
            val ringtoneObjectsList = ArrayList<RingtoneObject>(cursorSize)
            while (!ringtonesCursor.isAfterLast && ringtonesCursor.moveToNext()) {
                ringtoneObjectsList.add(RingtoneObject(
                        ringtonesCursor.getString(RingtoneManager.TITLE_COLUMN_INDEX), 100,
                        false, false, ringtoneManager.getRingtoneUri(ringtonesCursor.position)))
            }

            ringtoneObjectsList

        } else {
            ArrayList()

        }
    }
}