package com.mrkostua.mathalarm.tools

/**
 * @author Kostiantyn Prysiazhnyi on 10.12.2017.
 */
enum class PreferencesConstants {
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
    ALARM_DEEP_SLEEP_MUSIC() {
        override fun getKeyValue(): String = "deep_sleep_music"
        override fun getDefaultIntValue(): Int = 0 //0 false, 1 true
    };

    abstract fun getKeyValue(): String
    abstract fun getDefaultIntValue(): Int

    val defaultTextMessage = "It is nice to see you alive."
    val defaultRingtoneName = "ringtone_energy"


}