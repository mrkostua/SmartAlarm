package com.mrkostua.mathalarm.injections.components

import android.app.Activity
import android.content.Context
import com.mrkostua.mathalarm.Alarms.MathAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.injections.annotation.ActivityContext
import com.mrkostua.mathalarm.injections.modules.ActivityModule
import dagger.Component

/**
 * @author Kostiantyn Prysiazhnyi on 3/6/2018.
 */

@Component( modules = [(ActivityModule::class)])
interface ActivityComponent {
    @ActivityContext
    fun getActivityContext() : Context
    fun getActivity() : Activity

    fun inject(mainAlarmActivity: MainAlarmActivity)
}


