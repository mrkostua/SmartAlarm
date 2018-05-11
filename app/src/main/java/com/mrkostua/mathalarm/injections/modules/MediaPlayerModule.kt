package com.mrkostua.mathalarm.injections.modules

import android.content.Context
import android.media.MediaPlayer
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.MediaPlayerHelper
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 5/10/2018.
 */
@Module
class MediaPlayerHelperModule {
    @Provides
    fun provideMediaPlayerHelper(context: Context): MediaPlayerHelper = MediaPlayerHelper(context, MediaPlayer())
}