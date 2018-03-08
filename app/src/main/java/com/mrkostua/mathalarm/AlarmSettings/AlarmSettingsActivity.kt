package com.mrkostua.mathalarm.AlarmSettings

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import com.mrkostua.mathalarm.Interfaces.KotlinActivitiesInterface
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.Tools.AlarmTools
import com.mrkostua.mathalarm.Tools.ConstantValues
import com.mrkostua.mathalarm.Tools.NotificationTools
import com.mrkostua.mathalarm.Tools.ShowLogs
import com.mrkostua.mathalarm.extensions.app
import com.mrkostua.mathalarm.Interfaces.AddInjection
import com.mrkostua.mathalarm.injections.components.ActivityComponent
import com.mrkostua.mathalarm.injections.components.DaggerActivityComponent
import com.mrkostua.mathalarm.injections.modules.ActivityModule
import kotlinx.android.synthetic.main.activity_container_for_alarm_setttings.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 01.12.2017.
 */

/**
 * Consider adding schedule intent to Alarm Service which will fully secure user of waking up.
1. Check the battery level every 2 h (depends on last results and if ti was plugged in ).
2. Check if the alarm turn on and scheduled intent.
3. Check if Alarm volume is turn off (or very low).

In all this cases inform user by mail or etc.
Also there can 2-3 level of argent (importance of this alarm) (in some case app will automatically changed Settings or start Playing music to attract user attention to low battery level).

 */

class AlarmSettingsActivity : AppCompatActivity(), KotlinActivitiesInterface, AddInjection {
    private lateinit var notificationTools: NotificationTools
    private val TAG = this.javaClass.simpleName

    @Inject
    public lateinit var fragmentHelper: FragmentCreationHelper

    private val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent(app.applicationComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ShowLogs.log(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_for_alarm_setttings)

        initializeDependOnContextVariables(this)
        showChosenFragment(savedInstanceState?.getInt(ConstantValues.INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST, 0)
                ?: 0)

    }

    override fun onResume() {
        ShowLogs.log(TAG, "onResume")
        super.onResume()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(ConstantValues.INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST, getCurrentFragmentIndex())
    }

    override fun initializeDependOnContextVariables(context: Context) {
        injectDependencies()
        notificationTools = NotificationTools(this)

    }

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun onBackPressed() {
        moveBackToMainActivity(this)
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
        moveBackToMainActivity(this)
    }

    private fun moveBackToMainActivity(context: Context) {
        AlarmTools.startMainActivity(context)
        finish()
    }

    private fun showChosenFragment(fragmentIndexToLoad: Int) {
        val indexOfFragmentToLoad = intent.getIntExtra(ConstantValues.INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST, fragmentIndexToLoad)
        ShowLogs.log(TAG, "showChosenFragment + indexOfFragmentToLoad : " + indexOfFragmentToLoad)
        fragmentHelper.loadFragment((ConstantValues.alarmSettingsOptionsList[indexOfFragmentToLoad]))
        when (indexOfFragmentToLoad) {
            AlarmTools.getLastFragmentIndex() -> blockImageButton(ibMoveForward)
            0 -> blockImageButton(ibMoveBack)

        }
    }

    private fun showNextPreviousFragment(isNextFragment: Boolean) {
        val currentFragmentIndex = getCurrentFragmentIndex()
        ShowLogs.log(TAG, "currentFragmentIndex " + currentFragmentIndex)
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
                moveBackToMainActivity(this)
            }
        }
    }


    private fun moveToNextFragment(currentFragmentIndex: Int) {
        fragmentHelper.loadFragment(ConstantValues.alarmSettingsOptionsList[currentFragmentIndex + 1])

    }

    private fun moveToPreviousFragment(currentFragmentIndex: Int) {
        fragmentHelper.loadFragment(ConstantValues.alarmSettingsOptionsList[currentFragmentIndex - 1])

    }

    /**
     * @param whichFragmentIndex used for cases when new fragment is still loading
     * but gerCurrentFragmentIndex() returns previous (old one).
     */
    private fun isLastSettingsFragment(whichFragmentIndex: Int = 0): Boolean =
            getCurrentFragmentIndex() + whichFragmentIndex == AlarmTools.getLastFragmentIndex()

    private fun isFirstSettingsFragment(whichFragmentIndex: Int = 0): Boolean =
            getCurrentFragmentIndex() - whichFragmentIndex == 0


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





