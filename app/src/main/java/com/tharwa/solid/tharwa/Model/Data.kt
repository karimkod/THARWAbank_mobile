package com.tharwa.solid.tharwa.Model

import android.os.Parcelable
import android.support.annotation.Nullable
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
    @SerializedName("access_token") val token: String?,
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("expires_in") val expiresIn: Int?


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

data class RefreshResponse(
        @SerializedName("access_token") val token:String,
        @SerializedName("refresh_token") val refreshToken: String,
        @SerializedName("expires_in") val expiresIn: Int
)

data class RefreshBody
(
    @SerializedName("refresh_token") val refreshToken:String
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


data class AccountInfo(
        @SerializedName("id") val id:Int,
        @SerializedName("currency_code") val currency:String,
        @SerializedName("type") val type:Int,
        @SerializedName("balance") val balance:Double,
        @SerializedName("status") val status:Int

)

data class TransactionData(
        @SerializedName("id") val id:Int,
        @SerializedName("num_acc_sender") val num_acc_sender:Int,
        @SerializedName("code_bnk_sender") val code_bnk_sender:String,
        @SerializedName("code_curr_sender") val code_curr_sender:String,
        @SerializedName("num_acc_receiver") val num_acc_receiver:Int,
        @SerializedName("code_bnk_receiver") val code_bnk_receiver:String,
        @SerializedName("code_curr_receiver") val code_curr_receiver:String,
        @SerializedName("montant_virement") val montant_virement:String,
        @SerializedName("status") val status:Int,
        @SerializedName("type") val type:Int,
        @SerializedName("id_commission") val id_commission:String,
        @SerializedName("montant_commission") val montant_commission:String,
        @SerializedName("created_at") val created_at:String
)


data class HistoryResponse(

        @SerializedName("current_page") val current_page:Int,
        @SerializedName("data") val data:Array<TransactionData>,
        @SerializedName("from") val from:Int,
        @SerializedName("next_page_url") val next_page_url:String,
        @SerializedName("path") val path:String,
        @SerializedName("per_page") val per_page:Int,
        @Nullable @SerializedName("prev_page_url") val prev_page_url:String,
        @SerializedName("to") val to:Int

)