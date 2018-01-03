package com.mrkostua.mathalarm.AlarmSettings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import com.mrkostua.mathalarm.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.AlarmTools
import com.mrkostua.mathalarm.Tools.ConstantValues
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.ShowLogs
import kotlinx.android.synthetic.main.activity_container_for_alarm_setttings.*

/**
 * @author Kostiantyn Prysiazhnyi on 01.12.2017.
 */

public class AlarmSettingsActivity : AppCompatActivity(), KotlinActivitiesInterface {
    private lateinit var fragmentHelper: FragmentCreationHelper
    private lateinit var notificationTools: NotificationTools
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_for_alarm_setttings)
        initializeDependOnContextVariables()
    }
//todo in the beggining check maybe intent fragment index is first one or last so arrow button need to be blocked
    override fun onResume() {
        ShowLogs.log(TAG, "onResume")
        super.onResume()
        showChosenFragment()

    }

    override fun initializeDependOnContextVariables() {
        fragmentHelper = FragmentCreationHelper(this)
        notificationTools = NotificationTools(this)

    }

    public fun ibMoveForwardClickListener(view: View) {
        if (!isLastSettingsFragment()) {
            showNextPreviousFragment(true)

            if (isLastSettingsFragment(1)) {
                blockImageButton(view)
            } else {
                unblockImageButton(ibMoveBack)
            }

        } else {
            notificationTools.showToastMessage(resources.getString(R.string.blockedButtonMessage))

        }
    }

    public fun ibMoveBackClickListener(view: View) {
        if (!isFirstSettingsFragment()) {
            showNextPreviousFragment(false)

            if (isFirstSettingsFragment(1)) {

                blockImageButton(view)
            } else {

                unblockImageButton(ibMoveForward)
            }

        } else {
            notificationTools.showToastMessage(resources.getString(R.string.blockedButtonMessage))

        }

    }

    public fun ibBackToMainActivityClickListener(view: View) {
        AlarmTools.startMainActivity(this)
    }

    private fun showChosenFragment() {
        val indexOfFragmentToLoad = intent.getIntExtra(ConstantValues.INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST, -1)
        if (indexOfFragmentToLoad != -1) {
            fragmentHelper.loadFragment((ConstantValues.alarmSettingsOptionsList[indexOfFragmentToLoad]))

        } else {
            fragmentHelper.loadFragment(ConstantValues.alarmSettingsOptionsList[0])

        }
    }

    private fun showNextPreviousFragment(isNextFragment: Boolean) {
        val currentFragmentIndex = getCurrentFragmentIndex()
        ShowLogs.log(TAG,"currentFragmentIndex " + currentFragmentIndex)
        when (currentFragmentIndex) {
            0 -> {
                moveToNextFragment(currentFragmentIndex)

            }
            in 1 until AlarmTools.getLastFragmentIndex() -> {
                if (isNextFragment) {
                    moveToNextFragment(currentFragmentIndex)
                } else {
                    moveToPreviousFragment(currentFragmentIndex)
                }

            }
            AlarmTools.getLastFragmentIndex() -> {
                moveToPreviousFragment(currentFragmentIndex)

            }
            else -> {
                ShowLogs.log(TAG, " showNextPreviousFragment wrong index of currently showing fragment")
                AlarmTools.startMainActivity(this)
            }
        }
    }


    private fun moveToNextFragment(currentFragmentIndex: Int) {
        fragmentHelper.loadFragment(ConstantValues.alarmSettingsOptionsList[currentFragmentIndex + 1])

    }

    private fun moveToPreviousFragment(currentFragmentIndex: Int) {
        fragmentHelper.loadFragment(ConstantValues.alarmSettingsOptionsList[currentFragmentIndex - 1])

    }

    private fun isLastSettingsFragment(whichFragmentIndex: Int = 0): Boolean = getCurrentFragmentIndex() + whichFragmentIndex == AlarmTools.getLastFragmentIndex()
    private fun isFirstSettingsFragment(whichFragmentIndex: Int = 0): Boolean = getCurrentFragmentIndex() - whichFragmentIndex == 0

    private fun blockImageButton(whichButton: View) {
        whichButton.isClickable = false
        whichButton.isFocusable = false
        if (whichButton is ImageButton) {
            when (whichButton) {

                ibMoveBack -> whichButton.setImageResource(R.drawable.arrow_left_blocked)

                ibMoveForward -> whichButton.setImageResource(R.drawable.arrow_right_blocked)

                else -> ShowLogs.log(TAG, "blockImageButton wrong method argument.")
            }
        }

    }

    private fun unblockImageButton(whichButton: View) {
        whichButton.isClickable = true
        whichButton.isFocusable = true
        if (whichButton is ImageButton) {
            when (whichButton) {
                ibMoveBack -> whichButton.setImageResource(R.drawable.arrow_left)

                ibMoveForward -> whichButton.setImageResource(R.drawable.arrow_right)

                else -> ShowLogs.log(TAG, "unblockImageButton wrong method argument.")
            }
        }
    }

    private fun getCurrentFragmentIndex(): Int {
        return ConstantValues.alarmSettingsOptionsList.indexOf(fragmentHelper.getFragmentFormContainer())
    }
}




