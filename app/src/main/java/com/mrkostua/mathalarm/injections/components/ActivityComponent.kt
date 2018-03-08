package com.mrkostua.mathalarm.injections.components

import android.content.SharedPreferences
import com.mrkostua.mathalarm.Alarms.MathAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.Alarms.MathAlarm.PreviewOfAlarmSettings
import com.mrkostua.mathalarm.injections.modules.ActivityModule
import com.mrkostua.mathalarm.injections.scope.ActivityScope
import dagger.Component

/**
 * @author Kostiantyn Prysiazhnyi on 3/6/2018.
 */
@ActivityScope
@Component(dependencies = [(ApplicationComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun inject(mainActivity: MainAlarmActivity)

    fun getSharedPreferences() : SharedPreferences

    fun getPreviewOfAlarmSettings() : PreviewOfAlarmSettings
    /**
     * read more about Dependency components -> from what I have understand we need explicitly show
     * which methods from dependency component we want to use by creating methods with
     * same return type (in component which use some dependencies)
     */

}


