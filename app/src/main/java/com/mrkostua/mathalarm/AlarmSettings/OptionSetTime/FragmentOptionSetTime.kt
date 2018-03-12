package com.mrkostua.mathalarm.AlarmSettings.OptionSetTime

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.Interfaces.AddInjection
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.ShowLogs
import com.mrkostua.mathalarm.extensions.app
import kotlinx.android.synthetic.main.fragment_option_set_time.*
import javax.inject.Inject

/**
 * TODO test on with 2 timePicker styles and maybe improve some design
 */
class FragmentOptionSetTime : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface, AddInjection, OptionSetTimeContract.View {
    override lateinit var presenter: OptionSetTimeContract.Presenter

    private val TAG = this.javaClass.simpleName
    @Inject
    public lateinit var sharedPreferences: SharedPreferences

    override lateinit var fragmentContext: Context
    private lateinit var notificationTools: NotificationTools

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

    override fun onAttach(context: Context?) {
        injectDependencies()
        super.onAttach(context)
    }

    override fun injectDependencies() {
        app.applicationComponent.inject(this)

    }

    override fun initializeDependOnContextVariables(context: Context) {
        notificationTools = NotificationTools(fragmentContext)

    }

    override fun initializeDependOnViewVariables(view: View?) {
    }

    override fun saveSettingsInSharedPreferences() {
        tpSetAlarmTime.setOnTimeChangedListener({ timePicker, hourOfDay, minute ->
            showTimeUntilAlarmBoom(hourOfDay, minute)
            presenter.saveTime(hourOfDay, minute)
        })
    }

    private fun showTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int) {
        val (hoursUntilBoob, minutesUntilBoom) = notificationTools.getTimeUntilAlarmBoob(hourOfDay, minutes)
        tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, hoursUntilBoob, minutesUntilBoom)

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

