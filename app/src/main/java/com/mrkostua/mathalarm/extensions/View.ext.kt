@file:Suppress("DEPRECATION")

package com.mrkostua.mathalarm.extensions

import android.content.ClipData
import android.content.ClipDescription
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


fun TextView.setTextAppearance(@StyleRes style: Int, context: Context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        setTextAppearance(context, style)
    } else {
        setTextAppearance(style)
    }
}

fun View.startMyDragAndDrop(clipData: String, shadow: View.DragShadowBuilder) {
    val dragData = ClipData(clipData, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), ClipData.Item(clipData))

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        startDragAndDrop(dragData, shadow, null, 0)
    } else {
        startDrag(dragData, shadow, null, 0)

    }
}
