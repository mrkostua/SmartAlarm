package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarms.mathAlarm.Alarm_Receiver
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService.DisplayAlarmService
import com.mrkostua.mathalarm.databinding.ActivityDisplayAlarmBinding
import com.mrkostua.mathalarm.tools.ConstantValues
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */
class DisplayAlarmActivity : DaggerAppCompatActivity() {
    private val TAG = this.javaClass.simpleName

    @Inject
    public lateinit var displayViewModel: DisplayAlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDisplayAlarmBinding = DataBindingUtil.setContentView(this, R.layout.activity_display_alarm)
        with(binding) {
            viewModel = displayViewModel
            executePendingBindings()
        }

        anabelWindowsFlags()
    }

    override fun onDestroy() {
        super.onDestroy()
        finishDisplayingAlarm()

    }

    fun bStopAlarmOnClickListener(view: View) {
        finish()
        //TODO fix problem mainActivity staring 2 times blinking!!
    }

    fun bSnoozeAlarmOnClickListener(view: View) {
        sendBroadcast(Intent(ConstantValues.SNOOZE_ACTION)
                .setClass(this, Alarm_Receiver::class.java))
    }

    //TODO maybe add some fun as pushing KEYCODE_VOLUME_UP alarm will be snoozed for 5 m
    override fun onKeyDown(keyCode: Int, event: KeyEvent?) =
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> true
                KeyEvent.KEYCODE_VOLUME_UP -> true
                KeyEvent.KEYCODE_VOLUME_DOWN -> true
                else -> super.onKeyDown(keyCode, event)
            }

    private fun finishDisplayingAlarm() {
        stopMathService()
        startMainActivity()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainAlarmActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

    }

    private fun stopMathService() {
        stopService(Intent(this, DisplayAlarmService::class.java))

    }

    /**
     * @FLAG_DISMISS_KEYGUARD
     * Window flag: when set the window will cause the keyguard to be dismissed, only if it is
     * not a secure lock keyguard. Because such a keyguard is not needed for security, it will
     * never re-appear if the user navigates to another window (in contrast to FLAG_SHOW_WHEN_LOCKED,
     * which will only temporarily hide both secure and non-secure keyguards but ensure they reappear
     * when the user moves to another UI that doesn't hide them). If the keyguard is currently active
     * and is secure (requires an unlock pattern) than the user will still need to confirm it before
     * seeing this window, unless FLAG_SHOW_WHEN_LOCKED has also been set.
     */
    private fun anabelWindowsFlags() {
        /*flags to show activity if user device have keyguard*/
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                /*flags to turn screen on */
                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    }
}