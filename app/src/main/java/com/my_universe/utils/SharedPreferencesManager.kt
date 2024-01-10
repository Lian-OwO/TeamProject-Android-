package com.my_universe.utils

import android.content.Context
import android.content.SharedPreferences
import com.my_universe.MainActivity

object SharedPreferencesManager {

    private const val KEY_Email = "email"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    private const val KEY_TOKEN = "token"
    private const val KEY_NAME = "name"

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
    fun getLoginStatus(context: MainActivity): Boolean? {
        val sharedPreferences = context.getSharedPreferences(KEY_IS_LOGGED_IN, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    fun saveToken(context: Context, token: String?) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }
    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(KEY_TOKEN, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_TOKEN, "none")
    }
    fun saveName(context: Context, name: String?) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_NAME, name)
        editor.apply()
    }
    fun getName(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_NAME, null)
    }
}