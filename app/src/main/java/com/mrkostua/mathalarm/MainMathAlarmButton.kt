package com.mrkostua.mathalarm

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Kostiantyn on 21.11.2017.
 */
//TODO Koltin is there still need for butterKnife library ?
//todo read more about kotlin !!!!! the way it work and compile
public class MainMathAlarmButton : AppCompatActivity() {

    private lateinit var rlBackgroundLayout: RelativeLayout
    private lateinit var rlButtonLayout: RelativeLayout
    private lateinit var tvAlarmTime: TextView

    private lateinit var ibAdditionalSettings: ImageButton

    private val listDaysOfWeekViews = ArrayList<TextView>(7)
    private val lastAlarmData = LastAlarmData(this)

    private val calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set layout to the full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main_alarm)

        initializeViews()
        calendar.timeInMillis = System.currentTimeMillis()

        setThemeForAlarmButtonLayout()
        initializeAlarmButton()
    }

    private fun initializeViews() {
        rlButtonLayout = findViewById(R.id.rlButtonLayout) as RelativeLayout
        rlBackgroundLayout = findViewById(R.id.rlBackgroundLayout) as RelativeLayout

        tvAlarmTime = findViewById(R.id.tvAlarmTime) as TextView
        ibAdditionalSettings = findViewById(R.id.ibAdditionalSettings) as ImageButton

    }

    private fun setThemeForAlarmButtonLayout() {
        if (isDarkTime()) {
            setDayLayoutTheme()

        } else {
            setEveningLayoutTheme()

        }
    }

    private fun initializeAlarmButton() {
        showWeekDaysAndCurrentDay()
        if (isFirstAlarmCreation()) {
            setSettingsFromLastAlarm()

        } else {
            setCustomAlarmSettings()

        }
        //todo method for checking time and setting night day theme

    }


    private fun setDayLayoutTheme() {
        setViewBackgroundColor(ibAdditionalSettings, R.color.main_layout_backgroundDay)
        setViewBackgroundColor(rlBackgroundLayout, R.color.main_layout_backgroundDay)
    }

    private fun setEveningLayoutTheme() {
        setViewBackgroundColor(ibAdditionalSettings, R.color.main_layout_backgroundEvening)
        setViewBackgroundColor(rlBackgroundLayout, R.color.main_layout_backgroundEvening)
    }

    /**
     *  set background color of the @param[view] using deprecated @see[getColor] for api < M.
     */
    private fun setViewBackgroundColor(view: View, colorResource: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setBackgroundColor(resources.getColor(colorResource, null))

        } else {
            view.setBackgroundColor(resources.getColor(colorResource))

        }
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
                setDayOfWeekTextStyle(dayView)

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

    private fun setDayOfWeekTextStyle(tvDayOfWeek: TextView) {
        val ssContent = SpannableString("Content")
        ssContent.setSpan(UnderlineSpan(), 0, ssContent.length, 0)
        tvDayOfWeek.text = ssContent

        setTextAppearance(tvDayOfWeek, R.style.ChosenDayOfTheWeek_TextTheme)
    }

    private fun setTextAppearance(view: TextView, style: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            view.setTextAppearance(this, style)
        } else {
            view.setTextAppearance(style)
        }
    }

    private fun isFirstAlarmCreation(): Boolean {
        return lastAlarmData.alarmHours != 0
    }

    private fun isDarkTime(): Boolean {
        //todo Update method and make it more precise
        return calendar.get(Calendar.AM_PM) == Calendar.PM
    }

    private fun showFirstHelpingAlertDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Smart helper :)")
                .setMessage("Hi if you want to see how the Alarm work just follow appearing text and touch them to continue")
                .setPositiveButton("Let's do it", { dialog, which ->
                    showFirstHelpingTextMessage()
                    dialog.dismiss()
                })
                .setNegativeButton("Back", { dialog, which ->
                    Toast.makeText(this, "If you need some help just go to settings", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }).create().show()
    }

    private fun showFirstHelpingTextMessage(){
        val tvHelperMainButtonMessage = findViewById(R.id.tvHelperMainButton) as TextView
        if (isDarkTime())
            setTextAppearance(tvHelperMainButtonMessage, R.style.HelperDark_TextTheme)
        else
            setTextAppearance(tvHelperMainButtonMessage, R.style.HelperDark_TextTheme)

        tvHelperMainButtonMessage.setOnClickListener { view ->
            view.visibility = View.GONE
            showSecondHelpingTextMessage()
        }
    }

    private fun showSecondHelpingTextMessage(){
        val tvHelperMainButtonMessage = findViewById(R.id.tvHelperMainButton) as TextView
        if (isDarkTime())
            setTextAppearance(tvHelperMainButtonMessage, R.style.HelperDark_TextTheme)
        else
            setTextAppearance(tvHelperMainButtonMessage, R.style.HelperDark_TextTheme)

        tvHelperMainButtonMessage.setOnClickListener { view ->
            view.visibility = View.GONE
            showSecondHelpingTextMessage()
        }
    }

    public fun rlButtonLayoutOnClickListener(view: View) {
        if (isFirstAlarmCreation()) {
            //todo check if it is first alarm creation ( show some toast and etc.) and basic setting=]
            showFirstHelpingAlertDialog()

        } else {
            // todo show some preview of alarm settings and than set alarm.

        }
    }


    public fun ibAdditionalSettingsOnClickListener(view: View) {
        //todo open setting layout -> with volume settings and etc.
    }


}
