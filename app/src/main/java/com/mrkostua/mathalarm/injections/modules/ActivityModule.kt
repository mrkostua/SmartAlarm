package com.mrkostua.mathalarm.injections.modules

import android.app.Activity
import android.content.Context
import com.mrkostua.mathalarm.injections.annotation.ActivityContext
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 3/6/2018.
 */

@Module
public class ActivityModule(private val activity: Activity) {
    @Provides
    @ActivityContext
    fun provideActivityContext(): Context = activity

    @Provides
    fun provideActivity(): Activity = activity


}
