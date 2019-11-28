package com.rajkiran.skeleton.util

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

object SharedPrefsManager {

    fun putInt(key: String, value: Int, context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit { putInt(key, value) }
    }

    fun getInt(key: String, defaultValue: Int, context: Context): Int {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue)
    }

    fun putLong(key: String, value: Long, context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit { putLong(key, value) }
    }

    fun getLong(key: String, defaultValue: Long, context: Context): Long {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean, context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String, defaultValue: Boolean, context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue)
    }

    fun putString(key: String, value: String, context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit { putString(key, value) }
    }

    fun getString(key: String, defaultValue: String, context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue)
    }

}