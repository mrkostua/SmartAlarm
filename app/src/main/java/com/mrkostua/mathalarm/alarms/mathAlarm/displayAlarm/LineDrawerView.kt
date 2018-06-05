package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.mrkostua.mathalarm.tools.ShowLogs

/**
 * @author Kostiantyn Prysiazhnyi on 6/4/2018.
 */
class LineDrawerView(context: Context, private val isLine: Boolean) : View(context) {
    private val fromLocation = IntArray(2)
    private val toLocation = IntArray(2)
    private val paintLine = Paint()
    private var xPoint: Float = 0.0f
    private var yPoint: Float = 0.0f

    constructor(context: Context) : this(context, true)
    constructor(context: Context, xPoint: Float, yPoint: Float) : this(context, false) {
        this.xPoint = xPoint
        this.yPoint = yPoint
    }

    fun setCoordinates(fromView: View, toView: View) {
        //todo move arguments to constructor
        fromView.getLocationOnScreen(fromLocation)
        toView.getLocationOnScreen(toLocation)
        val rect = Rect()
        fromView.getGlobalVisibleRect(rect)
        ShowLogs.log(this.javaClass.simpleName, "setCoordinates top ${rect.top} +" +
                " bottom ${rect.bottom} +" +
                " left ${rect.left} +" +
                " right ${rect.right}" +
                " width ${rect.width()}" +
                " height ${rect.height()}")

    }


    init {
        paintLine.color = Color.RED
        paintLine.strokeWidth = 5.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        ShowLogs.log(this.javaClass.simpleName, "onDraw bla bla")
        if (isLine) {
            drawLine(canvas)
        } else {
            drawPoint(canvas)
        }
    }

    private fun drawLine(canvas: Canvas?) {
        ShowLogs.log(this.javaClass.simpleName, "onDraw numbers : ${fromLocation[0].toFloat()} + " +
                " ${fromLocation[1].toFloat()} +  ${toLocation[0].toFloat()} +  ${toLocation[0].toFloat()} +")

        canvas?.drawLine(0.toFloat(), 0.toFloat(), toLocation[0].toFloat(),
                toLocation[1].toFloat(), paintLine)
    }

    private fun drawPoint(canvas: Canvas?) {
        canvas?.drawPoint(xPoint, yPoint,paintLine)
    }


}