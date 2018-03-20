package com.mrkostua.mathalarm.injections.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */

@Module
public abstract class ApplicationModule {
    @Binds
    abstract fun provideAppContext(app: Application): Context

}