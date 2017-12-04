package com.mrkostua.mathalarm.AlarmSettings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mrkostua.mathalarm.Alarms.MathAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.ConstantValues
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.ShowLogs
import kotlinx.android.synthetic.main.activity_container_for_alarm_setttings.*

/**
 * @author Kostiantyn Prysiazhnyi on 01.12.2017.
 */

public class AlarmSettingsActivity : AppCompatActivity() {
    private val fragmentHelper: FragmentCreationHelper = FragmentCreationHelper(this)
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_for_alarm_setttings)

        showFirstSettingsOption()
    }

    private fun showFirstSettingsOption() {
        fragmentHelper.loadFragment(ConstantValues.alarmSettingsOptionsList[0])


        ibBackToMainActivity.setBackgroundColor(resources.getColor(R.color.white))
        //todo change button color and clickable false, so the user
    }

    private fun getCurrentFragmentIndex(): Int {
        return ConstantValues.alarmSettingsOptionsList.indexOf(fragmentHelper.getFragmentFormContainer())
    }

    private fun getLastFragmentIndex(): Int {
        return ConstantValues.alarmSettingsOptionsList.size - 1
    }


    private fun showNextPreviousFragment(isNextFragment: Boolean) {
        val currentFragmentIndex = getCurrentFragmentIndex()
        when (currentFragmentIndex) {
            -1, 0 -> {
                moveToNextFragment(currentFragmentIndex)

            }
            in 1 until getLastFragmentIndex() -> {
                if (isNextFragment) {
                    moveToNextFragment(currentFragmentIndex)


                } else {
                    moveToPreviousFragment(currentFragmentIndex)

                }

            }

            getLastFragmentIndex() -> {
                moveToPreviousFragment(currentFragmentIndex)
                //todo CHANGE next button look, clickable false

            }
            else -> {
                ShowLogs.log(TAG, " showNextPreviousFragment wrong index of currently showing fragment")
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainAlarmActivity::class.java))
    }


    private fun moveToNextFragment(currentFragmentIndex: Int) {
        fragmentHelper.loadFragment(ConstantValues.alarmSettingsOptionsList[currentFragmentIndex + 1])
        //todo maybe do some animations

    }

    private fun moveToPreviousFragment(currentFragmentIndex: Int) {
        fragmentHelper.loadFragment(ConstantValues.alarmSettingsOptionsList[currentFragmentIndex - 1])

    }

    fun ibMoveForwardClickListener(view: View) {
        showNextPreviousFragment(true)
    }

    fun ibMoveBackClickListener(view: View) {
        showNextPreviousFragment(false)
    }

    fun ibBackToMainActivityClickListener(view: View) {
        startMainActivity()
    }
}




