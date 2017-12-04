package com.mrkostua.mathalarm.Alarms.MathAlarm

import android.content.Context
import android.content.Intent
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
import com.mrkostua.mathalarm.AlarmSettings.AlarmSettingsActivity
import com.mrkostua.mathalarm.ConstantValues
import com.mrkostua.mathalarm.LastAlarmData
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Settings_Preference
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Kostiantyn on 21.11.2017.
 */

class MainAlarmActivity : AppCompatActivity() {
    private lateinit var rlBackgroundLayout: RelativeLayout
    private lateinit var rlButtonLayout: RelativeLayout
    private lateinit var tvAlarmTime: TextView

    private lateinit var ibAdditionalSettings: ImageButton

    private val listDaysOfWeekViews = ArrayList<TextView>(7)
    private val lastAlarmData = LastAlarmData(this)

    private val calendar = Calendar.getInstance()

    private val intentAlarmSettingsActivity = Intent(this,AlarmSettingsActivity::class.java)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set layout to the full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main_alarm_clock)

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


    inner class UserHelperMainLayout constructor(val context: Context) {
        private lateinit var tvFirstHelpingMessage: TextView
        private lateinit var tvSecondHelpingMessage: TextView
        private lateinit var rlBackgroundHelper: RelativeLayout

        init {
            initializeViews()
            rlBackgroundHelper.visibility = View.VISIBLE
        }

        fun showHelpingAlertDialog() {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle(getString(R.string.helperFirstDialogTitle))
                    .setMessage(getString(R.string.helperFirstDialogMessage))
                    .setPositiveButton(getString(R.string.LetsDoIt), { dialog, which ->
                        showFirstHelpingTextMessage()
                        dialog.dismiss()
                    })
                    .setNegativeButton(getString(R.string.Back), { dialog, which ->
                        Toast.makeText(context, "If you need some help just go to settings", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }).create().show()
        }

        private fun initializeViews() {
            tvFirstHelpingMessage = findViewById(R.id.tvFirstHelpingMessage) as TextView
            tvSecondHelpingMessage = findViewById(R.id.tvSecondHelpingMessage) as TextView
            rlBackgroundHelper = findViewById(R.id.rlBackgroundHelper) as RelativeLayout

            if (isDarkTime()) {
                setTextAppearance(tvFirstHelpingMessage, R.style.HelperDark_TextTheme)
                setTextAppearance(tvSecondHelpingMessage, R.style.HelperDark_TextTheme)
            } else {
                setTextAppearance(tvFirstHelpingMessage, R.style.HelperDark_TextTheme)
                setTextAppearance(tvSecondHelpingMessage, R.style.HelperDark_TextTheme)
            }

        }

        private fun showFirstHelpingTextMessage() {
            tvFirstHelpingMessage.visibility = View.VISIBLE
            tvFirstHelpingMessage.setOnClickListener { view ->
                view.visibility = View.GONE
                showSecondHelpingTextMessage()
            }
            //todo maybe add some onTouch method to change background color of the view or in XML
        }

        private fun showSecondHelpingTextMessage() {
            tvSecondHelpingMessage.visibility = View.VISIBLE
            tvSecondHelpingMessage.setOnClickListener { view ->
                view.visibility = View.GONE
//                showThirdHelpingTextMessage() if there is need for more messages

            }
        }


        private fun showAlarmSettingsActivity(){
            startActivity(intentAlarmSettingsActivity)

        }

    }


    public fun rlButtonLayoutOnClickListener(view: View) {
        if (isFirstAlarmCreation()) {
            val userHelper = UserHelperMainLayout(this)
            userHelper.showHelpingAlertDialog()

        } else {
            // todo show some preview of alarm settings and than set alarm.
            /*
            todo maybe show some AlertDialog with alarm settings like in basic version
            todo if user will push some settings he will be moved to proper activity where he can change settings
             */

        }
    }

    public fun ibAdditionalSettingsOnClickListener(view: View) {
        startActivity(Intent(this, Settings_Preference::class.java))
    }

}
