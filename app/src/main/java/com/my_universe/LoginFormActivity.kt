package com.my_universe


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.my_universe.MyApplication.Companion.auth
import com.my_universe.databinding.ActivityLoginFormBinding
import com.my_universe.model.RequestResultVO
import com.my_universe.model.User
import com.my_universe.utils.SharedPreferencesManager
import com.firebase.ui.auth.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFormActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginFormBinding
    lateinit var email : String
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
                                    SharedPreferencesManager.saveLoginStatus(this, false)
                                    Log.d("로그인 후 토큰 가져오기", "Access Token: $accessToken")
                                } else {
                                    Log.d("로그인 후 토큰 가져오기", "Failed to get access token")
                                }
                            }
                            Toast.makeText(this, "본인인증을 시작합니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginFormActivity, PhoneAuthActivity::class.java)
                            intent.putExtra("email", auth.currentUser?.email.toString())
                            Log.d("로그인 전화인증 전", auth.currentUser?.email.toString())
                            intent.putExtra("password", "!!undefined!!")
                            intent.putExtra("name", "!!undefined!!")
                            startActivity(intent)
                            Log.d("로그인 구글 인증 직후", auth.currentUser?.email.toString())
                            Log.d("로그인 구글 인증 직후", auth.currentUser?.uid.toString())
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
            email = binding.inputID.text.toString()
            pw = binding.inputPW.text.toString()
            val user: User = User(null, email, pw, null)

            val networkService = (applicationContext as MyApplication).androidServer
            val registerCall = networkService.doLogin(user)
            val registerCallTest = networkService.getAuthPage()
            registerCall.enqueue(object : Callback<RequestResultVO> {
                override fun onResponse(
                    call: Call<RequestResultVO>,
                    response: Response<RequestResultVO>
                ) {
                    if (response.isSuccessful) {
                        // 성공적인 응답 처리
                        val result = response?.body()
                        val isLogin : Boolean = result?.result!!
                        if(isLogin) {

                            Log.d("로그인, 요청 보냄", "요청 보냄")

                            Log.d("로그인 후 결과값 체크", "서버 메세지 : ${result?.message}")
                            Log.d("로그인 후 결과값 체크", "서버 메세지 : ${result?.token}")
                            Log.d("로그인 후 결과값 체크", "서버 메세지 : ${result?.userDto?.username}")
                            Log.d("로그인 후 결과값 체크", "서버 메세지 : ${result?.userDto?.email}")
                            SharedPreferencesManager.saveName(this@LoginFormActivity, result?.userDto?.username)
                            SharedPreferencesManager.saveEmail(this@LoginFormActivity, result?.userDto?.email)
                            SharedPreferencesManager.saveToken(this@LoginFormActivity, result?.token)
                            SharedPreferencesManager.saveLoginStatus(this@LoginFormActivity, true)
                            Toast.makeText(this@LoginFormActivity, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
                            registerCallTest.enqueue(object : Callback<RequestResultVO> {
                                override fun onResponse(
                                    call: Call<RequestResultVO>,
                                    response: Response<RequestResultVO>
                                ) {
                                    val test = response.body()
                                    Log.d("로그인 후 인증페이지 리턴", test?.message!!)
                                    Log.d("로그인 후 인증페이지 리턴", test?.token!!)
                                    Log.d("로그인 후 인증페이지 리턴", test?.result.toString())
                                }

                                override fun onFailure(call: Call<RequestResultVO>, t: Throwable) {
                                    Log.d("로그인 후 인증페이지 리턴", "실패함")
                                }

                            })
                            finish()
                        } else{
                            Log.d("로그인 후 결과값 체크", "서버 메세지 : ${result?.message}")
                            Toast.makeText(this@LoginFormActivity, "아이디와 패스워드를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // 응답이 실패한 경우
                        Log.d("로그인 후 결과값 체크", "응답 실패, 코드: ${response.code()}")
                        Toast.makeText(this@LoginFormActivity, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<RequestResultVO>, t: Throwable) {
                    Log.d("로그인 후 결과값 체크", "요청 실패..")
                    Log.d("로그인 후 결과값 체크", t.toString())
                    auth.signOut()
                }
            })

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