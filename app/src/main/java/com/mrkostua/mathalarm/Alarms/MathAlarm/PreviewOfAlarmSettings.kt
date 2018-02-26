package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import com.mrkostua.mathalarm.AlarmSettings.*
import com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.*
import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper.get

/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */
class PreviewOfAlarmSettings(private val context: Context, private val mainActivity: Activity) {
    private val TAG = this.javaClass.simpleName
    private val notificationTools = NotificationTools(context)
    private val alarmSettingActivityIntent = Intent(context, AlarmSettingsActivity::class.java)
    private val wakeLockServiceIntent = Intent(context, WakeLockService::class.java)
    private val alarmObject = getAlarmObjectWithAlarmSettings()

    //todo fix background color of the AlertDialog (in style file it is black but here is white (probably default color is overriding ours)
    fun showSettingsPreviewDialog() {
        val settingsList = getAlarmSettingsList(alarmObject)
        val settingItemsList = ArrayList<String>(settingsList.size)
        settingsList.forEach { pair ->
            settingItemsList.add(pair.first)
        }
        AlertDialog.Builder(context, R.style.AlertDialogCustomStyle)
                .setTitle(R.string.settingsPreviewTitle)
                .setItems(settingItemsList.toTypedArray(), { dialogInterface, whichClicked ->
                    ShowLogs.log(TAG, "showSettingsPreviewDialog alarmSettings fragment to load : " + ConstantValues.alarmSettingsOptionsList[whichClicked])
                    alarmSettingActivityIntent.putExtra(ConstantValues.INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST, whichClicked)
                    context.startActivity(alarmSettingActivityIntent)
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

        return AlarmObject(hours, minutes, textMessage, ringtoneName)

    }

    private fun getAlarmSettingsList(alarmObject: AlarmObject): ArrayList<Pair<String, Fragment>> {
        val settingsList = ArrayList<Pair<String, Fragment>>(ConstantValues.alarmSettingsOptionsList.size)

        settingsList.add(Pair("Time : " + notificationTools.convertTimeToReadableTime(alarmObject.hours, alarmObject.minutes),
                FragmentOptionSetTime()))
        settingsList.add(Pair("Message : " + alarmObject.textMessage, FragmentOptionSetMessage()))
        settingsList.add(Pair("Ringtone : " + alarmObject.ringtoneName, FragmentOptionSetRingtone()))
        settingsList.add(Pair("On/Off deep sleep music : " + alarmObject.isDeepSleepMusicOn.toString(), FragmentOptionSetDeepSleepMusic()))
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
        wakeLockServiceIntent.putExtra("alarmTimeKey", notificationTools.convertTimeToReadableTime(alarmObject.hours, alarmObject.minutes))
        mainActivity.startService(wakeLockServiceIntent)
        notificationTools.showToastMessage("Service was activated")
    }


}