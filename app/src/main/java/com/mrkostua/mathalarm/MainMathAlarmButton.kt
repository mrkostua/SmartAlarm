package com.mrkostua.mathalarm

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Kostiantyn on 21.11.2017.
 */
//TODO Koltin is there still need for butterKnife library ?
//todo read more about kotlin !!!!! the way it work and compile
class MainMathAlarmButton : AppCompatActivity() {
    private lateinit var rlBackgroundLayout: RelativeLayout
    private lateinit var rlButtonLayout: RelativeLayout
    private lateinit var tvAlarmTime: TextView

    private lateinit var ibAdditionalSettings : ImageButton

    private val listDaysOfWeekViews: ArrayList<TextView> = object : ArrayList<TextView>(7) {}
    private var lastAlarmData: LastAlarmData = object : LastAlarmData(this) {}

    private val calendar: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set layout to the full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main_alarm)

        initializeViews()
        calendar.timeInMillis = System.currentTimeMillis()


        initializeAlarmButton()
    }

    private fun initializeViews() {
        rlButtonLayout = findViewById(R.id.rlButtonLayout) as RelativeLayout

        tvAlarmTime = findViewById(R.id.tvAlarmTime) as TextView
        ibAdditionalSettings = findViewById(R.id.ibAdditionalSettings) as ImageButton

    }

    /*create basic 2 styles for night and day time - >
        here create variable and set in the beginning by checking currentTime than depends
         on this style variable all others colors must be changed automatically

     */
    private fun setThemeForAlarmButtonLayout(){


        if(calendar.get(Calendar.AM_PM) == Calendar.AM){
            rlBackgroundLayout
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ibAdditionalSettings.setBackgroundColor(resources.getColor(R.color.main_layout_backgroundDay,null))
            }
            //set day style
        } else {
            //set dark style (night)
        }
    }

//    private fun getAlarmButtonLayoutStyle(style : Int) : RelativeLayout{
//        val someLayout = RelativeLayout(context, null, R.style.LightStyle)
//
//        var rlMainAlarm  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            RelativeLayout(this,null,style)
//        } else {
//            RelativeLayout(this)
//        }
//        rlMainAlarm = findViewById(R.id.rlBackgroundLayout) as RelativeLayout
//
//        return rlMainAlarm
//    }

    private fun initializeAlarmButton() {
        showWeekDaysAndCurrentDay()

        if (lastAlarmData.alarmHours != 0) {
            setSettingsFromLastAlarm()

        } else {
            setCustomAlarmSettings()

        }
        //todo method for checking time and setting night day theme

    }

    private fun setSettingsFromLastAlarm() {
        tvAlarmTime.text = Integer.toString(lastAlarmData.alarmHours) + " : " + Integer.toString(lastAlarmData.alarmMinutes)

    }

    private fun setCustomAlarmSettings() {
        tvAlarmTime.text = Integer.toString(ConstantValues.CUSTOM_ALARM_SETTINGS_HOURS) + " : " + Integer.toString(ConstantValues.CUSTOM_ALARM_SETTINGS_MINUTES)

    }

    private fun showWeekDaysAndCurrentDay() {
        initializeDaysOfWeekViews()

        listDaysOfWeekViews.forEachIndexed { indexOfDay, dayView ->
            if (indexOfDay == calendar.get(Calendar.DAY_OF_WEEK))
                setDayOfWeekTestStyle(dayView)

        }
    }

    private fun initializeDaysOfWeekViews() {
        listDaysOfWeekViews.add(findViewById(R.id.tvMonday) as TextView)
        listDaysOfWeekViews.add(findViewById(R.id.tvTuesday) as TextView)
        listDaysOfWeekViews.add(findViewById(R.id.tvWednesday) as TextView)
        listDaysOfWeekViews.add(findViewById(R.id.tvThursday) as TextView)
        listDaysOfWeekViews.add(findViewById(R.id.tvFriday) as TextView)
        listDaysOfWeekViews.add(findViewById(R.id.tvSaturday) as TextView)
        listDaysOfWeekViews.add(findViewById(R.id.tvSunday) as TextView)

    }

    private fun setDayOfWeekTestStyle(tvDayOfWeek: TextView) {
        val ssContent: SpannableString = object : SpannableString("Content") {}
        ssContent.setSpan(object : UnderlineSpan() {}, 0, ssContent.length, 0)
        tvDayOfWeek.text = ssContent

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            tvDayOfWeek.setTextAppearance(this, R.style.ChosenDayOfTheWeek_TextTheme)
        } else {
            tvDayOfWeek.setTextAppearance(R.style.ChosenDayOfTheWeek_TextTheme)
        }
    }
}
