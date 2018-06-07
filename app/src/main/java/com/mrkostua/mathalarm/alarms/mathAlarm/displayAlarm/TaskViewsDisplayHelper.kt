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
import com.mrkostua.mathalarm.tools.ShowLogs

/**
 * @author Kostiantyn Prysiazhnyi on 6/4/2018.
 */
class TaskViewsDisplayHelper(private val activityContext: Context, tasksAmount: Int) {
    private val taskViewsList = getInitializedTasksViews(tasksAmount)
    private val initialTasksCount: Int = taskViewsList.size
    private var draggingTaskViewId = -1
    private val vibrator = activityContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

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

    private fun getPixelValue(dp: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), activityContext.resources.displayMetrics).toInt()

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
        val tvTask = TextView(activityContext)
        tvTask.layoutParams = layoutParams
        tvTask.setBackgroundResource(R.drawable.shape_oval)
        tvTask.setPadding(getPixelValue(20), getPixelValue(10), getPixelValue(20), getPixelValue(10))
        tvTask.setTextAppearance(R.style.displayedTasksTextStyle, activityContext)
        return tvTask
    }

    private fun setViewAppearance(view: View, resId: Int) {
        view.setBackgroundResource(resId)
        view.invalidate()
    }

    private fun getInitializedTasksViews(tasksAmount: Int): ArrayList<TextView> {
        var view: TextView
        val taskViewsList = ArrayList<TextView>()
        val randomLocation: ArrayList<Int>
        if (tasksAmount > 4) {
            randomLocation = CustomRandom.getUniqueRandomValues(0, 3, 4)
            for (i in 0 until (tasksAmount - 4)) {
                ShowLogs.log(this.javaClass.simpleName, "in for loop : " + randomLocation.size)
                randomLocation.add(CustomRandom.getUniqueRandomValues(0, 3, 1)[0])
            }
        } else {
            randomLocation = CustomRandom.getUniqueRandomValues(0, 3, tasksAmount)
        }
        val rTopBottomMargin = CustomRandom.getUniqueRandomValues(140, 250, tasksAmount)
        val rRightLeftMargin = CustomRandom.getUniqueRandomValues(40, 150, tasksAmount)
        val randomTasksNumber = CustomRandom.getUniqueRandomValues(0, 9, tasksAmount)

        for (index in 0 until randomLocation.size) {
            view = getTaskView(TaskViewLocation.TopLeft.getRepresentation(randomLocation[index]),
                    getPixelValue(rTopBottomMargin[index]),
                    getPixelValue((rRightLeftMargin[index])))
            view.text = randomTasksNumber[index].toString()
            view.id = index
            taskViewsList.add(view)
        }
        taskViewsList.sortBy { it.text.toString().toInt() }
        return taskViewsList
    }

    fun addTasksViewsToLayout(rl: RelativeLayout, dragDropListener: View.OnDragListener) {
        taskViewsList.forEach {
            initializeDragDropListeners(it)
            it.setOnDragListener(dragDropListener)
            rl.addView(it)
            rl.requestLayout()
        }

    }

    private fun initializeDragDropListeners(view: TextView) {
        view.setOnLongClickListener {
            vibrateFor(200)
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