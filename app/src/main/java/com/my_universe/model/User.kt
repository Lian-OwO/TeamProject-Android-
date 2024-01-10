package com.my_universe.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class User(val username: String? = null, val email: String? = null,val password: String? = null,val phoneNum: String?) : Serializable {

    // 빈 생성자 추가
    constructor() : this("", "", "", "")
}