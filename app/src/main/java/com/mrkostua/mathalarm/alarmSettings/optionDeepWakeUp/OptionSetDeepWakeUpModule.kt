package com.mrkostua.mathalarm.alarmSettings.optionDeepWakeUp

import com.mrkostua.mathalarm.injections.scope.FragmentScope
import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 4/14/2018.
 */
@Module
abstract class OptionSetDeepWakeUpModule {
    @FragmentScope
    @Binds
    abstract fun getOptionSetDeepWakeUpPresenter(presenter: OptionSetDeepWakeUpPresenter): OptionSetDeepWakeUpContract.Presenter
}