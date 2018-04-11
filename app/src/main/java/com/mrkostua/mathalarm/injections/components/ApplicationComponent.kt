package com.mrkostua.mathalarm.injections.components

import android.app.Application
import com.mrkostua.mathalarm.SmartAlarmApp
import com.mrkostua.mathalarm.data.AlarmDataHelper
import com.mrkostua.mathalarm.injections.modules.ActivityBindingModule
import com.mrkostua.mathalarm.injections.modules.ApplicationModule
import com.mrkostua.mathalarm.injections.modules.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */
@Singleton
@Component(modules = [(ApplicationModule::class),
    (DataModule::class),
    (AndroidSupportInjectionModule::class),
    (ActivityBindingModule::class)])
public interface ApplicationComponent : AndroidInjector<SmartAlarmApp> {

    public fun getAlarmDataHelper(): AlarmDataHelper

    @Component.Builder
    public interface Builder {
        @BindsInstance
        fun application(app: Application): ApplicationComponent.Builder

        fun build(): ApplicationComponent
    }
}