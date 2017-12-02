package com.mrkostua.mathalarm.AlarmSettingsOptions

/**
 * @author Kostiantyn Prysiazhnyi on 02.12.2017.
 */
interface AlarmSettingsOptionsHelper {
    fun saveSettingsInSharedPreferences()
    var settingsOptionIndex : Int
}