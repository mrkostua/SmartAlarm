package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import com.mrkostua.mathalarm.AlarmSettings.FragmentCreationHelper
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetTime
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.*
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.get

/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */
class PreviewOfAlarmSettings(val context: Context, val mainActivity: Activity) {
    private val notificationTools = NotificationTools(context)
    private val setAlarmSettingFragmentList = ArrayList<Fragment>()
    private val fragmentHelper = FragmentCreationHelper(mainActivity)

    private lateinit var alarmDataObject: AlarmObject

    fun showSettingsPreviewDialog() {
        AlertDialog.Builder(context, R.style.AlertDialogCustomStyle)
                .setTitle(R.string.settingsPreviewTitle)
                .setItems(getArrayOfSetAlarmSettings(), { dialogInterface, whichClicked ->
                    fragmentHelper.loadFragment(setAlarmSettingFragmentList[whichClicked])
                    dialogInterface.dismiss()

                })
                .setPositiveButton(R.string.settingsPreviewPostiveButtonText, { dialogInterface, i ->
                    scheduleNewAlarm()

                })
                .setNegativeButton(R.string.setttingsPreviewNegativeButtonText, { dialogInterface, i ->
                    dialogInterface.dismiss()

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
            setAlarmSettingFragmentList.add(FragmentOptionSetTime())

            val textMessage: String = preferencesHelper[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue(), PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage]?.toString()
                    ?: PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage
            settingsList.add(textMessage)
            setAlarmSettingFragmentList.add(FragmentOptionSetRingtone())

            val ringtoneId: Int = preferencesHelper[PreferencesConstants.ALARM_RINGTONE_RES_ID.getKeyValue(), PreferencesConstants.ALARM_RINGTONE_RES_ID.getDefaultIntValue()]?.toInt()
                    ?: PreferencesConstants.ALARM_RINGTONE_RES_ID.getDefaultIntValue()
            settingsList.add(ToolsMethod.getRingtoneNameByResId(context, ringtoneId))
            alarmDataObject = AlarmObject(hours, minutes, textMessage, ringtoneId, 0)

        } else {
            throw UnsupportedOperationException(" time need to be set!")

        }


        return settingsList.toTypedArray()
    }

    private fun scheduleNewAlarm() {
        //todo think about  : stop set alarm if exist(in the future after testing)
        val onOffAlarm = OnOffAlarm(context, alarmDataObject.hours, alarmDataObject.minutes, alarmDataObject.complexityLevel,
                alarmDataObject.ringToneId, true, alarmDataObject.textMessage, 0)
        onOffAlarm.SetNewAlarm()
        startNewWakeLockService()
    }

    private fun startNewWakeLockService() {
        mainActivity.startService(Intent(context, WakeLockService()::class.java))
        notificationTools.showToastMessage("Service was activated")
    }


}