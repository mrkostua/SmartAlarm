package com.mrkostua.mathalarm.alarms.mathAlarm.services.displayAlarmService

import android.media.MediaPlayer
import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.injections.scope.ServiceScope
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 4/2/2018.
 */
@Module
class DisplayAlarmServiceModule {
    @ServiceScope
    @Provides
    fun provideDisplayAlarmServicePresenter(dataHelper: AlarmDataHelper, mediaPlayer: MediaPlayer):
            DisplayAlarmServiceContract.Presenter = DisplayAlarmServicePresenter(dataHelper, mediaPlayer)
}