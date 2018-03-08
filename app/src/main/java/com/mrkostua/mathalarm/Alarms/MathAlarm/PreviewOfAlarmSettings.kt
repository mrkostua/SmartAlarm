package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.mrkostua.mathalarm.AlarmSettings.AlarmSettingsActivity
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetDeepSleepMusic
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetMessage
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetTime
import com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.AlarmSettings.AlarmSettingsActivity
import com.mrkostua.mathalarm.Alarms.MathAlarm.Services.WakeLockService
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.ConstantValues
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.PreferencesConstants
import com.mrkostua.mathalarm.Tools.ShowLogs
import com.mrkostua.mathalarm.extensions.get
import com.mrkostua.mathalarm.injections.annotation.ActivityContext
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */
public class PreviewOfAlarmSettings @Inject constructor(@ActivityContext private val context: Context,
                                                        private val mainActivity: Activity, private val sharedPreferences: SharedPreferences) {
    private val TAG = this.javaClass.simpleName
    private val notificationTools = NotificationTools(context)
    private val alarmSettingActivityIntent = Intent(context, AlarmSettingsActivity::class.java)
    private val wakeLockServiceIntent = Intent(context, WakeLockService::class.java)
    private val alarmObject = getAlarmObjectWithAlarmSettings()

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

        val hours: Int = sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue()]
                ?: PreferencesConstants.ALARM_HOURS.getDefaultIntValue()
        val minutes: Int = sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()]
                ?: PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()
        val textMessage: String = sharedPreferences[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue(), PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage]
                ?: PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage
        val ringtoneName: String = sharedPreferences[PreferencesConstants.ALARM_RINGTONE_NAME.getKeyValue(), PreferencesConstants.ALARM_RINGTONE_NAME.defaultRingtoneName]
                ?: PreferencesConstants.ALARM_RINGTONE_NAME.defaultRingtoneName

        return AlarmObject(hours, minutes, textMessage, ringtoneName)

    }

    private fun getAlarmSettingsList(alarmObject: AlarmObject): ArrayList<Pair<String, Fragment>> {
        val settingsList = ArrayList<Pair<String, Fragment>>(ConstantValues.alarmSettingsOptionsList.size)
        val alarmOptionsPreviewText = getAlarmOptionsPreviewText(alarmObject)
        (0 until ConstantValues.alarmSettingsOptionsList.size).mapTo(settingsList) {
            Pair(alarmOptionsPreviewText[it], ConstantValues.alarmSettingsOptionsList[it])
        }
        return settingsList
    }

    private fun getAlarmOptionsPreviewText(alarmObject: AlarmObject): Array<String> {
        val alarmOptionsPreviewText = context.resources.getStringArray(R.array.alarmOptionsPreviewText)
        alarmOptionsPreviewText[0] = alarmOptionsPreviewText[0] + notificationTools.convertTimeToReadableTime(alarmObject.hours, alarmObject.minutes)
        alarmOptionsPreviewText[1] = alarmOptionsPreviewText[1] + alarmObject.ringtoneName
        alarmOptionsPreviewText[2] = alarmOptionsPreviewText[2] + alarmObject.textMessage
        alarmOptionsPreviewText[3] = alarmOptionsPreviewText[3] + alarmObject.isDeepSleepMusicOn.toString()
        return alarmOptionsPreviewText

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