package com.mrkostua.mathalarm

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * @author Kostiantyn on 21.11.2017.
 */
public object SharedPreferencesHelper {
    fun defaultSharedPreferences(context: Context): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(context)

    fun customSharedPreferences(context: Context, nameOfDataSet: String): SharedPreferences
            = context.getSharedPreferences(nameOfDataSet, Context.MODE_PRIVATE)

    //extension function,
    //inline keyword (read! use it in small func with extension func to avoid the cost of higher-order functions
    // (more about how compiler works and costs of creating and saving this func)
    inline fun SharedPreferences.edit(action: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        action(editor)
        editor.apply()
    }

    public operator fun SharedPreferences.set(key: String, value: Any?) {
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

    public operator inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class ->
                getString(key, defaultValue as? String) as T?
            Boolean::class ->
                getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Int::class ->
                getInt(key, defaultValue as? Int ?: -1) as T?
            else ->
                throw UnsupportedOperationException("Not implemented")
        }

    }

}
