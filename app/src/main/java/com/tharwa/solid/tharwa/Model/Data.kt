package com.tharwa.solid.tharwa.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by LE on 03/03/2018.
 */

data class User
(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("channel") val channel: Int
   // @SerializedName("nonce") val nonce: String
)
data class UserCode
(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("nonce") val nonce: String
)

data class responseUser
(
 @SerializedName("message") val message: String
)