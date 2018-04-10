package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import com.mrkostua.mathalarm.injections.scope.DisplayAlarmServiceScope
import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */
@Module
abstract class DisplayAlarmServiceModule {
    @DisplayAlarmServiceScope
    @Binds
    abstract fun provideDisplayAlarmServicePresenter(presenter: DisplayAlarmServicePresenter): DisplayAlarmServiceContract.Presenter

}