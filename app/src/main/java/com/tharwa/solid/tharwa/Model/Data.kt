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
    @SerializedName("accounts_types") val accountTypes:Array<Int>,
    @SerializedName("current_account") val currentAccount:currentAccountDetail,
    @SerializedName("user_id") val userId: String,
    @SerializedName("access_token") val token: String,
    @SerializedName("expires_in") val expiresIn: String

)

data class currentAccountDetail(
    @SerializedName("account_code") val accountCode:String,
    @SerializedName("balance") val balance:Double
    )

data class Account(
        val type:String,
        val id: Int
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


data class ExchangeRateResponse
(
        @SerializedName("Euro") val Euro:String,
        @SerializedName("USDollars") val USDollars:String ,
        @SerializedName("TurkeyLira") val TurkeyLira:String ,
        @SerializedName("CanadaDollars") val CanadaDollars:String ,
        @SerializedName("SaouditRyal") val SaouditRyal:String ,
        @SerializedName("ChinaYuan") val ChinaYuan:String,
        @SerializedName("MarocDinars") val MarocDinars:String ,
        @SerializedName("TunisiaDinars") val TunisiaDinars:String ,
        @SerializedName("PoundSterling") val PoundSterling:String ,
        @SerializedName("EmaratDirham") val EmaratDirham:String

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


data class AccountInfo(
        @SerializedName("id") val id:Int,
        @SerializedName("currency_code") val currency:String,
        @SerializedName("type") val type:Int,
        @SerializedName("balance") val balance:Double,
        @SerializedName("status") val status:Int

)