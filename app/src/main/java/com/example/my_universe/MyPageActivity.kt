package com.example.my_universe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.my_universe.MyApplication.Companion.auth
import com.example.my_universe.databinding.ActivityMyPageBinding
import com.example.my_universe.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException

class MyPageActivity : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = ActivityMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // 현재 사용자 정보 가져오기
        val currentUser: FirebaseUser? = auth.currentUser

        if (currentUser != null) {
            // 사용자가 로그인한 상태
            // 이메일 정보 가져오기
            val email: String? = currentUser.email

            // TextView에 이메일 정보 설정
            binding.idTextView.text = "이메일 : $email"

            // 사용자의 UID 가져오기
            val uid: String = currentUser.uid

            // Firebase Realtime Database에서 사용자 정보 조회
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 데이터베이스에서 사용자 정보 가져오기
                    val user = dataSnapshot.getValue(User::class.java)

                    // 가져온 사용자 정보를 TextView에 설정
                    if (user != null) {
                        binding.PhoneNumberView.text = "전화번호 : ${user.phoneNum}"
                        binding.NameView.text = "이름 : ${user.name}"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 실패 시 처리
                    Toast.makeText(requireContext(), "데이터를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            })

            // 프로필 이미지 업로드 버튼 클릭 이벤트
            binding.profileUpdateBtn.setOnClickListener {
                chooseImage()
            }
        } else {
            Toast.makeText(requireContext(), "로그인 상태가 아님", Toast.LENGTH_SHORT).show()
        }
    }


    // 이미지 상수
    private val PICK_IMAGE_REQUEST = 1

    // 파일 패스
    private var filePath: Uri? = null
    private val storageRef: StorageReference = FirebaseStorage.getInstance().reference


    // 갤러리에서 이미지를 선택하고 결과를 처리 (보여주는 부분)
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                filePath = uri
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        filePath
                    )
                    // ImageView에 선택한 이미지 설정
                    binding.resultUserImage.setImageBitmap(bitmap)
                    // 이미지 업로드 실행
                    uploadImage()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    private fun chooseImage() {
        getContent.launch("image/*")
    }




    private fun uploadImage() {
        if (filePath != null) {
            // Firebase Storage에 이미지 업로드
            val ref = storageRef.child("images/${auth.currentUser?.uid}")
            ref.putFile(filePath!!)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                    // 업로드 성공 시 처리
                    Log.d("업로드", "업로드 성공")

                    // 업로드된 이미지의 다운로드 URL 가져오기
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()

                        // Firebase에 사용자 프로필 사진 업데이트
                        updateUserProfileImage(imageUrl)
                    }.addOnFailureListener { e ->
                        // 다운로드 URL 가져오기 실패 시 처리
                        Log.e("업로드", "다운로드 URL 가져오기 실패: ${e.message}", e)
                    }
                }
                .addOnFailureListener { e ->
                    // 업로드 실패 시 처리
                    Log.e("업로드", "업로드 실패: ${e.message}", e)
                }
        }
    }


    // 프로필 사진 업로드하는 부분
    private fun updateUserProfileImage(imageUrl: String) {
        val user = auth.currentUser
        // Firebase에 사용자 프로필 사진 업데이트
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(imageUrl))
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("프로필 업로드", "프로필 사진 업로드 완료.")
                } else {
                    Log.e("프로필 업로드", "프로필 사진 업로드 실패: ${task.exception?.message}", task.exception)
                }
            }
    }
}
