package iqro.mobile.retrofitexample.user

import com.google.gson.annotations.SerializedName

/**
 *Created by Zohidjon Akbarov
 */
data class UserResponse(
    @SerializedName("data")
    val user: User
)
