package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.app.AlertDialog
import android.content.Context
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.SharedPreferencesHelper
import com.mrkostua.mathalarm.SharedPreferencesHelper.get
import com.mrkostua.mathalarm.Tools.ConstantValues
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.PreferencesConstants

/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */
class PreviewOfAlarmSettings(val context: Context) {
    private val notificationTools = NotificationTools(context)
    public fun showSettingsPreviewDialog() {
        val alarmSettingsArray = getArrayOfSetAlarmSettings()
        val previewDialog = AlertDialog.Builder(context, R.style.SettingsPreviewAlertDialogStyle)
                .setTitle(R.string.settingsPreviewTitle)
                .setItems(alarmSettingsArray, { dialogInterface, i ->

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
        val preferencesHelper = SharedPreferencesHelper.customSharedPreferences(context, PreferencesConstants.ALARM_SP_NAME.getKeyValue())
        val settingsList = ArrayList<String>(ConstantValues.alarmSettingsOptionsList.size)

        val hours: Int? = preferencesHelper[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue()]
        val minutes: Int? = preferencesHelper[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()]
        if (hours != null && minutes != null && hours != PreferencesConstants.ALARM_HOURS.getDefaultIntValue() &&
                minutes != PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()) {
            settingsList.add(notificationTools.convertTimeToReadableTime(hours, minutes))

        }
        val alarmTextMessage: Int? = preferencesHelper[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue(), PreferencesConstants.ALARM_TEXT_MESSAGE.getDefaultIntValue()]
        if (alarmTextMessage != null && alarmTextMessage != PreferencesConstants.ALARM_TEXT_MESSAGE.getDefaultIntValue()) {
            settingsList.add(getRingtoneName())

        }

//        when(fragment){
//            FragmentOptionSetTime() ->
//                FragmentSettingsOptionSetRingtone() ->
//            action(ConstantValues.alarmSettingsOptionsList.indexOf(fragment))
//            FragmentOptionSetTime() ->
//                action(ConstantValues.alarmSettingsOptionsList.indexOf(fragment))
//
//        }

        return settingsList.toTypedArray()
    }

//    private fun moveToChosenSettingsFragment(activeSettingsList) {
//
//    }
    //todo think about updating this method to be more secure for developer
    private inline fun interateThrowSettingsFragmentList(action : (Int) -> Unit){
        for(fragment in ConstantValues.alarmSettingsOptionsList){
            action(ConstantValues.alarmSettingsOptionsList.indexOf(fragment))

        }
    }

    private fun getRingtoneName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}