package com.mrkostua.mathalarm

import android.content.SharedPreferences
import com.mrkostua.mathalarm.injections.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */
public class SmartAlarmApp : DaggerApplication() {
    @Inject
    public lateinit var sharedPreferences: SharedPreferences

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()

    }

}

