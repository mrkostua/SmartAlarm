package com.mrkostua.mathalarm.AlarmSettings

import android.app.Activity
import android.app.Fragment
import com.mrkostua.mathalarm.R

/**
 * @author Kostiantyn Prysiazhnyi on 02.12.2017.
 */

class FragmentCreationHelper(activity: Activity) {
    private val fragmentManager = activity.fragmentManager
    private val TAG : String = this.javaClass.simpleName

    fun loadFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.flFragmentsContainer, fragment)
                .addToBackStack(null)
                .commit()
    }

    fun getFragmentFormContainer() : Fragment{
        return fragmentManager.findFragmentById(R.id.flFragmentsContainer)

    }
}
