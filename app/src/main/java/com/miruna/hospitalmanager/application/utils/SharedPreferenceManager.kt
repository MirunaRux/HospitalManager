package com.miruna.hospitalmanager.application.utils

import android.content.Context

object SharedPreferenceManager {

    fun saveRememberMe(context: Context, remember: Boolean) {
        val pref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        pref.edit().putBoolean(Constants.PREFS_REMEMBER, remember).commit()
    }

    fun saveUsername(context: Context, user: String) {
        val pref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        pref.edit().putString(Constants.PREFS_USER, user).commit()
    }

    fun loadRemember(context: Context): Boolean {
        val pref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        return pref.getBoolean(Constants.PREFS_REMEMBER, false)
    }

    fun loadUsername(context: Context): String? {
        val pref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        return pref.getString(Constants.PREFS_USER, "")
    }

    fun savePacientId(context: Context, user: String) {
        val pref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        pref.edit().putString(Constants.PREFS_USER, user).commit()

    }

    fun saveFileId(context: Context, user: String) {
        val pref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        pref.edit().putString(Constants.PREFS_USER, user).commit()
    }
}
