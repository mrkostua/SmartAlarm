package com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarmSettings.mainSettings.AlarmSettingsActivity
import com.mrkostua.mathalarm.alarmSettings.mainSettings.AlarmSettingsNames
import com.mrkostua.mathalarm.alarms.mathAlarm.AlarmObject
import com.mrkostua.mathalarm.alarms.mathAlarm.OnOffAlarm
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService
import com.mrkostua.mathalarm.databinding.CustomViewAlertDialogSettingsPreviewBinding
import com.mrkostua.mathalarm.tools.ConstantValues
import com.mrkostua.mathalarm.tools.NotificationsTools
import com.mrkostua.mathalarm.tools.TimeToAlarmStart
import javax.inject.Inject


/**
 * @author Kostiantyn Prysiazhnyi on 05.12.2017.
 */
//TODO think about changing design of the Preview alertDialog by simply changing it from itemsList to custom activity(gives us a lot of features(different color of text size etc) to use)
class PreviewOfAlarmSettings @Inject constructor(private val context: Context,
                                                 private val mainActivity: Activity, private val mainViewModel: MainAlarmViewModel) : View.OnClickListener {
    private val TAG = this.javaClass.simpleName
    private val alarmNotifications = NotificationsTools(context)
    private val alarmSettingActivityIntent = Intent(context, AlarmSettingsActivity::class.java)
    private val wakeLockServiceIntent = Intent(context, WakeLockService::class.java)
    private val alarmObject = mainViewModel.getAlarmDataObject()
    private lateinit var binding: CustomViewAlertDialogSettingsPreviewBinding

    fun showSettingsPreviewDialog() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_view_alert_dialog_settings_preview,
                null, false)
        with(binding) {
            viewModel = mainViewModel
            executePendingBindings()
        }
        setOnClickListeners()

        AlertDialog.Builder(context, R.style.AlertDialogCustomStyle)
                .setTitle(R.string.settingsPreviewTitle)
                .setView(binding.root)
                .setPositiveButton(R.string.settingsPreviewPostiveButtonText, { dialogInterface, i ->
                    scheduleNewAlarm(alarmObject)
                })
                .setNegativeButton(R.string.setttingsPreviewNegativeButtonText, { dialogInterface, i ->
                    dialogInterface.dismiss()
                }).create().show()

    }

    override fun onClick(v: View?) {
        with(binding) {
            when (v) {
                tvHourPreview, tvMinutePreview -> {
                    showChosenSettingsFragment(AlarmSettingsNames.OPTION_SET_TIME.getKeyValue())
                }
                tvRingtonePreview -> {
                    showChosenSettingsFragment(AlarmSettingsNames.OPTION_SET_RINGTONE.getKeyValue())
                }
                tvTextMessagePreview -> {
                    showChosenSettingsFragment(AlarmSettingsNames.OPTION_SET_MESSAGE.getKeyValue())
                }
            }
        }
    }

    private fun scheduleNewAlarm(alarmObject: AlarmObject) {
        //todo think about  : stop set alarm if exist(in the future after testing)
        val onOffAlarm = OnOffAlarm(context, alarmObject.hours, alarmObject.minutes, alarmObject.complexityLevel,
                1, true, alarmObject.textMessage, 0)
        onOffAlarm.SetNewAlarm()
        startNewWakeLockService(alarmObject)
    }

    private fun startNewWakeLockService(alarmObject: AlarmObject) {
        wakeLockServiceIntent.putExtra("alarmTimeKey", TimeToAlarmStart.convertTimeToReadableTime(alarmObject.hours, alarmObject.minutes))
        mainActivity.startService(wakeLockServiceIntent)
        alarmNotifications.showToastMessage("Service was activated")
    }

    private fun showChosenSettingsFragment(which: Int) {
        alarmSettingActivityIntent.putExtra(ConstantValues.INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST, which)
        context.startActivity(alarmSettingActivityIntent)
    }

    private fun setOnClickListeners() {
        with(binding) {
            tvHourPreview.setOnClickListener(this@PreviewOfAlarmSettings)
            tvMinutePreview.setOnClickListener(this@PreviewOfAlarmSettings)
            tvRingtonePreview.setOnClickListener(this@PreviewOfAlarmSettings)
            tvTextMessagePreview.setOnClickListener(this@PreviewOfAlarmSettings)
        }
    }

}