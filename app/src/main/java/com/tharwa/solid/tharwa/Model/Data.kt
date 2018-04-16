package com.tharwa.solid.tharwa.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.w3c.dom.Text
import java.io.File

/**
 * Created by LE on 03/03/2018.
 */

//TO be send in the first request of auth
data class User
(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("channel") val channel: Int

)
//To be send in the second request of auth
data class UserCode
(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("nonce") val nonce: String
)

//Receive the token response
data class TokenResponse
(
    @SerializedName("name") val name:String,
    @SerializedName("photo") val photoPath:String,
    @SerializedName("type") val type:Int,
    @SerializedName("account_types") val accountTypes:Array<Int>,
    @SerializedName("current_account") val currentAccount:currentAccountDetail,
    @SerializedName("user_id") val userId: String,
    @SerializedName("access_token") val token: String,
    @SerializedName("expires_in") val expiresIn: String

)

data class currentAccountDetail(
    @SerializedName("account_code") val accountCode:String,
    @SerializedName("balance") val balance:Double

    )
//The request to send to the restful API
data class UserCreate
(

    @SerializedName("email") val email:String,
    @SerializedName("password") val password:String,
    @SerializedName("phone_number") val Tel:String,
    @SerializedName("name") val name:String,
    @SerializedName("address") val address:String,
    @SerializedName("function") val function:String,
    @SerializedName("wilaya") val wilaya:String,
    @SerializedName("commune") val commune:String,
    @SerializedName("type") val type:Int
)
// The response of the first request
data class CreateResponse
(
        @SerializedName("message") val message:String,
        @SerializedName("user_id") val user_id:Int
)


data class Avatar
(
        @SerializedName("id_user") val id_user:Text,
        @SerializedName("photo") val photo:File,
        @SerializedName("_method") val _method:String="PUT"
)
data class AvatarResponse
(
        @SerializedName("message") val message:String
)