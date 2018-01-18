package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.view.View
import android.widget.CompoundButton

/**
 * @author Kostiantyn Prysiazhnyi on 17.01.2018.
 */
interface RingtoneClickListeners {
    fun imageButtonClickListener(view: View, position: Int)
    fun checkBoxCheckListener(compoundButton: CompoundButton, isChecked: Boolean, position: Int)
    fun recycleViewClickListener(view: View, position: Int)
}