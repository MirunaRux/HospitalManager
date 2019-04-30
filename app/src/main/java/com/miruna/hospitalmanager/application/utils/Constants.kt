package com.miruna.hospitalmanager.application.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Constants {

    val PREFS = "myPrefs"
    val PREFS_TERMS_ACCEPT = "myPrefs_Terms"
    val PREFS_REMEMBER = "myPrefs_Remember"
    val PREFS_USER = "myPrefs_User"
    val REQUEST_CODE_CREATE_ACCOUNT = 1997
    val EXTRA_KEY_CREATE_ACCOUNT_USER = "key_create_account_user"
    val RESULT_CODE_ADD_PACIENT = 234
    val RQUEST_CODE_ADD_PACIENT = 123
    val RQUEST_CODE_ADD_EVENT = 555
    val RESULT_CODE_ADD_EVENT = 666
    val RQUEST_CODE_ADD_DRUG = 222
    val RESULT_CODE_ADD_DRUG = 333
    val DATE_FORMAT_MDY = "dd.MM.yyyy"
    val DATE_FORMAT_MDYHM = "yyyy-MM-dd'T'HH:mm"
    val TIME_FORMAT_HHmm = "HH:mm"
    fun showKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideKeyboard(context: Context, currentView: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentView.windowToken, 0)
    }
}
