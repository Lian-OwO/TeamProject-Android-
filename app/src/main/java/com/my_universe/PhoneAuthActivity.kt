package com.my_universe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.my_universe.MyApplication.Companion.auth
import com.my_universe.databinding.ActivityPhoneAuthBinding
import com.my_universe.model.RequestResultVO
import com.my_universe.model.User
import com.my_universe.utils.SharedPreferencesManager
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityPhoneAuthBinding
    lateinit var phoneNum : String
    lateinit var authNum : String
    lateinit var messageAuthCode : String
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.reference
        // 뒤로가기 클릭시
        binding.backBtn.setOnClickListener {
            SharedPreferencesManager.saveLoginStatus(this, false)
            auth.signOut()
            Log.d("로그인 뒤로가기 클릭시 유저 정보", auth.currentUser?.email.toString())
            Log.d("로그인 뒤로가기 클릭시 유저 정보", auth.currentUser?.uid.toString())
            finish()
        }
        // 인증요청 버튼 클릭시
        binding.reqAuth.setOnClickListener {
            phoneNum = binding.inputPhone.text.toString()
            // 공식문서 그대로 가져옴, 전화번호 인증을 위한 옵션 설정
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNum)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks) // 콜백함수 정의 해줘야 함. 구현해야 하는 메서드 있음.
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        // 인증번호 확인 버튼 눌렀을 때
        binding.checkAuth.setOnClickListener {
            val userCode = binding.inputAuthNum.text.toString()
            // 전화번호와 사용자가 입력한 인증번호를 사용하여 PhoneAuthCredential 객체 생성
            val credential = PhoneAuthProvider.getCredential(messageAuthCode, userCode)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 인증 성공
                        // 추가로 필요한 작업 수행
                        Log.d("로그인 과정 중 전화번호 인증", "사용자가 직접 입력한 인증번호가 유효합니다.")
                        Log.d("로그인 과정 중 전화번호 인증", auth.currentUser?.email.toString())
                        Log.d("로그인 과정 중 전화번호 인증", auth.currentUser?.uid.toString())
                        Toast.makeText(this@PhoneAuthActivity, "인증 성공했습니다.", Toast.LENGTH_SHORT).show()

                        val email = intent.getStringExtra("email")
                        val password = intent.getStringExtra("password")
                        val name = intent.getStringExtra("name")
                        Log.d("로그인 과정 중 전화번호 인증", phoneNum)
                        val user : User = User(name, email, password, phoneNum)
                        Log.d("로그인 과정 중 전화번호 인증", "${user.username} ${user.email} ${user.password} ${user.phoneNum} ")

                        val networkService = (applicationContext as MyApplication).androidServer
                        val registerCall = networkService.doRegister(user)
                        registerCall.enqueue(object : Callback<RequestResultVO> {
                            override fun onResponse(
                                call: Call<RequestResultVO>,
                                response: Response<RequestResultVO>
                            ) {
                                if (response.isSuccessful) {
                                    // 성공적인 응답 처리
                                    val result = response?.body()
                                    Log.d("로그인, 요청 보냄", "요청 보냄")
                                    Log.d("로그인 후 결과값 체크", "서버 메세지 : ${result?.message}")
                                    finish()
                                } else {
                                    // 응답이 실패한 경우
                                    Log.d("로그인 후 결과값 체크", "응답 실패, 코드: ${response.code()}")
                                    auth.signOut()
                                }
                            }
                            override fun onFailure(call: Call<RequestResultVO>, t: Throwable) {
                                Log.d("로그인 후 결과값 체크", "요청 실패..")
                                Log.d("로그인 후 결과값 체크", t.toString())
                                auth.signOut()
                            }
                        })


                    } else {
                        // 인증 실패, 구글 인증한 사용자 정보 비우기
                        auth.signOut()
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // 사용자가 입력한 인증번호가 유효하지 않은 경우
                            Log.e("로그인 과정 중 전화번호 인증", "유효하지 않은 인증번호입니다.")
                            Toast.makeText(this@PhoneAuthActivity, "인증 코드를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                        } else {
                            // 기타 인증 오류 처리
                            Log.e("로그인 과정 중 전화번호 인증", "인증 실패: ${task.exception}")
                        }
                    }
                }
        }
    }
//    override fun onPause() {
//        super.onPause()
//        SharedPreferencesManager.saveToken(this, null)
//        SharedPreferencesManager.saveLoginStatus(this, false)
//        auth.signOut()
//        finish()
//    }

    // 콜백함수 설정 부분.
    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        // 이 함수는 사용자가 문자를 수신했을 때, 파이어 베이스 SDK가 인증코드를 추출 후 자동으로 인증 완료 했을 때 호출되는 함수임.
        // 전화번호 인증 성공한다고 해서 파이어 베이스 로그인 처리 되는 것이 아님. 인증 요청은 보낼수도 안보낼 수도 있음.
        // 우리의 경우 이미 구글 인증을 통해서 파이어 베이스 Authentication에 등록된 상태일 것임. 구글인증->전화번호 인증
        // 따라서 휴대전화 인증 성공해도 파이어 베이스에 인증 요청 보내지 않을 예정. 우리는 인증 성공하면 전화번호만 DB에 저장.
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("로그인 과정 중 전화번호 인증", "인증이 자동으로 성공함!")
            finish()
        }
        // 이 함수는 사용자 인증 실패시 실행되는 콜백함수임
        override fun onVerificationFailed(e: FirebaseException) {
            Log.d("로그인 과정 중 전화번호 인증", "인증 실패!")
            Log.d("로그인 과정 중 전화번호 인증", e.toString())
            auth.currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 사용자 삭제 성공
                        Log.d("로그인 과정 중 전화번호 인증", "사용자가 전화번호 인증에 실패하여 인증 제거함")
                    } else {
                        // 사용자 삭제 실패
                        Log.d("로그인 과정 중 전화번호 인증", "사용자가 전화번호 인증에 실패하여 인증 제거하려 했으나 실패함")
                    }
                }
            if (e is FirebaseAuthInvalidCredentialsException) {
                // 전화번호가 유효하지 않은 경우의 처리
                Log.d("로그인 과정 중 전화번호 인증", "전화번호가 유효하지 않습니다.")
            } else if (e is FirebaseTooManyRequestsException) {
                // 너무 많은 요청이 발생한 경우의 처리
                Log.d("로그인 과정 중 전화번호 인증", "너무 많은 요청이 발생했습니다.")
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // Recaptcha 사용 시 관련된 액티비티가 누락된 경우의 처리
                Log.d("로그인 과정 중 전화번호 인증", "Recaptcha 사용 시 액티비티가 누락되었습니다.")
            }
        }
        // 이 메서드는 인증 코드가 성공적으로 전송되었을 때 실행되는 메서드임.
        // 인증번호와 토큰을 받게 됨.
        // 동일한 기기에서 문자 수신과 인증을 진행시, 로그인이 자동 진행되지만,
        // 문자 수신과 로그인 기기가 다를 경우 유저는 인증 코드를 직접 앱에 입력해야 함.
        // 이때 인증 코드와 수신된 코드 비교를 여기서 진행 후 로그인 처리하면 됨.
        // 우리의 경우 인증 코드 동일하면 DB저장
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            Log.d("로그인 과정 중 전화번호 인증", "인증번호가 발송되었습니다!")
            Log.d("로그인 과정 중 전화번호 인증", verificationId)
            Log.d("로그인 과정 중 전화번호 인증", token.toString())
            messageAuthCode = verificationId
        }
    }

}