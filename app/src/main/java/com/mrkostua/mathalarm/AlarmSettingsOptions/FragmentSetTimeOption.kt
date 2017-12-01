package com.mrkostua.mathalarm.AlarmSettingsOptions

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrkostua.mathalarm.R

class FragmentSetTimeOption : Fragment() {
    private lateinit var fragmentView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        fragmentView = inflater.inflate(R.layout.activity_time_option, container, false)

        return fragmentView
    }
}

