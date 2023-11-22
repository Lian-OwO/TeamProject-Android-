package com.example.my_universe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class LoginFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)
    }
    fun onRegisterClick(view: View) {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }
}