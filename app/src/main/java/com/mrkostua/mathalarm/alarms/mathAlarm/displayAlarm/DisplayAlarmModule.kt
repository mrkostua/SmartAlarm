package com.mrkostua.mathalarm.alarms.mathAlarm.displayAlarm

import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.injections.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 3/29/2018.
 */

@Module
class DisplayAlarmModule {
    @ActivityScope
    @Provides
    fun provideDisplayViewModel(data: AlarmDataHelper) = DisplayAlarmViewModel(data)
}