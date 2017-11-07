package com.mrkostua.mathalarm;

/**
 * Created by Администратор on 07.11.2017.
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
}
