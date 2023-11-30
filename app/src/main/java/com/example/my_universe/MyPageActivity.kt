package com.example.my_universe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.my_universe.databinding.ActivityMyPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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


            }else {

            Toast.makeText(requireContext(), "로그인 상태가 아님", Toast.LENGTH_SHORT).show()
        }
        }





    }
