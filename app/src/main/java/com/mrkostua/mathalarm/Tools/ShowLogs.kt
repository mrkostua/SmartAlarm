package com.mrkostua.mathalarm.Tools

import android.util.Log

/**
 * @author Kostiantyn Prysiazhnyi on 03.12.2017.
 */

object ShowLogs {
    var LOG_STATUS = if (ConstantValues.LOG_DEBUG_STATUS) true else false

    fun log(TAG: String, logMessage: String) {
        if(LOG_STATUS)
        Log.i(TAG, " " + logMessage)
    }

}