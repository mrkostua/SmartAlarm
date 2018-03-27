package com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.mrkostua.mathalarm.R
import kotlinx.android.synthetic.main.activity_main_alarm.*
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/26/2018.
 */
class UserHelper @Inject constructor(private val context: Context,
                                     private val mainActivity: Activity,
                                     private val alarmSettingsIntent: Intent) {
    private val helpingViewsList: MutableList<View> = ArrayList()
    fun showHelpingAlertDialog() {
        AlertDialog.Builder(context, R.style.AlertDialogCustomStyle)
                .setTitle(context.getString(R.string.helperFirstDialogTitle))
                .setMessage(context.getString(R.string.helperFirstDialogMessage))
                .setPositiveButton(context.getString(R.string.letsDoIt), { dialog, which ->
                    showFirstHelpingTextMessage()
                    dialog.dismiss()
                })
                .setNegativeButton(context.getString(R.string.back), { dialog, which ->
                    Toast.makeText(context, "If you need some help just go to settings", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }).create().show()
    }

    fun isHelpingViewsHidden(): Boolean = helpingViewsList.isEmpty()

    fun hideHelpingViews() {
        helpingViewsList.forEach({ view -> view.visibility = View.GONE })
        helpingViewsList.clear()
    }

    private fun showFirstHelpingTextMessage() {
        helpingViewsList.add(mainActivity.tvFirstHelpingMessage)
        mainActivity.tvFirstHelpingMessage.visibility = View.VISIBLE
        mainActivity.tvFirstHelpingMessage.setOnClickListener { view ->
            view.visibility = View.GONE
            showSecondHelpingTextMessage()
        }
    }

    private fun showSecondHelpingTextMessage() {
        helpingViewsList.add(mainActivity.tvSecondHelpingMessage)
        mainActivity.tvSecondHelpingMessage.visibility = View.VISIBLE
        mainActivity.tvSecondHelpingMessage.setOnClickListener { view ->
            view.visibility = View.GONE
            context.startActivity(alarmSettingsIntent)
            helpingViewsList.clear()

        }
    }

}