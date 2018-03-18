package com.tharwa.solid.tharwa

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.tharwa.solid.tharwa.View.CodeIntroductionActivity
import com.tharwa.solid.tharwa.Model.TokenResponse
import com.tharwa.solid.tharwa.Model.UserCode
import com.tharwa.solid.tharwa.Remote.UserApiService


import io.reactivex.Observable
import io.reactivex.Observer
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
 * Created by LE on 12/03/2018.
 */
@RunWith(MockitoJUnitRunner::class) // to run mokito API
class ToekenTest
{
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
    @Mock //to mock the user API service
    lateinit var userApiService: UserApiService

    @Mock //to mocke the Response
    lateinit var observer: Observer<UserCode>


    lateinit var  codeIntroductionActivity: CodeIntroductionActivity

    //Determine waht to do before test
    @Before
    fun setUp() {
        // initialize the   codeIntroductionActivity
        codeIntroductionActivity = CodeIntroductionActivity()
    }
    //Now the test

    @Test
    fun TestToken() {
        //Test with right email and password
        val usercode = UserCode("ez_taklit@esi.dz","password","2553")

        // make the UserApiService api to return mock data
        val tokenresponse:TokenResponse?=null
        Mockito.`when`(userApiService!!.loginCode(usercode))
                .thenReturn(Observable.just(Response.success(tokenresponse)))
        assert(codeIntroductionActivity.token!="")
    }
}