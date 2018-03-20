package com.mrkostua.mathalarm.tools

import android.util.Log

/**
 * @author Kostiantyn Prysiazhnyi on 03.12.2017.
 */

object ShowLogs {
    private var isLogForProduction = false

    fun log(TAG: String, logMessage: String) {
        if (!isLogForProduction)
            Log.i("KOKO " + TAG, " " + logMessage)
    }

}