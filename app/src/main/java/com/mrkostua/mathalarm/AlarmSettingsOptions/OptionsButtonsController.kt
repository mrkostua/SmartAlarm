package com.mrkostua.mathalarm.AlarmSettingsOptions

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.R

/**
 * Created by Администратор on 01.12.2017.
 */

/**
 * TODO maybe it will be ControllerActivity with 3 buttons in the button of the layout and fragment VIEW
 * TODO so it will be easy to control changing of the fragments by buttons.
 */

    public class OptionsButtonsController : AppCompatActivity() {
    private lateinit var fragmentView : View
    private val fragmentHelper : FragmentHelper = FragmentHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_option)
    }

    private fun showOptionsSettings(){
        fragmentHelper.loadFragment(FragmentSetTimeOption())
    }



}




