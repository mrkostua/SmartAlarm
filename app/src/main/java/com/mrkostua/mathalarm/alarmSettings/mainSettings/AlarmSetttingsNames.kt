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
    OPTION_SET_DEEP_WAKE_UP {
        override fun getKeyValue(): Int = 3

    },
    OPTION_WRONG {
        override fun getKeyValue(): Int = -1
    };

    abstract fun getKeyValue(): Int

    fun getAlarmSettingName(keyValue: Int): AlarmSettingsNames {
        return when (keyValue) {
            OPTION_SET_TIME.getKeyValue() -> OPTION_SET_TIME
            OPTION_SET_RINGTONE.getKeyValue() -> OPTION_SET_RINGTONE
            OPTION_SET_MESSAGE.getKeyValue() -> OPTION_SET_MESSAGE
            OPTION_SET_DEEP_WAKE_UP.getKeyValue() -> OPTION_SET_DEEP_WAKE_UP
            OPTION_WRONG.getKeyValue() -> OPTION_WRONG
            else -> OPTION_SET_TIME
        }
    }
}