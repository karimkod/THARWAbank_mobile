package com.tharwa.solid.tharwa.Model

import com.google.gson.annotations.SerializedName
import org.w3c.dom.Text
import java.io.File
import java.lang.reflect.Constructor
import java.util.*

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
    @SerializedName("accounts_types") val accountTypes:Array<Int>,

    @SerializedName("current_account") val currentAccount:Account,
    @SerializedName("user_id") val userId: String,
    @SerializedName("user_type") val user_type: String,
    @SerializedName("access_token") val token: String,
    @SerializedName("expires_in") val expiresIn: Int

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TokenResponse

        if (name != other.name) return false
        if (photoPath != other.photoPath) return false
        if (type != other.type) return false
        if (!Arrays.equals(accountTypes, other.accountTypes)) return false
        if (currentAccount != other.currentAccount) return false
        if (userId != other.userId) return false
        if (token != other.token) return false
        if (expiresIn != other.expiresIn) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + photoPath.hashCode()
        result = 31 * result + type
        result = 31 * result + Arrays.hashCode(accountTypes)
        result = 31 * result + currentAccount.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + token.hashCode()
        result = 31 * result + expiresIn
        return result
    }
}

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

data class MyTransferCuToSav
(
    @SerializedName("type") val type:Int,
    @SerializedName("amount") val amount:Int,
    @SerializedName("from") val from:Int,
    @SerializedName("to") val to:Int

)
data class Response
(
        @SerializedName("message") val message:String
)

data class VirmentInterne
(
@SerializedName("num_acc_sender") val num_acc_sender:Int ,
@SerializedName("type_acc_sender") val type_acc_sender: Int,
@SerializedName("code_curr_sender") val code_curr_sender: String,
@SerializedName("num_acc_receiver") val num_acc_receiver:Int,
@SerializedName("type_acc_receiver") val type_acc_receiver:Int,
@SerializedName("code_curr_receiver")val code_curr_receiver:String ,
@SerializedName("montant_virement")val montant_virement:Int,
@SerializedName("type") val type:Int
)

data class ResponseVirme
(
        @SerializedName("message") val message:String,
        @SerializedName("balance") val balance:String

)

data class VirToMe
(
        @SerializedName("type_acc_sender") val type_acc_sender:Int,
        @SerializedName("type_acc_receiver") val type_acc_receiver:Int,
        @SerializedName("montant_virement") val montant_virement:Double,
        @SerializedName("type") val type:Int
)




data class VirementTharwa
(
        @SerializedName("num_acc_receiver") val numAccount:Int,
        @SerializedName("montant_virement") val montant:Float,
        @SerializedName("type")val typeVirement:Int = 0
)

data class DestinationAccoutInfo
(
        @SerializedName("name") val name:String,
        @SerializedName("commune") val commune:String,
        @SerializedName("wilaya") val wilaya: String
)
