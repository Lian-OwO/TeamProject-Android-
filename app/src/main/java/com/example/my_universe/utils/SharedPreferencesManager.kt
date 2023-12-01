package com.example.my_universe.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private const val KEY_Email = "email"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"

    // 액세스 토큰 저장
    fun saveEmail(context: Context, email : String?) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(KEY_Email, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_Email, email)
        editor.apply()
    }
    fun getEmail(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(KEY_Email, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_Email, null)
    }
    // 로그인 상태 저장
    fun saveLoginStatus(context: Context, isLoggedIn: Boolean) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(KEY_IS_LOGGED_IN, Context.MODE_PRIVATE).edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }
    fun getLoginStatus(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(KEY_IS_LOGGED_IN, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_IS_LOGGED_IN, null)
    }
}
