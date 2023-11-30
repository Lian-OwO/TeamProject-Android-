//package com.example.my_universe
//
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import com.example.my_universe.databinding.ActivityMyPageBinding
//import com.example.my_universe.model.MyPageItem
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class MyPageActivity : AppCompatActivity() {
//    private lateinit var database: FirebaseDatabase
//    private lateinit var auth: FirebaseAuth
//    private lateinit var binding: ActivityMyPageBinding
//
//    // ValueEventListener 참조 변수 추가
//    private lateinit var valueEventListener: ValueEventListener
//    private lateinit var databaseReference: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("scb", "Log 호출됨")
//        super.onCreate(savedInstanceState)
//        binding = ActivityMyPageBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        database = FirebaseDatabase.getInstance()
//        auth = FirebaseAuth.getInstance()
//
//        // 현재 사용자의 UID 가져오기
//        val currentUser: FirebaseUser? = auth.currentUser
//        Log.d("scb", "Log 호출됨2")
//
//        if (currentUser != null) {
//            Log.d("scb", "Log 호출됨3")
//            // 사용자가 로그인한 상태
//            val uid: String = currentUser.uid
//
//            // DatabaseReference를 사용하여 데이터베이스에서 특정 UID의 데이터를 조회
//            databaseReference = database.reference.child("users").child(uid)
//            Log.d("scb", "Log 호출됨4")
//            // ValueEventListener 초기화
//            valueEventListener = object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    Log.d("scb", "onDataChange 호출")
//                    // 데이터를 가져와서 처리
//                    if (dataSnapshot.exists()) {
//                        Log.d("scb", "DataSnapshot이 존재함")
//                        // 데이터 스냅샷에서 MyPageItem으로 변환
//                        val myPageItem: MyPageItem? =
//                            dataSnapshot.getValue(MyPageItem::class.java)
//
//                        // myPageItem 객체에 데이터가 들어있습니다. 이를 원하는 방식으로 처리하세요.
//                        if (myPageItem != null) {
//                            // 이메일, 이름, 전화번호를 TextView에 설정
//                            binding.idTextView.text = "아이디 : ${myPageItem.uid}"
//                            binding.EmailView.text = "이메일: ${myPageItem.email}"
//                            binding.NameView.text = "이름: ${myPageItem.name}"
//                            binding.PhoneNumberView.text = "전화번호: ${myPageItem.phoneNumber}"
//
//                            Log.d("scb", "이메일: ${myPageItem.email}, 이름: ${myPageItem.name}, 전화번호: ${myPageItem.phoneNumber}")
//                        }
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // 에러 처리
//                    Log.d("scb", "Error: ${databaseError.message}")
//                }
//            }
//
//            // ValueEventListener를 사용하여 데이터 변경 감지
//            Log.d("scb", "databaseReference 생성 후")
//            databaseReference.addValueEventListener(valueEventListener)
//        } else {
//            // 사용자가 로그인하지 않은 상태
//            // 로그인 화면으로 이동하거나 다른 처리를 수행할 수 있습니다.
//            Log.d("scb", "로그인 상태 아님")
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // 리스너 제거
//        if (::valueEventListener.isInitialized) {
//            databaseReference.removeEventListener(valueEventListener)
//        }
//    }
//}



package com.example.my_universe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.my_universe.MainFragment.MapFragment
import com.example.my_universe.MainFragment.Test1Fragment
import com.example.my_universe.databinding.ActivityMyPageBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MyPageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // 현재 사용자 정보 가져오기
        val currentUser: FirebaseUser? = auth.currentUser

        if (currentUser != null) {
            // 사용자가 로그인한 상태
            // 이메일 정보 가져오기
            val email: String? = currentUser.email

            // TextView에 이메일 정보 설정
            binding.idTextView.text = "이메일 : $email"
            // 로그아웃 버튼 클릭 이벤트 처리
            binding.logoutButton.setOnClickListener {
                // Firebase Authentication에서 로그아웃
                auth.signOut()

                // 로그아웃 후 로그인 화면으로 이동 또는 다른 처리 수행
                // 예시: 로그인 화면으로 이동
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                }
            // 로그인 버튼 숨기기
            binding.loginButton.visibility = View.GONE
        } else {
            // 사용자가 로그인하지 않은 상태
            // 로그인 화면으로 이동하거나 다른 처리를 수행할 수 있습니다.
            binding.loginButton.visibility = View.VISIBLE
            binding.loginButton.setOnClickListener {
                // 로그인 화면으로 이동 또는 다른 처리 수행
                startActivity(Intent(this, LoginFormActivity::class.java))
                finish()
                Log.d("scb", "로그인 상태 아님")
                Toast.makeText(this, "로그인 상태가 아님", Toast.LENGTH_SHORT).show()
            }
            // 로그아웃 버튼 숨기기
            binding.logoutButton.visibility = View.GONE
        }

        val tabLayout = binding.tabs
        setContentView(binding.root)



        tabLayout.addOnTabSelectedListener( object: TabLayout.OnTabSelectedListener {
            //익명 클래스 정의하고, 해당 이벤트 리스너 구현하면, 의무적으로,
            // 재정의 해야하는 함수3개 있음.
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text) {
                    "home" -> transaction.replace(R.id.tabContent, Test1Fragment())
                    "wish" -> transaction.replace(R.id.tabContent, Test1Fragment())
                    "map" -> transaction.replace(R.id.tabContent, MapFragment())
                    "myPage" -> {
                        //MyPageActivity로 이동
                        startActivity(Intent(this@MyPageActivity, MyPageActivity::class.java))
                    }
                }
                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MyPageActivity,"onTabUnselected", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MyPageActivity,"onTabReselected", Toast.LENGTH_SHORT).show()
            }

        })
    }
}

