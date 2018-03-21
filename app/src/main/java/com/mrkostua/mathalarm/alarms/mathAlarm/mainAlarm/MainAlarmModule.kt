package com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm

import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.injections.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 3/21/2018.
 */
@Module
public class MainAlarmModule {
    @ActivityScope
    @Provides
    fun provideMainAlarmViewModel(data: AlarmDataHelper) = MainAlarmViewModel(data)
}