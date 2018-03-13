package com.mrkostua.mathalarm.injections.components

import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.FragmentCreationHelper
import com.mrkostua.mathalarm.alarmSettings.mainSettings.AlarmSettingsActivity
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import com.mrkostua.mathalarm.alarms.mathAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.PreviewOfAlarmSettings
import com.mrkostua.mathalarm.data.AlarmDataHelper
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
    fun inject(alarmSettingsActivity: AlarmSettingsActivity)

    fun getSharedPreferences() : SharedPreferences

    fun getPreviewOfAlarmSettings() : PreviewOfAlarmSettings

    fun getFragmentCreationHelper() : FragmentCreationHelper

    fun getAlarmDataHelper() : AlarmDataHelper

    fun getMediaPlayerHelper() : MediaPlayerHelper

    /**
     * read more about Dependency components -> from what I have understand we need explicitly show
     * which methods from dependency component we want to use by creating methods with
     * same return type (in component which use some dependencies)
     */

}


