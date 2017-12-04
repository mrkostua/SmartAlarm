package com.mrkostua.mathalarm.AlarmSettings

/**
 * @author Kostiantyn Prysiazhnyi on 02.12.2017.
 */
interface SettingsFragmentInterface {
    fun saveSettingsInSharedPreferences()
    var settingsOptionIndex : Int
}