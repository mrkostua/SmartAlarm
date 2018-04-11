package com.mrkostua.mathalarm.extensions

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.StyleRes
import android.view.View
import android.widget.TextView

/**
 * @author Kostiantyn Prysiazhnyi on 3/26/2018.
 */

/**
 *  set background color of the @param[view] using deprecated @see[getColor] for api < M.
 */
fun View.setBackgroundColor(resources: Resources, @ColorRes colorResource: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setBackgroundColor(resources.getColor(colorResource, null))

    } else {
        setBackgroundColor(resources.getColor(colorResource))

    }
}


@Suppress("DEPRECATION")
fun TextView.setTextAppearance(@StyleRes style: Int, context: Context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        setTextAppearance(context, style)
    } else {
        setTextAppearance(style)
    }
}
