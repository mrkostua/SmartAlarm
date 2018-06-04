package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.content.ClipDescription
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.DragEvent
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService
import com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService.DisplayAlarmService
import com.mrkostua.mathalarm.databinding.ActivityDisplayAlarmBinding
import com.mrkostua.mathalarm.tools.ConstantValues
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_display_alarm.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */

class DisplayAlarmActivity : DaggerAppCompatActivity(), View.OnDragListener {
    private val TAG = this.javaClass.simpleName
    @Inject
    public lateinit var displayViewModel: DisplayAlarmViewModel

    private lateinit var tasksHelper: TaskViewsDisplayHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(DataBindingUtil.setContentView(this, R.layout.activity_display_alarm) as ActivityDisplayAlarmBinding) {
            viewModel = displayViewModel
            executePendingBindings()
        }
        tasksHelper = TaskViewsDisplayHelper(this, 5)
        tasksHelper.addTasksViewsToLayout(rlDisplayAlarm, this)

        stopService(Intent(this, WakeLockService::class.java))
        anabelWindowsFlags()

    }

    fun bSnoozeAlarmOnClickListener(view: View) {
        sendBroadcast(Intent(ConstantValues.SNOOZE_ACTION)
                .setClass(this, AlarmReceiver::class.java))
    }

    override fun onDrag(v: View, event: DragEvent) = when (event.action) {
        DragEvent.ACTION_DRAG_STARTED -> {
            if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                tasksHelper.actionDragStarted(v as TextView)
            } else {
                false
            }

        }
        DragEvent.ACTION_DRAG_ENTERED -> {
            tasksHelper.actionDragEntered(v as TextView)
            true
        }
        DragEvent.ACTION_DRAG_LOCATION -> {
            //TODO draw line during dragging or after successful dropping
            true
        }
        DragEvent.ACTION_DRAG_EXITED -> {
            tasksHelper.actionDragExited(v as TextView)
            true
        }
        DragEvent.ACTION_DROP -> {
            tasksHelper.actionDrop(v as TextView)
            if (tasksHelper.isAllTasksDone()) {
                finishDisplayingAlarm()
            }
            true
        }
        DragEvent.ACTION_DRAG_ENDED -> {
            tasksHelper.actionDragEnded(v)
            true

        }
        else -> false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?) =
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> true
                KeyEvent.KEYCODE_VOLUME_UP -> true
                KeyEvent.KEYCODE_VOLUME_DOWN -> true
                else -> super.onKeyDown(keyCode, event)
            }

    private fun finishDisplayingAlarm() {
        stopDisplayAlarmService()
        startMainActivity()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainAlarmActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

    }

    private fun stopDisplayAlarmService() {
        stopService(Intent(this, DisplayAlarmService::class.java))

    }

    private fun anabelWindowsFlags() {
        /*flags to show activity if user device have keyguard*/
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                /*flags to turn screen on */
                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    }

}
