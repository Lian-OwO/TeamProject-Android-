package com.example.my_universe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
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
            val email: String? = currentUser.email
            binding.idTextView.text = "이메일 : $email"

            val uid: String = currentUser.uid
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)

                    if (user != null) {
                        binding.PhoneNumberView.text = "전화번호 : ${user.phoneNum}"
                        binding.NameView.text = "이름 : ${user.name}"
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
            // 사용자가 로그인하지 않은 상태이면 편집 버튼을 숨김
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

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("프로필 업로드", "프로필 사진 업데이트 완료.")
                } else {
                    Log.e("프로필 업로드", "프로필 사진 업데이트 실패: ${task.exception?.message}", task.exception)
                }
            }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageRef.child("profile/${auth.currentUser?.uid}")
            ref.putFile(filePath!!)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                    Log.d("프로필", "업데이트 성공")

                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        loadImageIntoImageView(imageUrl)
                        updateUserProfileImage(imageUrl)
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
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference
        val imageRef: StorageReference = storageRef.child("profile/${auth.currentUser?.uid}")

        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                loadImageIntoImageView(uri.toString())
            }
            .addOnFailureListener { e ->
                Log.e("프로필", "이미지 불러오기 실패: ${e.message}", e)
            }
    }

    private fun loadImageIntoImageView(imageUrl: String) {
        binding?.cardView?.findViewById<ImageView>(R.id.resultUserImage)?.let { imageView: ImageView ->
            Glide.with(requireContext())
                .load(imageUrl)
                .into(imageView)
        } ?: run {
            Log.e("프로필", "CardView or ImageView is null.")
        }
    }
}
