package com.tharwa.solid.tharwa.Remote
import com.tharwa.solid.tharwa.Model.TokenResponse
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
    fun loginCode(@Body usercd: UserCode): Observable<Response<TokenResponse>>




    companion object {
        //val bsUrl = "https://api.github.com/"
       // val bsUrl="http://localhost:3000/"
<<<<<<< HEAD
        val bsUrl="https://serene-retreat-29274.herokuapp.com//"
=======
        val bsUrl="https://serene-retreat-29274.herokuapp.com/"
>>>>>>> f6054d6c6c94b1ae1631fed99f21bc9d4f9acf14
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