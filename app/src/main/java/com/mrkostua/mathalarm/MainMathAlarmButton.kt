package com.mrkostua.mathalarm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * @author Kostiantyn on 21.11.2017.
 */

class MainMathAlarmButton : AppCompatActivity() {
    private lateinit var rlBackgroundLayout: RelativeLayout
    private lateinit var rlButtonLayout: RelativeLayout

    private lateinit var tvAlarmTime: TextView
    /**
    lateinit - this lets you have non-nullable properties in your Activity that you
     don't initialize when the constructor is called, but only later,
      in the onCreate method. You do however, in this case, take
      responsibility for initializing the variables before using
      them the first time, otherwise you'll get an exception at runtime.
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set layout to the full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main_alarm)

        initializeViews()
    }


    private fun initializeViews() {
        rlBackgroundLayout = findViewById(R.id.rlBackgroundLayout) as RelativeLayout
        rlButtonLayout = findViewById(R.id.rlButtonLayout) as RelativeLayout

        tvAlarmTime = findViewById(R.id.tvAlarmTime) as TextView

    }

    private fun intializeAlarm(){
     //todo method for checking time and setting night day theme

    }
}
