package com.mrkostua.mathalarm.alarmSettings.optionSetTime

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.Interfaces.SettingsFragmentInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.injections.scope.FragmentScope
import com.mrkostua.mathalarm.tools.NotificationTools
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_option_set_time.*
import javax.inject.Inject

@FragmentScope
class FragmentOptionSetTime @Inject constructor() : DaggerFragment(), SettingsFragmentInterface, OptionSetTimeContract.View {
    private val TAG = this.javaClass.simpleName
    override lateinit var fragmentContext: Context
    @Inject
    public lateinit var notificationTools: NotificationTools

    @Inject
    public lateinit var presenter: OptionSetTimeContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = activity!!.applicationContext

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_option_set_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this)
        initializeDependOnViewVariables(view)
        notificationTools.showToastMessage(getString(R.string.alarmTimeLimitationMessage))

    }

    override fun onResume() {
        super.onResume()
        initializeTimePicker(presenter.getSavedHour(), presenter.getSavedMinute())
        saveSettingsInSharedPreferences()
    }

    override fun initializeDependOnViewVariables(view: View?) {
    }

    override fun saveSettingsInSharedPreferences() {
        tpSetAlarmTime.setOnTimeChangedListener { tp, hourOfDay, minute ->
            presenter.saveTime(hourOfDay, minute)
        }
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

