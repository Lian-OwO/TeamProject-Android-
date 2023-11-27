package com.example.my_universe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.my_universe.MyApplication.Companion.auth
import com.example.my_universe.MyApplication.Companion.email
import com.example.my_universe.databinding.ActivityLoginFormBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFormActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(MyApplication.checkAuth()){
            Log.d("lsy","로그인 인증이 됨")
            // 인증 되면, mode에 따라 보여주는 함수를 동작 시키기
            auth.currentUser?.getIdToken(false)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 액세스 토큰을 성공적으로 얻었을 때의 코드
                        val token = task.result?.token
                        Log.d("로그인 이메일", token.toString())
                        // 여기에서 액세스 토큰을 사용하거나 다른 작업 수행
                    } else {
                        // 작업이 실패한 경우의 코드
                        val exception = task.exception
                        // 오류 처리
                    }
                }
        } else {
            Log.d("lsy","로그인 인증이 안됨")
            // 인증 되면, mode에 따라 보여주는 함수를 동작 시키기
            Toast.makeText(this, "로그인 후 메인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
        }

        val requestLauncher = registerForActivityResult(
            // 후처리 하는 함수가 정해져 있는데, 이함수를 인증 , 권한 확인용
            ActivityResultContracts.StartActivityForResult()
        ){
            // 실제 작업은 여기 이루어짐.
            // 구글 인증 결과 처리.
            // it.data 이부분이 구글로부터 받아온 계정 정보.
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            // 로그인 정보가 있는지? 없는지? 또는 네트워크 연결 오류등으로
            // 정보가 받거나 못 받거나 할 가능성이 있으면, 무조건, try catch 구문 사용함.
            try {
                // 계정 정보 가져오기.
                val account = task.getResult(ApiException::class.java)
                // 계정의 정보 가져오기.
                val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                // 우리가 만든 MyApplication에서 auth로 확인
                MyApplication.auth.signInWithCredential(credential)
                    // 정보를 잘 가지고 왔을 때 , 수행이 될 콜백함수,
                    .addOnCompleteListener(this) {
                        // 수행할 작업.
                            task ->
                        if(task.isSuccessful){
                            email = account.email
                            Toast.makeText(this, "로그인 됨", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException){
                Log.d("로그인 ApiException", e.message.toString())
            }
        }
        binding.JoinBtn.setOnClickListener {
            val email = binding.authEmailEdit.text.toString()
            val password = binding.authPasswordEdit.text.toString()

            auth.createUserWithEmailAndPassword(email,password)
                // 회원가입이 잘 되었을 경우, 호출되는 콜백함수임.
                .addOnCompleteListener(this) {
                        task ->
                    binding.authEmailEdit.text.clear()
                    binding.authPasswordEdit.text.clear()
                    if(task.isSuccessful) {
                        // 회원 가입 성공 한 경우,
                        Log.d("로그인 이메일", task.result?.user?.email.toString())
                        Log.d("로그인 uid", task.result?.user?.uid.toString())
                        auth.currentUser?.sendEmailVerification()
                            // 회원가입한 이메일에 인증 링크를 잘보냈다면, 수항해라 콜백함수.
                            ?.addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "회원가입 성공, 전송된 메일 확인해주세요.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "메일 발송 실패",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                    }else {
                        // 회원 가입 실패 한 경우,
                        Toast.makeText(this,"회원가입 실패",Toast.LENGTH_SHORT).show()
                    }
                }
        }
        // 구글 인증 버튼 클릭시, 해당 구글 계정 선택 화면으로 이동하는 인텐트 추가하기.
        binding.googleAuthInBtn.setOnClickListener {
            // 샘플 코드
            // 옵션, 이메일, 아이디토큰 가져오는 옵션
            val gso = GoogleSignInOptions
                // 오타, DEFAULT_SIGN_IN 인데 여기서 게임으로 잘못 잡혔음.
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build()
            // 구글 인증 화면으로 이동하는 코드
            val signInIntent = GoogleSignIn.getClient(this,gso).signInIntent
            // 후처리 함수 동작 연결시키기.
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