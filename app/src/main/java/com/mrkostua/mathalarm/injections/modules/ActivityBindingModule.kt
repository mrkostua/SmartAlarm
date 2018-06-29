package com.mrkostua.mathalarm.injections.modules

import com.mrkostua.mathalarm.alarmSettings.mainSettings.AlarmSettingsActivity
import com.mrkostua.mathalarm.alarmSettings.mainSettings.AlarmSettingsModule
import com.mrkostua.mathalarm.alarmSettings.optionDeepWakeUp.FragmentOptionSetDeepWakeUp
import com.mrkostua.mathalarm.alarmSettings.optionDeepWakeUp.OptionSetDeepWakeUpModule
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.OptionSetRingtoneModule
import com.mrkostua.mathalarm.alarmSettings.optionSetTime.FragmentOptionSetTime
import com.mrkostua.mathalarm.alarmSettings.optionSetTime.OptionSetTimeModule
import com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm.DisplayAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm.DisplayAlarmModule
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm.MainAlarmModule
import com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService.DisplayAlarmService
import com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService.DisplayAlarmServiceModule
import com.mrkostua.mathalarm.injections.scope.ActivityScope
import com.mrkostua.mathalarm.injections.scope.DisplayAlarmServiceScope
import com.mrkostua.mathalarm.injections.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Kostiantyn Prysiazhnyi on 3/16/2018.
 */
@Module
public abstract class ActivityBindingModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [(OptionSetTimeModule::class), (DisplayHelperModule::class)])
    public abstract fun optionSetTimeFragment(): FragmentOptionSetTime

    @FragmentScope
    @ContributesAndroidInjector(modules = [(OptionSetRingtoneModule::class), (DisplayHelperModule::class), (MediaPlayerHelperModule::class)])
    public abstract fun getOptionSetRingtoneFragement(): FragmentOptionSetRingtone

    @ActivityScope
    @ContributesAndroidInjector(modules = [(DisplayHelperModule::class), (AlarmSettingsModule::class)])
    public abstract fun getAlarmSettingActivty(): AlarmSettingsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainAlarmModule::class), (DisplayHelperModule::class)])
    public abstract fun getMainAlarmActivity(): MainAlarmActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(DisplayAlarmModule::class), (DisplayHelperModule::class)])
    public abstract fun getDisplayAlarmActivity(): DisplayAlarmActivity

    @DisplayAlarmServiceScope
    @ContributesAndroidInjector(modules = [(DisplayAlarmServiceModule::class), (DisplayHelperModule::class), (MediaPlayerHelperModule::class)])
    public abstract fun getDisplayAlarmService(): DisplayAlarmService

    @FragmentScope
    @ContributesAndroidInjector(modules = [(OptionSetDeepWakeUpModule::class), (MediaPlayerHelperModule::class)])
    public abstract fun getFragmentOptionSetDeepWakeUp(): FragmentOptionSetDeepWakeUp
}