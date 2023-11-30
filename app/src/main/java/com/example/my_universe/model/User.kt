package com.example.my_universe.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class User(val name: String? = null, val email: String? = null,val password: String? = null,val phoneNum: String?) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
    //   val email = binding.joinEmail.text
    //        val password = binding.joinPassword.text
    //        val passwordCheck = binding.joinPwck.text
    //        val name = binding.joinName
}