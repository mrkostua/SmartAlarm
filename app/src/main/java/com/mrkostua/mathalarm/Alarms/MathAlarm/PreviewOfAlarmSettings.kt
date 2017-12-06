package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.app.AlertDialog
import android.content.Context
import com.mrkostua.mathalarm.ConstantValues
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.SharedPreferencesAlarmData
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
        val alarmSettingData = SharedPreferencesAlarmData(context)
        val settingsList = ArrayList<String>(ConstantValues.alarmSettingsOptionsList.size)

        if (alarmSettingData.alarmHours != ConstantValues.SHARED_PREFERENCES_WRONG_TIME_VALUE && alarmSettingData.alarmMinutes != ConstantValues.SHARED_PREFERENCES_WRONG_TIME_VALUE) {
            settingsList.add(notficationTools.convertTimeToReadableTime(alarmSettingData.alarmHours, alarmSettingData.alarmMinutes))

        }

        return settingsList.toTypedArray()
    }


}