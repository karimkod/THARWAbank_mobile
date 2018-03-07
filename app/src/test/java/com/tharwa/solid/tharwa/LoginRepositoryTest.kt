package com.tharwa.solid.tharwa

import com.tharwa.solid.tharwa.Model.User
import com.tharwa.solid.tharwa.View.LoginActivity
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.sign_up_activity.*
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

/**
 * Created by LE on 05/03/2018.
 */
class LoginRepositoryTest {
    @Test
    fun `login with correct email and password`() {
        //given
        val objectUnderTest=LoginActivity()
        val correctLogin="dbacinki"
        val correctPassword="correct"
        //when
        val user: User=User(correctLogin,correctPassword,0)
       val result= objectUnderTest.login(user)
        //then
      //  Assert.assertEquals(, 2 + 2)
     //   result..assertResult(true)
    }
}

