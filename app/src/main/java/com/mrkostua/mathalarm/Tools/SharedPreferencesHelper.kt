package com.mrkostua.mathalarm.Tools

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Kostiantyn on 21.11.2017.
 */

@Singleton
object SharedPreferencesHelper {
    private val TAG = this.javaClass.simpleName
    public fun defaultSharedPreferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Inject
    public fun customSharedPreferences(context: Context, nameOfDataSet: String): SharedPreferences {
        return context.getSharedPreferences(nameOfDataSet, Context.MODE_PRIVATE)

    }

    //extension function,
    //inline keyword (read! use it in small func with extension func to avoid the cost of higher-order functions
    // (more about how compiler works and costs of creating and saving this func)
    public inline fun SharedPreferences.edit(action: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        action(editor)
        editor.apply()
    }

    /**
     * to use extension in different package this fun need to be imported like .get or .*
     */
    public operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? ->
                edit({ it.putString(key, value) })
            is Int ->
                edit({ it.putInt(key, value) })
            is Boolean ->
                edit({ it.putBoolean(key, value) })
/*            is MutableSet<*> -> {
                    edit({ it.putStringSet(key, value as MutableSet<String>?) })
            }*/

            else ->
                throw UnsupportedOperationException("Not implemented")
        }
    }

    //todo Think about creating additional fun for get(PreferencesConstants : Enum) so default value will be get inside this method not every time and result can but not null so we eliminate checking for null result
    /**
     * to use extension in different package this fun need to be imported like .get or .*
     */
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
