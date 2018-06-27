package com.mrkostua.mathalarm.alarmSettings.mainSettings

import com.mrkostua.mathalarm.alarmSettings.FragmentCreationHelper
import com.mrkostua.mathalarm.injections.scope.ActivityScope
import com.mrkostua.mathalarm.tools.ConstantValues
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 3/19/2018.
 */
@Module
class AlarmSettingsModule {
    @ActivityScope
    @Provides
    fun provideFragmentCreationHelper(activity: AlarmSettingsActivity) = FragmentCreationHelper(activity)

    @ActivityScope
    @Provides
    fun provideAlarmSettingsPresenter(fragmentToLoad: AlarmSettingsNames, activity: AlarmSettingsActivity)
            : AlarmSettingsContract.Presenter = AlarmSettingsPresenter(fragmentToLoad, activity)

    @ActivityScope
    @Provides
    fun provideIndexOfFragmentToLoad(activity: AlarmSettingsActivity): AlarmSettingsNames =
            AlarmSettingsNames.OPTION_SET_TIME.getAlarmSettingName(
                    activity.intent.getIntExtra(ConstantValues.INTENT_KEY_WHICH_FRAGMENT_TO_LOAD_FIRST,
                            AlarmSettingsNames.OPTION_WRONG.getKeyValue()))
}