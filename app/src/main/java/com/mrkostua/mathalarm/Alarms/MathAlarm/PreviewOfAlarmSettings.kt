package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import com.mrkostua.mathalarm.AlarmSettings.FragmentCreationHelper
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetMessage
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone.FragmentOptionSetTime
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.ConstantValues
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.PreferencesConstants
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.get

/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */
class PreviewOfAlarmSettings(val context: Context, val mainActivity: Activity) {
    private val notificationTools = NotificationTools(context)
    private val fragmentHelper = FragmentCreationHelper(mainActivity)

    //todo fix background color of the AlertDialog (in style file it is black but here is white (probably default color is overriding ours)
    fun showSettingsPreviewDialog() {
        val alarmObject = getAlarmObjectWithAlarmSettings()

        val settingsList = getAlarmSettingsList(alarmObject)
        val settingItemsList = ArrayList<String>(settingsList.size)
        val settingsFragmentsList = ArrayList<Fragment>(settingsList.size)
        settingsList.forEach { pair ->
            settingItemsList.add(pair.first)
            settingsFragmentsList.add(pair.second)
        }
        AlertDialog.Builder(context, R.style.AlertDialogCustomStyle)
                .setTitle(R.string.settingsPreviewTitle)
                .setItems(settingItemsList.toTypedArray(), { dialogInterface, whichClicked ->
                    fragmentHelper.loadFragment(settingsFragmentsList[whichClicked])
                    dialogInterface.dismiss()

                })
                .setPositiveButton(R.string.settingsPreviewPostiveButtonText, { dialogInterface, i ->
                    scheduleNewAlarm(alarmObject)
                })
                .setNegativeButton(R.string.setttingsPreviewNegativeButtonText, { dialogInterface, i ->
                    dialogInterface.dismiss()
                }).create().show()

    }

    private fun getAlarmObjectWithAlarmSettings(): AlarmObject {
        val preferencesHelper = SharedPreferencesHelper.customSharedPreferences(context, PreferencesConstants.ALARM_SP_NAME.getKeyValue())
        val hours: Int = preferencesHelper[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue()]
                ?: PreferencesConstants.ALARM_HOURS.getDefaultIntValue()
        val minutes: Int = preferencesHelper[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()]
                ?: PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()
        val textMessage: String = preferencesHelper[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue(), PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage]
                ?: PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage
        val ringtoneName: String = preferencesHelper[PreferencesConstants.ALARM_RINGTONE_NAME.getKeyValue(), PreferencesConstants.ALARM_RINGTONE_NAME.defaultRingtoneName]
                ?: PreferencesConstants.ALARM_RINGTONE_NAME.defaultRingtoneName

        return AlarmObject(hours, minutes, textMessage, ringtoneName, 0)

    }

    private fun getAlarmSettingsList(alarmObject: AlarmObject): ArrayList<Pair<String, Fragment>> {
        val settingsList = ArrayList<Pair<String, Fragment>>(ConstantValues.alarmSettingsOptionsList.size)

        settingsList.add(Pair(notificationTools.convertTimeToReadableTime(alarmObject.hours, alarmObject.minutes),
                FragmentOptionSetTime()))
        settingsList.add(Pair(alarmObject.textMessage, FragmentOptionSetMessage()))
        settingsList.add(Pair(alarmObject.ringToneName, FragmentOptionSetRingtone()))
        return settingsList
    }

    private fun scheduleNewAlarm(alarmObject: AlarmObject) {
        //todo think about  : stop set alarm if exist(in the future after testing)
        val onOffAlarm = OnOffAlarm(context, alarmObject.hours, alarmObject.minutes, alarmObject.complexityLevel,
                1, true, alarmObject.textMessage, 0)
        onOffAlarm.SetNewAlarm()
        startNewWakeLockService()
    }

    private fun startNewWakeLockService() {
        mainActivity.startService(Intent(context, WakeLockService()::class.java))
        notificationTools.showToastMessage("Service was activated")
    }


}