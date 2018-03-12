package com.tharwa.solid.tharwa.Remote
import com.tharwa.solid.tharwa.Model.User
import com.tharwa.solid.tharwa.Model.UserCode
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


    @Headers("Accept:application/json")
    @POST("login")
    fun login(@Body user: User): Observable<Response<User>>

    @Headers("Accept:application/json")
    @POST("login/code")
    fun loginCode(@Body usercd: UserCode): Observable<Response<UserCode>>


    companion object {
        //val bsUrl = "https://api.github.com/"
       // val bsUrl="http://localhost:3000/"
        val bsUrl="https://serene-retreat-29274.herokuapp.com//"
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