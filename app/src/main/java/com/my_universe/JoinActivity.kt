package com.my_universe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.my_universe.databinding.ActivityJoinBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class JoinActivity : AppCompatActivity() {
    lateinit var binding : ActivityJoinBinding
    lateinit var email : String
    lateinit var password : String
    lateinit var passwordCheck : String
    lateinit var name : String
    lateinit var phoneNum : String
    private lateinit var database: DatabaseReference
// ...

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)


        database = Firebase.database.reference

        binding.joinButton.setOnClickListener {
            // 리얼 타임 디비에 쓰기.
            // Create a new user with a first and last name
            email = binding.joinEmail.text.toString()
            password = binding.joinPassword.text.toString()
            passwordCheck = binding.joinPwck.text.toString()
            name = binding.joinName.text.toString()
            phoneNum = ""

            if(password == passwordCheck) {
                val intent = Intent(this@JoinActivity, PhoneAuthActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                intent.putExtra("name", name)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@JoinActivity, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }

            Log.d("scb", "name : ${name}")
            Log.d("scb", "password : ${password}")
            Log.d("scb", "passwordCheck : ${passwordCheck}")
            Log.d("scb", "email : ${email}")
        }
        binding.delete.setOnClickListener {
            finish()
        }



        // 리얼 타임 디비에 가져오기.


    }
}