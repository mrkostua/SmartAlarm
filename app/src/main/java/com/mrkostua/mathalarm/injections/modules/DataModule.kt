package com.mrkostua.mathalarm.injections.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mrkostua.mathalarm.alarmSettings.optionSetRingtone.RingtoneManagerHelper
import com.mrkostua.mathalarm.tools.ConstantsPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Kostiantyn Prysiazhnyi on 3/18/2018.
 */
@Module
class DataModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences = app.getSharedPreferences(ConstantsPreferences.ALARM_SP_NAME.getKeyValue(), Context.MODE_PRIVATE)

    @Provides
    fun provideRingtoneManagerHelper(context: Context) = RingtoneManagerHelper(context)

}