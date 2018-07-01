package com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.databinding.ActivityMainAlarmBinding
import com.mrkostua.mathalarm.extensions.setTextAppearance
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main_alarm.*
import javax.inject.Inject

/**
 * @author Prysiazhnyi Kostiantyn on 21.11.2017.
 */
class MainAlarmActivity : DaggerAppCompatActivity() {
    private val TAG = this.javaClass.simpleName

    @Inject
    public lateinit var mainAlarmViewModule: MainAlarmViewModel
    @Inject
    public lateinit var userHelper: UserHelper
    @Inject
    public lateinit var intentSettingActivity: Intent
    @Inject
    public lateinit var previewOfSetting: PreviewOfAlarmSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding: ActivityMainAlarmBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_alarm)
        with(viewDataBinding) {
            viewModel = mainAlarmViewModule
            executePendingBindings()
        }
        initializeAlarmButton()
    }

    fun rlBackgroundHelperOnClickListener(view: View) {
        if (!userHelper.isHelpingViewsHidden()) {
            clickOutsideOfHelpingViews()
        }
    }

    fun rlButtonLayoutOnClickListener(view: View) {
        if (mainAlarmViewModule.isFirstAlarmCreation()) {
            if (!userHelper.isHelpingViewsHidden()) {
                clickOutsideOfHelpingViews()
            } else {
                userHelper.showHelpingAlertDialog()
            }

        } else {
            previewOfSetting.showSettingsPreviewDialog()
        }
    }

    fun ibAdditionalSettingsOnClickListener(view: View) {
        showAlarmSettingsActivity()
    }

    private fun clickOutsideOfHelpingViews() {
        AlertDialog.Builder(this, R.style.AlertDialogCustomStyle)
                .setTitle(getString(R.string.helperHideDialogTitle))
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    dialog.dismiss()
                    userHelper.hideHelpingViews()
                    showAlarmSettingsActivity()

                }
                .setNegativeButton(getString(R.string.back), { dialog, which -> dialog.dismiss() })
                .create().show()
    }

    private fun initializeAlarmButton() {
        showWeekDaysAndCurrentDay()
    }

    private fun showWeekDaysAndCurrentDay() {
        arrayListOf<TextView>(tvSunday, tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday)
                .forEachIndexed { indexOfDay, dayView ->
                    if (indexOfDay == mainAlarmViewModule.getCurrentDayOfWeek()) {
                        setDayOfWeekTextStyle(dayView)
                    }

                }
    }

    private fun setDayOfWeekTextStyle(tvDayOfWeek: TextView) {
        val ssContent = SpannableString(tvDayOfWeek.text)
        ssContent.setSpan(UnderlineSpan(), 0, ssContent.length, 0)
        tvDayOfWeek.text = ssContent
        tvDayOfWeek.setTextAppearance(R.style.ChosenDayOfTheWeek_TextTheme, this)

    }

    private fun showAlarmSettingsActivity() {
        startActivity(intentSettingActivity)
    }

}