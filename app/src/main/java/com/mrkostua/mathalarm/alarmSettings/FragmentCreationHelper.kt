package com.mrkostua.mathalarm.alarmSettings

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.mrkostua.mathalarm.R
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.injections.scope.ActivityScope
import com.mrkostua.mathalarm.tools.ShowLogs
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 02.12.2017.
 */
@ActivityScope
class FragmentCreationHelper @Inject constructor(activity: FragmentActivity) {
    private val fragmentManager: FragmentManager = activity.supportFragmentManager
    private val TAG: String = this.javaClass.simpleName

    fun loadFragment(fragment: Fragment) {
        ShowLogs.log(TAG, "loadFragment + fragment " + fragment.toString())
        fragmentManager.beginTransaction()
                .replace(R.id.flFragmentsContainer, fragment)
                .addToBackStack(null)
                .commit()

    }

    fun getFragmentFormContainer(): Fragment {
        return fragmentManager.findFragmentById(R.id.flFragmentsContainer)
                ?: FragmentOptionSetRingtone()

    }
}
