package com.mrkostua.mathalarm.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Window
import android.view.WindowManager
import com.mrkostua.mathalarm.alarms.mathAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.extensions.get

/**
 * @author Kostiantyn Prysiazhnyi on 06.12.2017.
 */
object AlarmTools {
    private val TAG = AlarmTools::class.java.simpleName

    @Suppress("DEPRECATION")
    public fun getDrawable(resources: Resources, drawableId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(drawableId, null)

        } else {
            resources.getDrawable(drawableId)

        }
    }

    public fun getCustomRingtoneResId(context: Context): Int =
            context.resources.getIdentifier(ConstantValues.CUSTOM_ALARM_RINGTONE, "raw", context.packageName)

    public fun getRingtoneNameByResId(context: Context, ringtoneResId: Int): String =
            context.resources.getResourceName(ringtoneResId)

    public fun isFragmentExistsForThisIndex(index: Int): Boolean =
            (index > 0 && index <= ConstantValues.alarmSettingsOptionsList.size - 1)


    public fun getLastFragmentIndex(): Int {
        return ConstantValues.alarmSettingsOptionsList.size - 1
    }

    public fun startMainActivity(context: Context) {
        context.startActivity(Intent(context, MainAlarmActivity::class.java))
    }

    //todo fix this method default value of the hour is set to 7
    public fun isFirstAlarmCreation(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences[PreferencesConstants.ALARM_HOURS.getKeyValue(), ConstantValues.PREFERENCES_WRONG_VALUE] == ConstantValues.PREFERENCES_WRONG_VALUE

    }

    fun setFullScreenMode(activity: Activity) {
        //set layout to the full screen
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}