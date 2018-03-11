package com.mrkostua.mathalarm.AlarmSettings

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
import com.mrkostua.mathalarm.Tools.PreferencesConstants
import com.mrkostua.mathalarm.Tools.ShowLogs
import com.mrkostua.mathalarm.extensions.app
import com.mrkostua.mathalarm.extensions.get
import com.mrkostua.mathalarm.extensions.set
import kotlinx.android.synthetic.main.fragment_option_set_time.*
import javax.inject.Inject

/**
 * TODO test on with 2 timePicker styles and maybe improve some design
 */
class FragmentOptionSetTime : Fragment(), SettingsFragmentInterface, KotlinActivitiesInterface, AddInjection {
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
        initializeTimePicker()
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
            sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue()] = hourOfDay
            sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue()] = minute
        })
    }

    private fun showTimeUntilAlarmBoom(hourOfDay: Int, minutes: Int) {
        val (hoursUntilBoob, minutesUntilBoom) = notificationTools.getTimeUntilAlarmBoob(hourOfDay, minutes)
        tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, hoursUntilBoob, minutesUntilBoom)

    }

    @Suppress("DEPRECATION")
    private fun initializeTimePicker() {
        val hours = sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue(), PreferencesConstants.ALARM_HOURS.getDefaultIntValue()]
                ?: PreferencesConstants.ALARM_HOURS.getDefaultIntValue()
        val minutes = sharedPreferences[PreferencesConstants.ALARM_MINUTES.getKeyValue(), PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()]
                ?: PreferencesConstants.ALARM_MINUTES.getDefaultIntValue()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tpSetAlarmTime.hour = hours
            tpSetAlarmTime.minute = minutes
            tvTimeUntilAlarmBoom.text = resources.getString(R.string.timeUntilAlarmBoom, tpSetAlarmTime.hour, tpSetAlarmTime.minute)

        } else {
            tpSetAlarmTime.currentHour = hours
            tpSetAlarmTime.currentMinute = minutes

        }
        showTimeUntilAlarmBoom(hours, minutes)
    }

}

