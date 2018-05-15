package com.tharwa.solid.tharwa.Model

import com.google.gson.annotations.SerializedName


data class Account(
        @SerializedName("id") val id:Int,
        @SerializedName("currency_code") val currency:String,
        @SerializedName("type") val type:Int,
        @SerializedName("balance") val balance:Double,
        @SerializedName("status") val status:Int
)