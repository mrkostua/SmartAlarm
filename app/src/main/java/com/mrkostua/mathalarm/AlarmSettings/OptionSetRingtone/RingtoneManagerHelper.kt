package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.content.Context
import android.media.RingtoneManager

/**
 * @author Kostiantyn Prysiazhnyi on 23.01.2018.
 */
object RingtoneManagerHelper {
    fun getDefaultAlarmRingtonesList(context: Context): ArrayList<RingtoneObject> {
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