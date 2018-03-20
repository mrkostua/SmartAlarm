package com.mrkostua.mathalarm.alarmSettings.optionSetTime

import com.mrkostua.mathalarm.injections.scope.FragmentScope
import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 3/18/2018.
 */
@Module
public abstract class OptionSetTimeModule {
    @FragmentScope
    @Binds
    abstract fun getOptionSetTimePresenter(presenter: OptionSetTimePresenter): OptionSetTimeContract.Presenter
}