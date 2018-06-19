package com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm

import android.content.Intent
import com.mrkostua.mathalarm.alarmSettings.mainSettings.AlarmSettingsActivity
import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.injections.scope.ActivityScope
import com.mrkostua.mathalarm.tools.NotificationTools
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

    @ActivityScope
    @Provides
    fun provideAlarmSettingIntent(activity: MainAlarmActivity): Intent {
        return Intent(activity, AlarmSettingsActivity::class.java)
    }

    @ActivityScope
    @Provides
    fun provideUserHelper(activity: MainAlarmActivity, intent: Intent) =
            UserHelper(activity, intent)

    @ActivityScope
    @Provides
    fun providePreviewOfAlarmSettings(activity: MainAlarmActivity, viewModule: MainAlarmViewModel,
                                      notificationsTools: NotificationTools) =
            PreviewOfAlarmSettings(activity, viewModule, notificationsTools)


}