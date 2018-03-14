package com.mrkostua.mathalarm.injections.modules

import android.content.Context
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import com.mrkostua.mathalarm.injections.annotation.ApplicationContext
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 3/14/2018.
 */
@Module
class RingtoneManagerModule {
    @Provides
    fun provideRingtoneManagerHelper(@ApplicationContext context: Context): RingtoneManagerHelper = RingtoneManagerHelper(context)

}