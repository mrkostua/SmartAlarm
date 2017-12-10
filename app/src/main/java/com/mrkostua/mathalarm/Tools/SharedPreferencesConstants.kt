package com.mrkostua.mathalarm.Tools

/**
 * @author Kostiantyn Prysiazhnyi on 10.12.2017.
 */
enum class SharedPreferencesConstants {
    ALARM_SP_NAME {
        override fun getKeyValue(): String = "last_alarm_data"
        override fun getIntValue(): Int = -1

    },
    ALARM_HOURS() {
        override fun getKeyValue(): String = "hours"
        override fun getIntValue(): Int = 8

    },
    ALARM_MINUTES() {
        override fun getKeyValue(): String = "minutes"
        override fun getIntValue(): Int = 15

    },
    ALARM_TEXT_MESSAGE(){
        override fun getKeyValue(): String = "text_message"
        override fun getIntValue(): Int = -1
    },
    ALARM_RINGTONE_RES_ID(){
        override fun getKeyValue(): String = "ringtone_res_id"
        override fun getIntValue(): Int = -1 //todo change ringtone custom id or replace it is initialization
    };


    abstract fun getKeyValue(): String
    abstract fun getIntValue(): Int

}