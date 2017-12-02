package com.mrkostua.mathalarm.AlarmSettingsOptions

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mrkostua.mathalarm.ConstantValues
import com.mrkostua.mathalarm.R
import kotlinx.android.synthetic.main.activity_container_for_alarm_setttings.*

/**
 * @author Kostiantyn Prysiazhnyi on 01.12.2017.
 */

/**
 * TODO maybe it will be ControllerActivity with 3 buttons in the button of the layout and fragment VIEW
 * TODO so it will be easy to control changing of the fragments by buttons.
 */

class OptionsButtonsController : AppCompatActivity() {
    private val fragmentHelper: FragmentHelper = FragmentHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container_for_alarm_setttings)

        showFirstSettingsOption()
    }

    private fun showFirstSettingsOption() {
        fragmentHelper.loadFragment(FragmentOptionSetTime())
        //todo show or do some things during normal opening Settings Views
    }

    public fun ibMoveForwardClickListener(view: View) {
        //todo change this method be completely dependent on the Settings Map (not just typed number by hand)
        //todo it is mean that by adding new Setting Option developer need to only to update ConstantValue variable
        when (getCurrentFragmentIndex()) {
            0 -> {

            }
        }
    }

    private fun getCurrentFragmentIndex(): Int {
        return ConstantValues.alarmSettingsOptionsMap[fragmentHelper.getFragmentFormContainer()] ?: 0
    }

    public fun ibMoveBackClickListener(view: View) {

    }

    public fun ibBackToMainActivityClickListener(view: View) {

    }
}




