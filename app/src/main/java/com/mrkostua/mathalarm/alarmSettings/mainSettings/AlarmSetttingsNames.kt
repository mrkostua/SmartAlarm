package com.mrkostua.mathalarm.alarmSettings.mainSettings

/**
 * @author Kostiantyn Prysiazhnyi on 3/12/2018.
 */
enum class AlarmSettingsNames {
    OPTION_SET_TIME {
        override fun getKeyValue(): Int = 0

    },
    OPTION_SET_RINGTONE {
        override fun getKeyValue(): Int = 1

    },
    OPTION_SET_MESSAGE {
        override fun getKeyValue(): Int = 2

    },
    OPTION_SET_DEEP_SLEEP_MUSIC {
        override fun getKeyValue(): Int = 3

    };

    abstract fun getKeyValue(): Int
}