package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.app.AlertDialog
import android.content.Context
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.SharedPreferencesHelper
import com.mrkostua.mathalarm.SharedPreferencesHelper.get
import com.mrkostua.mathalarm.Tools.ConstantValues
import com.mrkostua.mathalarm.Tools.ConstantsEnumPrefrences
import com.mrkostua.mathalarm.Tools.NotificationTools

/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */
class PreviewOfAlarmSettings(val context: Context) {
    private val notficationTools = NotificationTools(context)
    public fun showSettingsPreviewDialog() {
        val previewDialog = AlertDialog.Builder(context, R.style.SettingsPreviewAlertDialogStyle)
                .setTitle(R.string.settingsPreviewTitle)
                .setItems(getArrayOfSetAlarmSettings(), { dialogInterface, i ->
                    dialogInterface.dismiss()
                    //todo chosen settings option and start activity with those fragments
                })
                .setPositiveButton(R.string.settingsPreviewPostiveButtonText, { dialogInterface, i ->
                    //todo set new Alarm more detail in MainMathAlarm.class
                })
                .setNegativeButton(R.string.setttingsPreviewNegativeButtonText, { dialogInterface, i ->
                    dialogInterface.dismiss()
                    //todo maybe show something after moving back ( so user will know how to open Alarm Settings
                }).create().show()
    }

    private fun getArrayOfSetAlarmSettings(): Array<String> {
        val preferencesHelper = SharedPreferencesHelper.customSharedPreferences(context, ConstantsEnumPrefrences.ALARM_SP_NAME.getKeyValue())
        val settingsList = ArrayList<String>(ConstantValues.alarmSettingsOptionsList.size)

        val hours: Int? = preferencesHelper[ConstantsEnumPrefrences.ALARM_HOURS.getKeyValue(), ConstantsEnumPrefrences.ALARM_HOURS.getDefaultIntValue()]
        val minutes: Int? = preferencesHelper[ConstantsEnumPrefrences.ALARM_MINUTES.getKeyValue(), ConstantsEnumPrefrences.ALARM_MINUTES.getDefaultIntValue()]
        if (hours != null && minutes != null && hours != ConstantsEnumPrefrences.ALARM_HOURS.getDefaultIntValue() &&
                minutes != ConstantsEnumPrefrences.ALARM_MINUTES.getDefaultIntValue()) {
            settingsList.add(notficationTools.convertTimeToReadableTime(hours, minutes))

        }
        val alarmTextMessage: Int? = preferencesHelper[ConstantsEnumPrefrences.ALARM_TEXT_MESSAGE.getKeyValue(), ConstantsEnumPrefrences.ALARM_TEXT_MESSAGE.getDefaultIntValue()]
        if (alarmTextMessage != null && alarmTextMessage != ConstantsEnumPrefrences.ALARM_TEXT_MESSAGE.getDefaultIntValue()) {
            settingsList.add(getRingtoneName())

        }

        return settingsList.toTypedArray()
    }

    private fun getRingtoneName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}