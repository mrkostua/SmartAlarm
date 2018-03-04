package com.mrkostua.mathalarm.extensions

import android.support.v7.app.AppCompatActivity
import com.mrkostua.mathalarm.SmartAlarmApp

/**
 * Created by User on 3/4/2018.
 */

val AppCompatActivity.app: SmartAlarmApp
    get() = application as SmartAlarmApp