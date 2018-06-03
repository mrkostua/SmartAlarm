package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.content.ClipDescription
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.DragEvent
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService
import com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService.DisplayAlarmService
import com.mrkostua.mathalarm.databinding.ActivityDisplayAlarmBinding
import com.mrkostua.mathalarm.extensions.setTextAppearance
import com.mrkostua.mathalarm.extensions.startMyDragAndDrop
import com.mrkostua.mathalarm.tools.ConstantValues
import com.mrkostua.mathalarm.tools.ShowLogs
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_display_alarm.*
import java.util.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */

class DisplayAlarmActivity : DaggerAppCompatActivity(), View.OnDragListener {
    private val TAG = this.javaClass.simpleName
    private val taskViewsList = ArrayList<TextView>()
    @Inject
    public lateinit var displayViewModel: DisplayAlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(DataBindingUtil.setContentView(this, R.layout.activity_display_alarm) as ActivityDisplayAlarmBinding) {
            viewModel = displayViewModel
            executePendingBindings()
        }
        initializeTasksViews()
        initializeDragDropListeners()
        stopService(Intent(this, WakeLockService::class.java))
        anabelWindowsFlags()

    }

    private var draggingElementNumber = -1
    private fun initializeDragDropListeners() {
        taskViewsList.sortBy { it.text.toString().toInt() }
        taskViewsList.forEach { view ->
            view.setOnDragListener(this)
            view.setOnLongClickListener {
                setViewAppearance(view, R.drawable.task_shape_moving)
                it.startMyDragAndDrop(view.text.toString(), View.DragShadowBuilder(it))
                draggingElementNumber = view.text.toString().toInt()
                true
            }
        }
    }

    override fun onDrag(v: View, event: DragEvent) = when (event.action) {
        DragEvent.ACTION_DRAG_STARTED -> {
            if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                if (draggingElementNumber != (v as TextView).text.toString().toInt()) {
                    setViewAppearance(v, R.drawable.task_shape_posible_options)
                }
                isProperTaskChosen(v)
            } else {
                false
            }

        }
        DragEvent.ACTION_DRAG_ENTERED -> {
            if (draggingElementNumber != (v as TextView).text.toString().toInt()) {
                actionDragEntered(v)
            }
            true
        }
        DragEvent.ACTION_DRAG_LOCATION -> {
            true
        }
        DragEvent.ACTION_DRAG_EXITED -> {
            if (draggingElementNumber != (v as TextView).text.toString().toInt()) {
                setViewAppearance(v, R.drawable.task_shape_posible_options)
            }
            true
        }
        DragEvent.ACTION_DROP -> {

            if (isProperTaskChosen(v)) {
                drawLineFromTaskToTask(v, taskViewsList[getTaskIndexByNumber(draggingElementNumber) + 1])
                //TODO set done tasks listeners to NULL so user can't drag it
            }
            true


        }
        DragEvent.ACTION_DRAG_ENDED -> {
            //TODO Drag Ended will be called always after dragStarted

            if (event.result) {
                ShowLogs.log(TAG, "The drop was handled.")

            } else {
                ShowLogs.log(TAG, "The drop didn't work.")
            }
            resetDraggingColorsStates()

            true


        }
        else -> false

    }

    private fun drawLineFromTaskToTask(v1: View, v2: View) {
        //TODO("not implemented")
        ShowLogs.log(TAG, "drawLineFromTaskToTask() Draw something ??? ")

    }

    private fun resetDraggingColorsStates() {
        draggingElementNumber = -1
        taskViewsList.forEach {
            setViewAppearance(it, R.drawable.shape_oval)
        }
    }

    private fun isProperTaskChosen(v: View): Boolean {
        if (draggingElementNumber == -1) {
            throw UnsupportedOperationException("Something is wrong")
        }
        return when (draggingElementNumber) {
            taskViewsList[taskViewsList.lastIndex].text.toString().toInt() -> {
                false
            }
            else -> {
                taskViewsList[getTaskIndexByNumber(draggingElementNumber) + 1].text.toString().toInt() == (v as TextView).text.toString().toInt()
            }
        }
    }

    private fun actionDragEntered(v: View) {
        if (draggingElementNumber == -1) {
            throw UnsupportedOperationException("Something is wrong")
        }
        when (draggingElementNumber) {
            taskViewsList[taskViewsList.lastIndex].text.toString().toInt() -> {
                setViewAppearance(v, R.drawable.task_shape_wrong)
            }
            else -> {
                if (taskViewsList[getTaskIndexByNumber(draggingElementNumber) + 1].text.toString().toInt() == (v as TextView).text.toString().toInt()) {
                    setViewAppearance(v, R.drawable.task_shape_correct)
                } else {
                    setViewAppearance(v, R.drawable.task_shape_wrong)

                }
            }
        }
    }

    private fun getTaskIndexByNumber(number: Int): Int {
        taskViewsList.forEachIndexed { index, textView ->
            if (number == textView.text.toString().toInt()) {
                return index
            }
        }
        throw UnsupportedOperationException("getTaskIndexByNumber() wrong argument, taskViewList don't contain this element")
    }

    private fun initializeTasksViews() {
        var view: TextView
        val randomLocation = displayViewModel.getUniqueRandomValues(0, 3, 4)
        val rTopBottomMargin = displayViewModel.getUniqueRandomValues(140, 250, 4)
        val rRightLeftMargin = displayViewModel.getUniqueRandomValues(40, 150, 4)
        val randomTasksNumber = displayViewModel.getUniqueRandomValues(0, 10, 5)
        //adding 5th task view
        randomLocation.add(1)
        rTopBottomMargin.add(250)
        rRightLeftMargin.add(150)
        for (index in 0 until randomLocation.size) {
            view = getTaskView(TaskViewLocation.TopLeft.getRepresentation(randomLocation[index]),
                    getPixelValue(rTopBottomMargin[index]), getPixelValue((rRightLeftMargin[index])))
            view.text = randomTasksNumber[index].toString()
            view.id = index

            rlDisplayAlarm.addView(view)
            rlDisplayAlarm.requestLayout()
            taskViewsList.add(view)
        }

    }

    fun bStopAlarmOnClickListener(view: View) {
        finishDisplayingAlarm()

    }

    fun bSnoozeAlarmOnClickListener(view: View) {
        sendBroadcast(Intent(ConstantValues.SNOOZE_ACTION)
                .setClass(this, AlarmReceiver::class.java))
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

    private fun setViewAppearance(view: View, resId: Int) {
        view.setBackgroundResource(resId)
        view.invalidate()
    }

    private fun getTaskView(location: TaskViewLocation, topBottomMarginPixels: Int, rightLeftMarginPixels: Int): TextView {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        when (location) {
            TaskViewLocation.TopLeft -> {
                layoutParams.setMargins(rightLeftMarginPixels, topBottomMarginPixels, 0, 0)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                    layoutParams.marginStart = rightLeftMarginPixels
                }
            }
            TaskViewLocation.TopRight -> {
                layoutParams.setMargins(0, topBottomMarginPixels, rightLeftMarginPixels, 0)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                    layoutParams.marginEnd = rightLeftMarginPixels

                }
            }
            TaskViewLocation.BottomLeft -> {
                layoutParams.setMargins(rightLeftMarginPixels, 0, 0, topBottomMarginPixels)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                    layoutParams.marginStart = rightLeftMarginPixels
                }
            }
            TaskViewLocation.BottomRight -> {
                layoutParams.setMargins(0, 0, rightLeftMarginPixels, topBottomMarginPixels)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                    layoutParams.marginEnd = rightLeftMarginPixels
                }
            }
        }
        val tvTask = TextView(this)
        tvTask.layoutParams = layoutParams
        tvTask.setBackgroundResource(R.drawable.shape_oval)
        tvTask.setPadding(getPixelValue(10), getPixelValue(10), getPixelValue(10), getPixelValue(10))
        tvTask.setTextAppearance(R.style.displayedTasksTextStyle, this)
        return tvTask
    }

    private fun getPixelValue(dp: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics).toInt()

}
