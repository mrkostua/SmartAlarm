package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.content.ClipDescription
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.receivers.AlarmReceiver
import com.mrkostua.mathalarm.alarms.mathAlarm.services.WakeLockService
import com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService.DisplayAlarmService
import com.mrkostua.mathalarm.databinding.ActivityDisplayAlarmBinding
import com.mrkostua.mathalarm.extensions.startMyDragAndDrop
import com.mrkostua.mathalarm.tools.ConstantValues
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_display_alarm.*
import java.util.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */
class DisplayAlarmActivity : DaggerAppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private val taskViewsList = ArrayList<TextView>()

    @Inject
    public lateinit var displayViewModel: DisplayAlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(DataBindingUtil.setContentView(this, R.layout.activity_display_alarm) as ActivityDisplayAlarmBinding) {
            viewModel = displayViewModel
            executePendingBindings()
            initializeTasksViews()
        }
        stopService(Intent(this, WakeLockService::class.java))
        anabelWindowsFlags()

    }

    private fun initializeTasksViews() {
        val r = Random()
        var view: TextView
        for (i in 0..4) {
            view = getTaskView(ViewLocation.BottomRight.getRepresentation(r.nextInt(4)), r.nextInt(130), r.nextInt(130))
            view.text = "yo $i"
            taskViewsList.add(view)
            rlDisplayAlarm.addView(view)
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


    private fun testDragAndDrop(view1: View, view2: View) {
        view1.setOnLongClickListener {
            it.startMyDragAndDrop("how do you do?", View.DragShadowBuilder(it))
            true
        }

        view2.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        v.setBackgroundColor(Color.BLUE)
                        v.invalidate()
                        true
                    } else {
                        false
                    }

                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.setBackgroundColor(Color.GREEN)
                    v.invalidate()
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    v.setBackgroundColor(Color.YELLOW)
                    v.invalidate()
                    true
                }

                DragEvent.ACTION_DROP -> {
                    Toast.makeText(this, event.clipData.getItemAt(0).text, Toast.LENGTH_LONG).show()
                    v.setBackgroundColor(Color.TRANSPARENT)
                    v.invalidate()

                    true

                }

                DragEvent.ACTION_DRAG_ENDED -> {

                    v.setBackgroundColor(Color.TRANSPARENT)
                    v.invalidate()
                    if (event.result) {
                        Toast.makeText(this, "The drop was handled.", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG).show()

                    }
                    true


                }

                else -> false

            }
        }
    }

    private fun getTaskView(location: ViewLocation, topButtonMargin: Int, rightLeftMargin: Int): TextView {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        when (location) {
            ViewLocation.TopLeft -> {
                layoutParams.setMargins(rightLeftMargin, topButtonMargin, 0, 0)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                }
            }
            ViewLocation.TopRight -> {
                layoutParams.setMargins(0, topButtonMargin, rightLeftMargin, 0)
                layoutParams.setMargins(rightLeftMargin, topButtonMargin, 0, 0)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                }
            }
            ViewLocation.BottomLeft -> {
                layoutParams.setMargins(rightLeftMargin, 0, 0, topButtonMargin)
                layoutParams.setMargins(rightLeftMargin, topButtonMargin, 0, 0)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                }
            }
            ViewLocation.BottomRight -> {
                layoutParams.setMargins(0, 0, rightLeftMargin, topButtonMargin)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                }
            }
        }
        val tvTask = TextView(this)
        tvTask.layoutParams = layoutParams
        tvTask.setBackgroundResource(R.drawable.shape_oval)
        tvTask.setPadding(10, 10, 10, 10)
        return tvTask
    }

    private enum class ViewLocation {
        TopLeft, TopRight, BottomLeft, BottomRight;

        fun getRepresentation(viewLocation: ViewLocation) = when (viewLocation) {
            ViewLocation.TopLeft -> 0
            ViewLocation.TopRight -> 1
            ViewLocation.BottomLeft -> 2
            ViewLocation.BottomRight -> 3
        }

        fun getRepresentation(viewLocation: Int) = when (viewLocation) {
            0 -> ViewLocation.TopLeft
            1 -> ViewLocation.TopRight
            2 -> ViewLocation.BottomLeft
            3 -> ViewLocation.BottomRight
            else -> throw UnsupportedOperationException("fuck")

        }


    }

    private fun setOnDragListenerOf(listenerView: View, movingView: View) {
        listenerView.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        v.setBackgroundColor(Color.BLUE)
                        v.invalidate()
                        true
                    } else {
                        false
                    }

                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.setBackgroundColor(Color.GREEN)
                    v.invalidate()
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    v.setBackgroundColor(Color.YELLOW)
                    v.invalidate()
                    true
                }

                DragEvent.ACTION_DROP -> {
                    Toast.makeText(this, event.clipData.getItemAt(0).text, Toast.LENGTH_LONG).show()
                    v.setBackgroundColor(Color.TRANSPARENT)
                    v.invalidate()

                    true

                }

                DragEvent.ACTION_DRAG_ENDED -> {

                    v.setBackgroundColor(Color.TRANSPARENT)
                    v.invalidate()
                    if (event.result) {
                        Toast.makeText(this, "The drop was handled.", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG).show()

                    }
                    true


                }

                else -> false

            }
        }
    }

    private fun setViewAppereance(view: View, color: Int) {
        view.setBackgroundColor(color)
        view.invalidate()
    }


    inner class MyDragShadowBuilder(it: View) : View.DragShadowBuilder(it) {
        private val shadow = (it as ImageView).drawable
        override fun onDrawShadow(canvas: Canvas?) {
            shadow.draw(canvas)
        }

        override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
            val height = view.height / 2
            val width = view.width / 2
            shadow.setBounds(0, 0, width, height)
            outShadowSize?.set(width, height)
            outShadowTouchPoint?.set(width / 2, height / 2)
        }
    }
}
