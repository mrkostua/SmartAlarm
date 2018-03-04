package com.mrkostua.mathalarm.injections.components

import com.mrkostua.mathalarm.Tools.SharedPreferencesHelper
import com.mrkostua.mathalarm.injections.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */
@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
    fun getPreferencesHelper() : SharedPreferencesHelper
}