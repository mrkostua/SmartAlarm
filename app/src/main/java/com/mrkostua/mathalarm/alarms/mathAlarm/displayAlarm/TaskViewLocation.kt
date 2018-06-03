package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

/**
 * @author Kostiantyn Prysiazhnyi on 6/2/2018.
 */
enum class TaskViewLocation {
    TopLeft, TopRight, BottomLeft, BottomRight;

    fun getRepresentation(viewLocation: TaskViewLocation) = when (viewLocation) {
        TaskViewLocation.TopLeft -> 0
        TaskViewLocation.TopRight -> 1
        TaskViewLocation.BottomLeft -> 2
        TaskViewLocation.BottomRight -> 3
    }

    fun getRepresentation(viewLocation: Int) = when (viewLocation) {
        0 -> TaskViewLocation.TopLeft
        1 -> TaskViewLocation.TopRight
        2 -> TaskViewLocation.BottomLeft
        3 -> TaskViewLocation.BottomRight
        else -> throw UnsupportedOperationException("fuck number is $viewLocation")

    }
}