package com.mrkostua.mathalarm.injections

import android.app.Activity
import android.os.Build
import dagger.android.AndroidInjection
import dagger.android.DaggerFragment

/**
 * @author Kostiantyn Prysiazhnyi on 6/14/2018.
 */
public abstract class CustomDaggerFragment : DaggerFragment() {
    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidInjection.inject(this)
        }
        super.onAttach(activity)
    }
}