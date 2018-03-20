package com.mrkostua.mathalarm.alarmSettings.optionSetRingtone

import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton

/**
 * @author Kostiantyn Prysiazhnyi on 17.01.2018.
 */
interface RingtoneClickListeners {
    fun imageButtonClickListener(view: ImageButton, position: Int)
    fun checkBoxClickListener(view: CheckBox, position: Int)
    fun recycleViewClickListener(view: View, position: Int)

}