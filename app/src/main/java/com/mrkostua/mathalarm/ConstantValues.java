package com.mrkostua.mathalarm;

/**
 * @author Kostiantyn on 07.11.2017.
 */

public class ConstantValues {
    /**
     *  LOG_DEBUG_STATUS true for debug mode, false for production.
     */
    static final boolean LOG_DEBUG_STATUS  = true;



    public static final String[] ALARM_RINGTONE_NAMES = {"mechanic_clock","energy","loud","digital_clock"};

    //actions for alarm receiver
    public static final String SNOOZE_ACTION = "alarm_snooze";
    public static final String DISMISS_ACTION = "alarm_dismiss";
    public static final String START_NEW_ALARM_ACTION = "alarm_start_new";

    public static final String ALARM_SHARED_PREFERENCE_NAME = "LAST_ALARM_DATA";
    public static final String LAST_ALARM_HOURS = "hours";
    public static final String LAST_ALARM_MINUTES = "minutes";

}
