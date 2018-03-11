package com.mrkostua.mathalarm

import android.app.Application
import android.content.SharedPreferences
import com.mrkostua.mathalarm.injections.components.ApplicationComponent
import com.mrkostua.mathalarm.injections.components.DaggerApplicationComponent
import com.mrkostua.mathalarm.injections.modules.ApplicationModule
import javax.inject.Inject

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */
open class SmartAlarmApp : Application() {
    @Inject
    public lateinit var sharedPreferences: SharedPreferences

    open val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }

}

