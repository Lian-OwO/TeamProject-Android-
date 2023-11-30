package com.example.my_universe


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.my_universe.MyApplication.Companion.auth
import com.example.my_universe.databinding.ActivityLoginFormBinding
import com.example.my_universe.utils.SharedPreferencesManager
import com.firebase.ui.auth.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider


class LoginFormActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginFormBinding
    lateinit var id : String
    lateinit var pw : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                            val user = auth.currentUser
                            user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                                if (tokenTask.isSuccessful) {
                                    val accessToken = tokenTask.result?.token
                                    // 여기서 액세스 토큰을 사용하여 필요한 작업 수행
                                    SharedPreferencesManager.saveToken(this, accessToken)
                                    SharedPreferencesManager.saveLoginStatus(this, false)
                                    Log.d("로그인 후 토큰 가져오기", SharedPreferencesManager.getToken(this).toString())
                                    Log.d("로그인 후 토큰 가져오기", "Access Token: $accessToken")
                                } else {
                                    Log.d("로그인 후 토큰 가져오기", "Failed to get access token")
                                }
                            }
                            Toast.makeText(this, "본인인증을 시작합니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginFormActivity, PhoneAuthActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException){
                Log.d("로그인 ApiException", e.statusCode.toString())
                Toast.makeText(this, "로그인에 실패했습니다. 관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 로그인 버튼 클릭시 실행될 함수
        binding.SignInBtn.setOnClickListener {
            id = binding.inputID.text.toString()
            pw = binding.inputID.text.toString()

            // 이 부분부터 실시간 DB와 데이터 비교 후 로그인 처리하기.
        }
        // 회원가입 버튼 클릭시 실행될 함수
        binding.SignUpBtn.setOnClickListener {
            // 로그인 화면에서 회원가입 화면으로의 전환 코드
            val intent = Intent(this@LoginFormActivity, JoinActivity::class.java)
            Toast.makeText(this, "회원가입을 진행합니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        binding.googleLoginBtn.setOnClickListener {
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this,gso).signInIntent
            requestLauncher.launch(signInIntent)
        }

        binding.backBtn.setOnClickListener {
            SharedPreferencesManager.saveToken(this, null)
            SharedPreferencesManager.saveLoginStatus(this, false)
            auth.signOut()
            finish()
        }

    }
    fun onRegisterClick(view: View) {
        val intent = Intent(this, JoinActivity::class.java)
        startActivity(intent)
    }
}