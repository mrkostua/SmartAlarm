package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import com.mrkostua.mathalarm.injections.scope.ServiceScope
import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */
@Module
abstract class DisplayAlarmServiceModule {
    @ServiceScope
    @Binds
    abstract fun bindDisplayAlarmServicePresenter(presenter: DisplayAlarmServicePresenter): DisplayAlarmServiceContract.Presenter

}