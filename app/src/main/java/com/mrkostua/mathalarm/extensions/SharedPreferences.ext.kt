package com.mrkostua.mathalarm.extensions

import android.content.SharedPreferences

/**
 * @author Kostiantyn Prysiazhnyi on 3/5/2018.
 */

 inline fun SharedPreferences.edit(action: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    action(editor)
    editor.apply()
}

/**
 * to use extension in different package this fun need to be imported like .get or .*
 */
 operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? ->
            edit({ it.putString(key, value) })
        is Int ->
            edit({ it.putInt(key, value) })
        is Boolean ->
            edit({ it.putBoolean(key, value) })
        else ->
            throw UnsupportedOperationException("Not implemented")
    }
}

/**
 * to use extension in different package this fun need to be imported like .get or .*
 */
 inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T): T {
    return when (T::class) {
        String::class ->
            getString(key, defaultValue as String) as T
        Boolean::class ->
            getBoolean(key, defaultValue as Boolean) as T
        Int::class ->
            getInt(key, defaultValue as Int) as T
        else ->
            throw UnsupportedOperationException("Not implemented")
    }

}

