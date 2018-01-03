package com.mrkostua.mathalarm.Tools

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import com.mrkostua.mathalarm.Alarms.MathAlarm.MainAlarmActivity

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

    public fun checkIfFragmentExistForThisIndex(index: Int): Boolean =
            (index > 0 && index < ConstantValues.alarmSettingsOptionsList.size - 1)


    public fun getLastFragmentIndex(): Int {
        return ConstantValues.alarmSettingsOptionsList.size - 1
    }

    public fun startMainActivity(context: Context) {
        context.startActivity(Intent(context, MainAlarmActivity::class.java))
    }
}