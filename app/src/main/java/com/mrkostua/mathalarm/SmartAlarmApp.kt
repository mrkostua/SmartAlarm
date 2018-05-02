package com.mrkostua.mathalarm

import com.mrkostua.mathalarm.injections.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */
class SmartAlarmApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()

    }

}

