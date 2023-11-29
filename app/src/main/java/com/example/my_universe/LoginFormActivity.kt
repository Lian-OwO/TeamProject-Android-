package com.example.my_universe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.my_universe.MyApplication.Companion.auth
import com.example.my_universe.MyApplication.Companion.email
import com.example.my_universe.databinding.ActivityLoginFormBinding
import com.example.my_universe.retrofit.RetrofitAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.play.integrity.internal.t
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFormActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(MyApplication.checkAuth()){
            Log.d("lsy","로그인 인증이 됨")
            auth.currentUser?.getIdToken(false)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result?.token
                        Log.d("로그인 체크", "로그인이 되어있습니다.")
                    } else {
                        val exception = task.exception
                    }
                }
        } else {
            Log.d("로그인 체크","로그인 되어있지 않습니다.")
            // 인증 되면, mode에 따라 보여주는 함수를 동작 시키기
            Toast.makeText(this, "로그인 후 메인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
        }

        val requestLauncher = registerForActivityResult(
            // 후처리 하는 함수가 정해져 있는데, 이함수를 인증 , 권한 확인용
            ActivityResultContracts.StartActivityForResult()
        ){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) {
                            task ->
                        if(task.isSuccessful){
                            // 액세스 토큰 가져오기
                            Log.d("로그인 레트로핏 체크", "실행됨")
                            val accessToken = auth.currentUser?.getIdToken(false)?.result?.token
                            Log.d("로그인 레트로핏", "${accessToken}")
                            Log.d("로그인 레트로핏", "${task.result.user?.email.toString()}")
                            val testService = RetrofitAdapter().testService
                            val testCall = testService.getTest("Bearer $accessToken", task.result.user?.email.toString())
                            testCall.enqueue(object : Callback<Map<String, String>> {
                                override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                                    Log.d("로그인 레트로핏", "${response?.code()}")

                                }
                                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                                    // 데이터를 못 받았을 때 수행되는 함수
                                    Log.d("로그인 레트로핏", "데이터를 받는데 실패함")
                                    Log.d("로그인 레트로핏 실패", t.message.toString())
                                    call.cancel()
                                }
                            })
                            Toast.makeText(this, "로그인 됨", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException){
                Log.d("로그인 ApiException", e.statusCode.toString())
            }
        }

        binding.googleAuthInBtn.setOnClickListener {
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this,gso).signInIntent
            requestLauncher.launch(signInIntent)
        }

        binding.SignOutBtn.setOnClickListener {
            auth.signOut()
            val currentUser = auth.currentUser
            if (currentUser == null) {
                Toast.makeText(this, "로그아웃 됨", Toast.LENGTH_SHORT).show()
                Log.d("로그아웃 검증", "사용자가 로그아웃되었습니다.")
            } else {
                Log.d("로그아웃 검증", "사용자가 로그아웃되지 않았습니다.")
            }
        }
    }
    fun onRegisterClick(view: View) {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }
}