package com.example.my_universe.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private const val KEY_TOKEN = "accessToken"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"

    // 액세스 토큰 저장
    fun saveToken(context: Context, accessToken : String?) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_TOKEN, accessToken)
        editor.apply()
    }
    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_TOKEN, null)
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
