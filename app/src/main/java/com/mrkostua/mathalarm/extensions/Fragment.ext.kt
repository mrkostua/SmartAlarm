package com.mrkostua.mathalarm.extensions

import android.app.Fragment
import com.mrkostua.mathalarm.SmartAlarmApp


/**
 * @author Kostiantyn Prysiazhnyi on 3/6/2018.
 */
val Fragment.app: SmartAlarmApp
    get() = activity.application as SmartAlarmApp