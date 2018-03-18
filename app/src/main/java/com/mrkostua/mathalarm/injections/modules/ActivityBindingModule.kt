package com.mrkostua.mathalarm.injections.modules

import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.FragmentOptionSetRingtone
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.OptionSetRingtoneModule
import com.mrkostua.mathalarm.alarmSettings.optionSetTime.FragmentOptionSetTime
import com.mrkostua.mathalarm.alarmSettings.optionSetTime.OptionSetTimeModule
import com.mrkostua.mathalarm.injections.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Kostiantyn Prysiazhnyi on 3/16/2018.
 */
@Module
public abstract class ActivityBindingModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [(OptionSetTimeModule::class), (DisplayModule::class)])
    public abstract fun optionSetTimeFragment(): FragmentOptionSetTime

    @FragmentScope
    @ContributesAndroidInjector(modules = [(OptionSetRingtoneModule::class), (DisplayModule::class)])
    public abstract fun getOptionSetRingtoneFragement(): FragmentOptionSetRingtone
}