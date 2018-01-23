package com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone

import android.net.Uri


/**`
 * @author Kostiantyn Prysiazhnyi on 18.01.2018.
 */
class RingtoneObject(val name: String, var rating: Int = 1000, var isPlaying: Boolean = false, var isChecked: Boolean = false, val uri: Uri? = null)
//todo maybe add some more information as author and etc (so it can be displayed in Dialog)