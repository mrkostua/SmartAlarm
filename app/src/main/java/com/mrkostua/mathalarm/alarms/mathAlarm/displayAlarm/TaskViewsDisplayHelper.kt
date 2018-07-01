package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.extensions.setTextAppearance
import com.mrkostua.mathalarm.extensions.startMyDragAndDrop
import com.mrkostua.mathalarm.tools.CustomRandom
import java.util.*


/**
 * @author Kostiantyn Prysiazhnyi on 6/4/2018.
 */
class TaskViewsDisplayHelper(private val activityContext: Context) {
    private val taskViewsList = ArrayList<TextView>()
    private val initialTasksCount: Int = taskViewsList.size
    private var draggingTaskViewId = -1
    private val vibrator = activityContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun getInitializedTasksViews(tasksAmount: Int, topBounds: Pair<Int, Int>, leftBounds: Pair<Int, Int>): ArrayList<TextView> {
        if (tasksAmount > 5) {
            throw UnsupportedOperationException("could be too much tasks for small screens (error for generating elements with borders)")
        }
        taskViewsList.clear()
        var view: TextView
        var borderInDp = (topBounds.second - topBounds.first) / tasksAmount
        val randomTasksNumber = CustomRandom.getUniqueRandomValues(0, 9, tasksAmount)
        val rTopMargin = CustomRandom.getUniqueRandomBorderedValues(topBounds.first, topBounds.second,
                tasksAmount, borderInDp - borderInDp / 2)
        borderInDp = (leftBounds.second - leftBounds.first) / tasksAmount
        val rLeftMargin = CustomRandom.getUniqueRandomBorderedValues(leftBounds.first, leftBounds.second,
                tasksAmount, borderInDp - borderInDp / 2)
        for (index in 0 until randomTasksNumber.size) {
            view = getTaskView(convertDpToPixel(rTopMargin[index]),
                    convertDpToPixel((rLeftMargin[index])))
            view.text = randomTasksNumber[index].toString()
            view.id = index
            taskViewsList.add(view)
        }
        taskViewsList.sortBy { it.text.toString().toInt() }
        return taskViewsList
    }

    fun actionDragStarted(toDropView: TextView) = if (draggingTaskViewId != toDropView.id) {
        setViewAppearance(toDropView, R.drawable.task_shape_posible_options)
        true
    } else {
        false
    }

    fun actionDragEntered(onDropView: TextView) {
        if (draggingTaskViewId == -1) {
            throw UnsupportedOperationException("draggingTaskViewId wasn't set in the LongClickListener")
        }
        if (draggingTaskViewId != onDropView.id) {
            when (draggingTaskViewId) {
                taskViewsList[0].id -> {
                    if (taskViewsList[1].id == onDropView.id) {
                        setViewAppearance(onDropView, R.drawable.task_shape_correct)
                    } else {
                        setViewAppearance(onDropView, R.drawable.task_shape_wrong)
                    }
                }
                else -> {
                    setViewAppearance(onDropView, R.drawable.task_shape_wrong)
                }
            }
        }
    }

    fun actionDragExited(onDropView: TextView) {
        if (draggingTaskViewId != onDropView.id) {
            setViewAppearance(onDropView, R.drawable.task_shape_posible_options)
        }
    }

    fun actionDrop(onDropView: TextView) {
        if (isProperTaskChosen(onDropView)) {
            setTaskViewToDone(getTaskIndexByViewId(draggingTaskViewId))
        }

    }

    fun isAllTasksDone() = if (taskViewsList.size == 1) {
        setTaskViewToDone(0)
        true
    } else {
        false
    }

    fun actionDragEnded(onDropView: View) {
        if (taskViewsList.size == initialTasksCount) {
            resetDraggingColorsStates()
            vibrator.cancel()
        }
    }

    fun addTasksViewsToLayout(rl: RelativeLayout, tasks: List<TextView>, dragDropListener: View.OnDragListener) {
        tasks.forEach {
            initializeDragDropListeners(it)
            it.setOnDragListener(dragDropListener)
            rl.addView(it)
            rl.requestLayout()
        }

    }

    private fun convertDpToPixel(dp: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), activityContext.resources.displayMetrics).toInt()

    private fun getTaskView(topMarginPixels: Int, leftMarginPixels: Int): TextView {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        val tvTask = TextView(activityContext)
        with(layoutParams) {
            setMargins(leftMarginPixels, topMarginPixels, 0, 0)
            addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                marginStart = leftMarginPixels
            }
            tvTask.layoutParams = this
        }
        tvTask.setBackgroundResource(R.drawable.shape_oval)
        tvTask.setPadding(convertDpToPixel(20), convertDpToPixel(10), convertDpToPixel(20), convertDpToPixel(10))
        tvTask.setTextAppearance(R.style.displayedTasksTextStyle, activityContext)
        return tvTask
    }

    private fun setViewAppearance(view: View, resId: Int) {
        view.setBackgroundResource(resId)
        view.invalidate()
    }


    private fun initializeDragDropListeners(view: TextView) {
        view.setOnLongClickListener {
            vibrateFor(100)
            setViewAppearance(view, R.drawable.task_shape_moving)
            draggingTaskViewId = view.id
            it.startMyDragAndDrop(view.text.toString(), View.DragShadowBuilder(it))
            true
        }
    }

    private fun vibrateFor(millSeconds: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(millSeconds.toLong(), VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(millSeconds.toLong())
        }
    }

    private fun getTaskIndexByViewId(id: Int): Int {
        taskViewsList.forEachIndexed { index, taskView ->
            if (id == taskView.id) {
                return index
            }
        }
        throw UnsupportedOperationException("getTaskIndexByNumber() wrong argument, taskViewList don't contain this element")
    }

    private fun isProperTaskChosen(enteredView: TextView): Boolean {
        return when (draggingTaskViewId) {
            taskViewsList[0].id -> {
                taskViewsList[1].id == enteredView.id
            }
            else -> false

        }
    }

    private fun setTaskViewToDone(taskViewIndex: Int) {
        taskViewsList[taskViewIndex].setOnLongClickListener(null)
        taskViewsList[taskViewIndex].setOnDragListener(null)
        setViewAppearance(taskViewsList.removeAt(taskViewIndex), R.drawable.shape_oval)
    }

    private fun resetDraggingColorsStates() {
        draggingTaskViewId = -1
        taskViewsList.forEach {
            setViewAppearance(it, R.drawable.shape_oval)
        }
    }
}