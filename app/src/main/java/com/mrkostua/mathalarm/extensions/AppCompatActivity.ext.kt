package com.mrkostua.mathalarm.extensions

import android.support.v7.app.AppCompatActivity
import com.mrkostua.mathalarm.SmartAlarmApp

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */

public val AppCompatActivity.app: SmartAlarmApp
    get() = application as SmartAlarmApp
