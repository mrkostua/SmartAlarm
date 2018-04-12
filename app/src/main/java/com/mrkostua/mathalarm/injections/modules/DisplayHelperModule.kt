package com.mrkostua.mathalarm.injections.modules

import android.content.Context
import com.mrkostua.mathalarm.tools.NotificationTools
import dagger.Module
import dagger.Provides

/**
 * @author Kostiantyn Prysiazhnyi on 3/18/2018.
 */
@Module
class DisplayHelperModule {
    @Provides
    fun getNotificationsTools(context: Context) = NotificationTools(context)
}