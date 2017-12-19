package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import com.mrkostua.mathalarm.AlarmSettings.FragmentCreationHelper
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetTime
import com.mrkostua.mathalarm.AlarmSettings.FragmentSettingsOptionSetRingtone
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.SharedPreferencesHelper
import com.mrkostua.mathalarm.SharedPreferencesHelper.get
import com.mrkostua.mathalarm.Tools.ConstantValues
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.PreferencesConstants

/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */
class PreviewOfAlarmSettings(val context: Context, val mainActivity: Activity) {
    private val notificationTools = NotificationTools(context)
    private val setAlarmSettingFragmentList = ArrayList<Fragment>()
    private val fragmentHelper = FragmentCreationHelper(mainActivity)
//    private val setNewAlarm = MathAlarmPreview()

    fun showSettingsPreviewDialog() {
        val previewDialog = AlertDialog.Builder(context, R.style.SettingsPreviewAlertDialogStyle)
                .setTitle(R.string.settingsPreviewTitle)
                .setItems(getArrayOfSetAlarmSettings(), { dialogInterface, whichClicked ->
                    fragmentHelper.loadFragment(setAlarmSettingFragmentList[whichClicked])
                    dialogInterface.dismiss()
                })
                .setPositiveButton(R.string.settingsPreviewPostiveButtonText, { dialogInterface, i ->
                    //                    setNewAlarm.ConfirmAlarmPreview_Method()
                    //todo implement new class or method for starting alarm Service and other func for alarm setting.
                })
                .setNegativeButton(R.string.setttingsPreviewNegativeButtonText, { dialogInterface, i ->
                    dialogInterface.dismiss()
                }).create().show()
    }

    //todo If preferences value is empty add default to the list
    private fun getArrayOfSetAlarmSettings(): Array<String> {
        val preferencesHelper = SharedPreferencesHelper.customSharedPreferences(context, PreferencesConstants.ALARM_SP_NAME.getKeyValue())
        val settingsList = ArrayList<String>(ConstantValues.alarmSettingsOptionsList.size)

        val hours: Int? = preferencesHelper[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue()]
        val minutes: Int? = preferencesHelper[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()]
        if (hours != null && minutes != null && hours != PreferencesConstants.ALARM_HOURS.getDefaultIntValue() &&
                minutes != PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()) {
            settingsList.add(notificationTools.convertTimeToReadableTime(hours, minutes))
            setAlarmSettingFragmentList.add(FragmentOptionSetTime())

            settingsList.add(preferencesHelper[PreferencesConstants.ALARM_TEXT_MESSAGE.getKeyValue(), PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage]?.toString()
                    ?: PreferencesConstants.ALARM_TEXT_MESSAGE.defaultTextMessage)
            setAlarmSettingFragmentList.add(FragmentSettingsOptionSetRingtone())

        } else {
            throw UnsupportedOperationException(" time need to be set!")

        }


        return settingsList.toTypedArray()
    }

/*       think about updating this method to be more secure for developer
    private inline fun iterateThrowSettingsFragmentList(action: (Int) -> Unit) {
        for (fragment in ConstantValues.alarmSettingsOptionsList) {
            action(ConstantValues.alarmSettingsOptionsList.indexOf(fragment))

        }
    }*/

    private fun getRingtoneName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}