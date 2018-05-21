package com.tharwa.solid.tharwa.Remote
import com.tharwa.solid.tharwa.Model.*
import io.reactivex.Observable
import okhttp3.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import okhttp3.RequestBody
import okhttp3.MultipartBody
import okhttp3.ResponseBody

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor





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

    @Multipart
    @Headers("Accept:multipart/form-data")
    @POST("user/photo")
    fun postImage(@Part image: okhttp3.MultipartBody.Part
            ,@Part("id_user") userId:okhttp3.RequestBody)
            :retrofit2.Call<okhttp3.ResponseBody>


    @PUT("/my_exchange")
    fun myTransferCuSav(@Header("Authorization")token:String,@Body mytransfer:MyTransferCuToSav): Observable<Response<com.tharwa.solid.tharwa.Model.Response>>

    //virement interne
    @PUT("/virements_internes")
    fun virementInterne(@Header("Authorization")token:String,
                        @Body virement:VirmentInterne): Observable<Response<com.tharwa.solid.tharwa.Model.Response>>


    @Headers("Accept:application/json")
    @POST("virements_internes_thw")
    fun virementToTharwa(@Header("Authorization")token:String,@Body virementTharwa: VirementTharwa):Observable<Response<ResponseBody>>


    @GET("accounts/name/{id}")
    fun getDestinationAccountInfo(@Header("Authorization")token:String, @Path("id")id:Int):Observable<Response<DestinationAccoutInfo>>


    @GET("accounts/type/{type}")
    fun getAccountInfo(@Header("Authorization")token:String,@Path("type")type:Int):Observable<Response<AccountInfo>>


    @GET("/currency")
    fun getExchangeRate( @Header("Authorization")token:String ): Observable<Response<com.tharwa.solid.tharwa.Model.ExchangeRateResponse>>


    @Multipart
    @Headers("Accept:multipart/form-data")
    @POST("virements_internes_thw")
    fun VirementToTharwa(@Header("Authorization")token:String,@Part image: okhttp3.MultipartBody.Part ,@Part("num_acc_receiver") destination:okhttp3.RequestBody,@Part("montant_virement") montant:okhttp3.RequestBody,@Part("type") type:okhttp3.RequestBody):retrofit2.Call<okhttp3.ResponseBody>


    @Headers("Accept:application/json")
    @GET("account/virements/{id}")
    fun getHistory(@Header("Authorization")token:String, @Path("id")id:Int, @Query("page") page:Int): Observable<Response<HistoryResponse>>



    //create the service
    companion object {

        //val URL="https://serene-retreat-29274.herokuapp.com/"

        val URL="http://192.168.43.5/"

        fun create(): UserApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(URL)
                    .build()
            return retrofit.create(UserApiService::class.java)
        }

        fun createServiceForImage():UserApiService
        {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit.Builder().baseUrl(URL).client(client).build().create(UserApiService::class.java)
        }
    }
}