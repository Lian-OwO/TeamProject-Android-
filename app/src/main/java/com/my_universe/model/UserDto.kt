package com.my_universe.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class UserDto(val username: String? = null, val email: String? = null, val password: String? = null, val phoneNum: String?) : Serializable {
    constructor() : this("", "", "", "")
}