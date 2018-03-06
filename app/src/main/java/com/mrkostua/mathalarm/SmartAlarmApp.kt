package com.mrkostua.mathalarm

import android.app.Application
import com.mrkostua.mathalarm.injections.components.ApplicationComponent
import com.mrkostua.mathalarm.injections.modules.ApplicationModule

/**
 * @author Kostiantyn Prysiazhnyi on 3/4/2018.
 */
open class SmartAlarmApp : Application() {
    open val applicationComponent: ApplicationComponent by l azy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
/** learning injection with Dagger 2
 * So modules - they provides some variables as Context any other complex or simple objects.
 * Components - they with help of interfaces give to actual developer access to variables provided by Modules.
 */

