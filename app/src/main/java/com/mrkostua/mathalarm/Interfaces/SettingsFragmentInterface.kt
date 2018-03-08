package com.mrkostua.mathalarm.Interfaces

import android.content.Context
import android.view.View

/**
 * @author Kostiantyn Prysiazhnyi on 02.12.2017.
 */
interface SettingsFragmentInterface {
    fun saveSettingsInSharedPreferences()
    fun initializeDependOnViewVariables(view : View?)
    var fragmentContext: Context
}