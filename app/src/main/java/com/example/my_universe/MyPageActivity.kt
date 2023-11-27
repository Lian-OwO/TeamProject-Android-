package com.example.my_universe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.my_universe.databinding.ActivityMyPageBinding
import com.example.my_universe.model.MyPageItem
import com.example.my_universe.recycler.MyPageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyPageActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        // 현재 사용자의 UID 가져오기
        val currentUser: FirebaseUser? = auth.currentUser

        if (currentUser != null) {
            // 사용자가 로그인한 상태
            val uid: String = currentUser.uid

            // DatabaseReference를 사용하여 데이터베이스에서 특정 UID의 데이터를 조회
            val databaseReference: DatabaseReference =
                database.reference.child("users").child(uid)

            // ValueEventListener를 사용하여 데이터 변경 감지
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 데이터를 가져와서 처리
                    if (dataSnapshot.exists()) {
                        // 데이터 스냅샷에서 MyPageItem으로 변환
                        val myPageItem: MyPageItem? =
                            dataSnapshot.getValue(MyPageItem::class.java)

                        // myPageItem 객체에 데이터가 들어있습니다. 이를 원하는 방식으로 처리하세요.
                        if (myPageItem != null) {
                            Log.d("scb", "User ID: ${myPageItem.uid}")
                            Log.d("scb", "Email: ${myPageItem.email}")
                            Log.d("scb", "Name: ${myPageItem.name}")
                            Log.d("scb", "Phone Number: ${myPageItem.phoneNumber}")
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 에러 처리
                    Log.d("scb", "Error: ${databaseError.message}")
                }
            })
        } else {
            // 사용자가 로그인하지 않은 상태
            // 로그인 화면으로 이동하거나 다른 처리를 수행할 수 있습니다.
            Log.d("scb", "User is not logged in.")
        }
    }
}
