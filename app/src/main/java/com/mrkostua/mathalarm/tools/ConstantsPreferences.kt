package com.mrkostua.mathalarm.tools

/**
 * @author Kostiantyn Prysiazhnyi on 10.12.2017.
 */
enum class ConstantsPreferences {
    ALARM_SP_NAME {
        override fun getKeyValue(): String = "last_alarm_data"
        override fun getDefaultIntValue(): Int = -1
    },
    ALARM_HOURS() {
        override fun getKeyValue(): String = "hours"
        override fun getDefaultIntValue(): Int = 8

    },
    ALARM_MINUTES() {
        override fun getKeyValue(): String = "minutes"
        override fun getDefaultIntValue(): Int = 15

    },
    ALARM_TEXT_MESSAGE() {
        override fun getKeyValue(): String = "text_message"
        override fun getDefaultIntValue(): Int = -1
    },
    ALARM_RINGTONE_NAME() {
        override fun getKeyValue(): String = "ringtone_name"
        override fun getDefaultIntValue(): Int = -1
    },
    ALARM_DEEP_WAKE_UP_STATE() {
        override fun getKeyValue(): String = "deep_wake_up_state"
        override fun getDefaultIntValue(): Int = 0 //0 false, 1 true
    },
    TASKS_EXPLANATION_SHOW_STATE() {
        override fun getKeyValue(): String = "tasks_explanation_show_state"
        override fun getDefaultIntValue(): Int = 1 //0 false, 1 true
    },
    ALARM_DEEP_WAKE_UP_RINGTONE() {
        override fun getKeyValue(): String = "deep_wake_up_ringtone"
        override fun getDefaultIntValue(): Int = -1
    };

    abstract fun getKeyValue(): String
    abstract fun getDefaultIntValue(): Int

    val defaultTextMessage = "Hello"
    val defaultRingtoneName = "energy"
    val defaultDeepWakeUpRingtone = "calm"
    val emptyPreferencesValue = -1


}