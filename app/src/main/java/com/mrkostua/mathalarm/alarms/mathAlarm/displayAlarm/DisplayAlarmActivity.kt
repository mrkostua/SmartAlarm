package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipDescription
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.DisplayMetrics
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
import com.mrkostua.mathalarm.tools.NotificationTools
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_display_alarm.*
import kotlinx.android.synthetic.main.custom_dialog_tasks_explenation.view.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */

class DisplayAlarmActivity : DaggerAppCompatActivity(), View.OnDragListener {
    @Inject
    public lateinit var displayViewModel: DisplayAlarmViewModel

    private lateinit var tasksHelper: TaskViewsDisplayHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(DataBindingUtil.setContentView(this, R.layout.activity_display_alarm) as ActivityDisplayAlarmBinding) {
            viewModel = displayViewModel
            executePendingBindings()
        }
        initializeViews()
        showTaskExplanationDialog()
        tasksHelper = TaskViewsDisplayHelper(this)
        getTasksPopulater(tasksHelper).execute(5)
        stopService(Intent(this, WakeLockService::class.java))
        anabelWindowsFlags()
    }

    private fun initializeViews() {
        tvStartedAlarmMessageText.movementMethod = ScrollingMovementMethod()
        bSnoozeAlarm.setOnLongClickListener {
            bSnooze()
            true
        }
    }

    /**
     * will be garbage collected after task finishes.
     */
    @SuppressLint("StaticFieldLeak")
    private fun getTasksPopulater(tasksHelper: TaskViewsDisplayHelper) =
            object : AsyncTask<Int, Boolean, List<TextView>>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                    pbLoadTasks.visibility = View.VISIBLE
                }

                override fun doInBackground(vararg params: Int?): List<TextView> {
                    while (bSnoozeAlarm.measuredHeight == 0 || tvStartedAlarmMessageText.measuredHeight == 0) {
                        Thread.sleep(50)
                    }
                    if (params[0] != null) {
                        return tasksHelper.getInitializedTasksViews(params[0]!!, getTopBoundsInDp(30, 80), getLeftBoundsInDp(40, 80))

                    } else {
                        throw UnsupportedOperationException("doInBackground params argument is empty")
                    }
                }

                override fun onPostExecute(result: List<TextView>?) {
                    super.onPostExecute(result)
                    pbLoadTasks.visibility = View.GONE
                    if (result != null && result.isNotEmpty()) {
                        tasksHelper.addTasksViewsToLayout(rlDisplayAlarm, result, this@DisplayAlarmActivity)
                    } else {
                        NotificationTools(this@DisplayAlarmActivity).showToastMessage("please push snooze button")
                    }
                }

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


    private fun getTopBoundsInDp(taskHeightTopDp: Int, taskHeightBottomDp: Int) = Pair(convertPixelToDp(tvStartedAlarmMessageText.measuredHeight) + taskHeightTopDp,
            this.resources.configuration.screenHeightDp - (convertPixelToDp(bSnoozeAlarm.measuredHeight) + taskHeightBottomDp))

    private fun getLeftBoundsInDp(taskLeftStartDp: Int, taskLeftEndDp: Int): Pair<Int, Int> = Pair(taskLeftStartDp, this.resources.configuration.screenWidthDp - taskLeftEndDp)


    private fun convertPixelToDp(pixels: Int): Int {
        val metrics = this.resources.displayMetrics
        val dp = pixels / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        return dp.toInt()
    }

    private fun showTaskExplanationDialog() {
        if (displayViewModel.isShowExplanationDialog.get()) {
            val explanationView = layoutInflater.inflate(R.layout.custom_dialog_tasks_explenation, null)
            AlertDialog.Builder(this, R.style.AlertDialogCustomStyle)
                    .setTitle("How to stop the the alarm?")
                    .setView(explanationView)
                    .setPositiveButton("got it", { dialog, which ->
                        displayViewModel.setIsShowExplanationDialog(!explanationView.cbShowDialogAgain.isChecked)
                        dialog.dismiss()
                    }).create().show()

        }
    }

    private fun bSnooze() {
        sendBroadcast(Intent(ConstantValues.SNOOZE_ACTION)
                .setClass(this, AlarmReceiver::class.java))
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
