package com.mrkostua.mathalarm.alarms.mathAlarm.mainAlarm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.mainSettings.AlarmSettingsActivity
import com.mrkostua.mathalarm.alarmSettings.mainSettings.PreviewOfAlarmSettings
import com.mrkostua.mathalarm.alarms.mathAlarm.MainAlarmActivity
import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.injections.scope.ActivityScope
import com.mrkostua.mathalarm.tools.ConstantValues
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
    fun provideAlarmSettingIntent(context: Context): Intent {
        return Intent(context, AlarmSettingsActivity::class.java)
                .putExtra(ConstantValues.INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST, 0)
    }

    @ActivityScope
    @Provides
    fun provideUserHelper(context: Context, activity: MainAlarmActivity, intent: Intent) =
            UserHelper(context, activity, intent)

    @ActivityScope
    @Provides
    fun providePreviewOfAlarmSettings(activity: MainAlarmActivity, sharedPreferences: SharedPreferences) =
            PreviewOfAlarmSettings(activity, activity, sharedPreferences)
}