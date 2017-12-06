package com.mrkostua.mathalarm.Tools

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build

/**
 * @author Kostiantyn Prysiazhnyi on 06.12.2017.
 */
object AllApiTools {
    public fun getDrawable(resources: Resources, drawableId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(drawableId, null)
        } else {
            resources.getDrawable(drawableId)
        }
    }
}