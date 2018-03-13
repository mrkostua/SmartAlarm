package com.mrkostua.mathalarm.injections.modules

import android.content.Context
import android.content.SharedPreferences
import com.mrkostua.mathalarm.SmartAlarmApp
import com.mrkostua.mathalarm.tools.PreferencesConstants
import com.mrkostua.mathalarm.injections.annotation.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */

@Module
public class ApplicationModule(private val app: SmartAlarmApp) {
    @Provides
    @Singleton
    fun provideSmartAlarmApp() = app

    @Provides
    @Singleton
    fun provideSharedPreferences(app: SmartAlarmApp): SharedPreferences = app.getSharedPreferences(PreferencesConstants.ALARM_SP_NAME.getKeyValue(), Context.MODE_PRIVATE)

    @Provides
    @ApplicationContext
    fun provideContext() : Context = app
}