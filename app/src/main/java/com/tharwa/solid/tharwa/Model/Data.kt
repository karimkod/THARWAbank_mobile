package com.tharwa.solid.tharwa.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by LE on 03/03/2018.
 */

data class User
(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("channel") var channel: Int
   // @SerializedName("nonce") var nonce: String
)
data class UserCode
(
        @SerializedName("email") var email: String,
        @SerializedName("password") var password: String,
        @SerializedName("nonce") var nonce: String
)

data class TokenResponse
(
    @SerializedName("user_id") val useId: String,
    @SerializedName("user_type") val userType: String,
    @SerializedName("access_token") val token: String,
    @SerializedName("expires_in") val expiresIn: String

)

data class responseUser
(
 @SerializedName("message") var message: String
)