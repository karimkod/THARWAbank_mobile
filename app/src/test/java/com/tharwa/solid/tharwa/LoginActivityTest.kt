package com.tharwa.solid.tharwa
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.tharwa.solid.tharwa.Controller.LoginActivity

import com.tharwa.solid.tharwa.Model.User
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.enumration.CodeStatus
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
 * Created by LE on 10/03/2018.
 */
@RunWith(MockitoJUnitRunner::class) // to run mokito API
class LoginActivityTest
{
   @Rule
    @JvmField //Instructs the Kotlin compiler not to generate getters/setters for this property and expose it as a field.
    var rule = InstantTaskExecutorRule()

    // Test rule for making the RxJava to run synchronously in unit test
   companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
    /*lateinit means you will be initializing the value for this variable before accessing it
    instead of Lazy.
     Lazy: It’s same as lazy initialization. Your variable will not be initialized unless you use that variable in your code
     */
   @Mock //to mock the user API service
    lateinit var userApiService: UserApiService

    @Mock //to mocke the Response
    lateinit var observer: Observer<User>


    lateinit var  lodingActivity: LoginActivity

    //Determine waht to do before test
    @Before
    fun setUp() {
        // initialize the   lodingActivity
        lodingActivity = LoginActivity()
    }
    //Now the test

    @Test
    fun TestLogin() {
      //Test with right email and password
        val user = User("ez_taklit@esi.dz","password",0)

        // make the UserApiService api to return mock data
       Mockito.`when`(userApiService.login(user))
               .thenReturn(Observable.just(Response.success(user)))

        // assert that the code matches
        assert(lodingActivity.code==CodeStatus.succ200.status )

    }
}