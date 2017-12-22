package com.mrkostua.mathalarm.AlarmSettings

import android.content.Context

/**
 * @author Kostiantyn Prysiazhnyi on 02.12.2017.
 */
interface SettingsFragmentInterface {
    fun saveSettingsInSharedPreferences()
    var fragmentContext : Context
}