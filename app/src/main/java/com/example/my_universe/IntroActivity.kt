package com.example.my_universe


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.my_universe.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3초 후에 메인 화면으로 이동
        Handler().postDelayed({
            // 메인 화면으로 이동하는 Intent 생성
            val intent = Intent(this@IntroActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // IntroActivity 종료
        }, 3000) // 3초 지연
    }

//    fun showLoginPage(view: View) {
//        // 로그인 화면으로 이동하는 Intent 생성
//        val intent = Intent(this@IntroActivity, LoginFormActivity::class.java)
//        startActivity(intent)
//    }
}