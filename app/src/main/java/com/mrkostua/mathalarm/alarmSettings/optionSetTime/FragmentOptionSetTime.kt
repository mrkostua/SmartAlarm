package com.mrkostua.mathalarm.alarmSettings.optionSetTime

import android.app.Fragment
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.tools.NotificationsTools
import com.mrkostua.mathalarm.tools.ShowLogs
import kotlinx.android.synthetic.main.fragment_option_set_time.*

/**
 * TODO test on with 2 timePicker styles and maybe improve some design
 * TODO is there place for injection in View using MVP design pattern
 */
class FragmentOptionSetTime : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface, OptionSetTimeContract.View {
    private val TAG = this.javaClass.simpleName

    override lateinit var presenter: OptionSetTimeContract.Presenter
    override lateinit var fragmentContext: Context

    private lateinit var notificationTools: NotificationsTools

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity.applicationContext
        initializeDependOnContextVariables(fragmentContext)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_option_set_time, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        initializeDependOnViewVariables(view)
        notificationTools.showToastMessage(getString(R.string.alarmTimeLimitationMessage))

    }

    override fun onResume() {
        super.onResume()
        initializeTimePicker(presenter.getSavedHour(), presenter.getSavedMinute())
        saveSettingsInSharedPreferences()
    }

    override fun initializeDependOnContextVariables(context: Context) {
        notificationTools = NotificationsTools(fragmentContext)

    }

    override fun initializeDependOnViewVariables(view: View?) {
    }

    override fun saveSettingsInSharedPreferences() {
        tpSetAlarmTime.setOnTimeChangedListener({ tp, hourOfDay, minute ->
            presenter.saveTime(hourOfDay, minute)
        })
    }

    override fun showTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int) {
        val timeLeft = presenter.getTimeUntilAlarmBoom(hourOfDay, minutes)
        tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, timeLeft.first, timeLeft.second)

    }

    @Suppress("DEPRECATION")
    override fun initializeTimePicker(hourOfDay: Int, minutes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tpSetAlarmTime.hour = hourOfDay
            tpSetAlarmTime.minute = minutes
            tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, tpSetAlarmTime.hour, tpSetAlarmTime.minute)

        } else {
            tpSetAlarmTime.currentHour = hourOfDay
            tpSetAlarmTime.currentMinute = minutes

        }
        showTimeUntilAlarmBoom(hourOfDay, minutes)
    }

}

