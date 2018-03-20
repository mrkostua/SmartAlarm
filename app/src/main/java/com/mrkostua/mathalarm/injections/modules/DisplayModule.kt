package com.mrkostua.mathalarm.injections.modules

import android.content.Context
import com.mrkostua.mathalarm.tools.NotificationsTools
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 3/18/2018.
 */
@Module
class DisplayModule {
    @Provides
    fun getNotificationsTools(context: Context): NotificationsTools = NotificationsTools(context)
}