package com.mrkostua.mathalarm.injections.components

import android.content.Context
import android.content.SharedPreferences
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetDeepSleepMusic
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetMessage
import com.mrkostua.mathalarm.AlarmSettings.FragmentOptionSetTime
import com.mrkostua.mathalarm.AlarmSettings.OptionSetRingtone.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.SmartAlarmApp
import com.mrkostua.mathalarm.injections.annotation.ApplicationContext
import com.mrkostua.mathalarm.injections.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */
@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
    fun inject(smartAlarmApp: SmartAlarmApp)
    fun inject(fragmentOptionSetMessage: FragmentOptionSetMessage)
    fun inject(fragmentOptionSetDeepSleepMusic: FragmentOptionSetDeepSleepMusic)
    fun inject(fragmentOptionSetTime: FragmentOptionSetTime)
    fun inject(fragmentOptionSetRingtone: FragmentOptionSetRingtone)

    fun getSharedPreferences(): SharedPreferences

    @ApplicationContext
    fun getContext(): Context

    fun getApplication(): SmartAlarmApp
}