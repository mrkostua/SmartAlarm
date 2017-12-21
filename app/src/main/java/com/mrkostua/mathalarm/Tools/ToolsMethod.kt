package com.mrkostua.mathalarm.Tools

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build

/**
 * @author Kostiantyn Prysiazhnyi on 06.12.2017.
 */
object ToolsMethod {
    public fun getDrawable(resources: Resources, drawableId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(drawableId, null)

        } else {
            resources.getDrawable(drawableId)

        }
    }

    public fun getCustomRingtoneResId(context: Context): Int {
        return context.resources.getIdentifier(ConstantValues.CUSTOM_ALARM_RINGTONE, "raw", context.packageName)

    }

    public fun getRingtoneNameByResId(context: Context, ringtoneResId : Int) : String{
        TODO("check how to het name of the file by ResID")
    }


}