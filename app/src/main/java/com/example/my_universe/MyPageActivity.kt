package com.example.my_universe

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.my_universe.databinding.ActivityMyPageBinding
import com.example.my_universe.model.User
import com.example.my_universe.utils.SharedPreferencesManager.getEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException

class MyPageActivity : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMyPageBinding
    private var filePath: Uri? = null
    private val storageRef: StorageReference = FirebaseStorage.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = auth.currentUser

        if (currentUser != null) {
            binding.idTextView.text = "이메일 : ${getEmail(requireContext())}"


            val uid: String = currentUser.uid
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    Log.d("로그인 후 매핑 확인", user?.email.toString())
                    Log.d("로그인 후 매핑 확인", user?.phoneNum.toString())
                    if (user != null) {
                        binding.PhoneNumberView.setText("전화번호 : ${user.phoneNum}")
                        binding.NameView.setText("이름 : ${user.name}")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(requireContext(), "데이터를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            })

            binding.profileUpdateBtn.setOnClickListener {
                chooseImage()
            }
        } else {
            Toast.makeText(requireContext(), "로그인 상태가 아님", Toast.LENGTH_SHORT).show()
        }

        loadProfileImage()
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                filePath = uri
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        filePath
                    )
                    binding.resultUserImage.setImageBitmap(bitmap)
                    uploadImage()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    private fun chooseImage() {
        getContent.launch("image/*")
    }

    private fun updateUserProfileImage(imageUrl: String) {
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(imageUrl))
            .build()

        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("프로필 업로드", "프로필 사진 업데이트 완료.")
            } else {
                Log.e("프로필 업로드", "프로필 사진 업데이트 실패: ${task.exception?.message}", task.exception)
            }
        }
    }

    private fun uploadImage() {
        filePath?.let { uri ->
            val ref = storageRef.child("profile/${auth.currentUser?.uid}")
            ref.putFile(uri)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                    Log.d("프로필", "업데이트 성공")

                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                        loadImageIntoImageView(imageUrl.toString())
                        updateUserProfileImage(imageUrl.toString())
                    }.addOnFailureListener { e ->
                        Log.e("프로필", "다운로드 URL 가져오기 실패: ${e.message}", e)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("프로필", "업로드 실패: ${e.message}", e)
                }
        }
    }

    private fun loadProfileImage() {
        val imageRef: StorageReference = storageRef.child("profile/${auth.currentUser?.uid}")

        imageRef.downloadUrl
            .addOnSuccessListener { imageUrl ->
                loadImageIntoImageView(imageUrl.toString())
            }
            .addOnFailureListener { e ->
                Log.e("프로필", "이미지 불러오기 실패: ${e.message}", e)
            }
    }

    private fun loadImageIntoImageView(imageUrl: String) {
        binding.cardView.findViewById<ImageView>(R.id.resultUserImage)?.let { imageView ->
            Glide.with(requireContext())
                .load(imageUrl)
                .into(imageView)
        } ?: run {
            Log.e("프로필", "CardView or ImageView is null.")
        }
    }
}