package com.example.my_universe.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class User(val name: String? = null, val email: String? = null,val password: String? = null,val phoneNum: String?) {
}