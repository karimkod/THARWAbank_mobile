package com.tharwa.solid.tharwa.Remote
import com.tharwa.solid.tharwa.Model.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by LE on 03/03/2018.
 */
interface UserApiService {

    //Send the first request of mail,password,choice
    @Headers("Accept:application/json")
    @POST("login")
    fun login(@Body user: User): Observable<Response<User>>

    //send the sencond request of authentification mail,password,nonce
    @Headers("Accept:application/json")
    @POST("login/code")
    fun loginCode(@Body usercd: UserCode): Observable<Response<TokenResponse>>

    // Send the information to create a customer
    @Headers("Accept:application/json")
    @POST("customers")
    fun createCustomer(@Body usercr: UserCreate): Observable<Response<CreateResponse>>

    // Send the photo of the customer

    @Headers("Accept:multipart/form-data")
    @POST("/update_photo")
    fun sendAvatar(@Body userAvatar: Avatar): Observable<Response<AvatarResponse>>


    //create the service
    companion object {
        val bsUrl="https://serene-retreat-29274.herokuapp.com/"
        fun create(): UserApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(bsUrl)
                    .build()
            return retrofit.create(UserApiService::class.java)
        }
    }
}