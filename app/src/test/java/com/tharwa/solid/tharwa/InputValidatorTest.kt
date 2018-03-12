package com.tharwa.solid.tharwa

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import android.support.design.widget.TextInputLayout
import android.widget.EditText
import android.widget.LinearLayout
import com.tharwa.solid.tharwa.Controller.InputValidator
import com.tharwa.solid.tharwa.R.id.email
import com.tharwa.solid.tharwa.enumration.InputType
import org.junit.ClassRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by LE on 12/03/2018.
 */
@RunWith(MockitoJUnitRunner::class) // to run mokito API
class InputValidatorTest
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
    @Mock //to mock the user API service
    lateinit var inputValidator: InputValidator

   @Mock

    var context: Context?=null
    var inputLayout: TextInputLayout?=TextInputLayout(context)
    var type: InputType= InputType.EMAIL

    var editText = EditText(context)

}