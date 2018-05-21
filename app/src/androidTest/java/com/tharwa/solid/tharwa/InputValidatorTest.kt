package com.tharwa.solid.tharwa

import android.content.res.Resources
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.tharwa.solid.tharwa.Bussiness.InputValidator
import com.tharwa.solid.tharwa.enumration.InputType

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InputValidatorTest
{

    private val MAIL_VALID = "ea_bournane@esi.dz"
    private val MAIL_INVALID_1 = "ea_b ournane@esi"
    private val MAIL_INVALID_2 = "ea_bournane"

    private val MONTANT_VALID ="2514.25"
    private val MONTANT_INVALID_1 = "-2514.21"
    private val MONTANT_INVALID_2 = "abcd"

    private val EMPTY_FIELD = ""

    private val CODE_VALID="1254"
    private val CODE_INVALID_1 = "a1dg"
    private val CODE_INVALID_2 = "da"

    private val WILAYA_UNSELECTED = "Wilaya*"
    private val WILAYA_ALGER = "Alger"

    private val ACCOUNTNUMBER_VALID = "000001"
    private val ACCOUNTNUMBER_INVALID = "00a"

    private lateinit var resources: Resources


    @Before
    fun setUpContext()
    {

        resources = InstrumentationRegistry.getTargetContext().resources

    }

    @Test fun test_mail()
    {
        assertEquals(InputValidator.checkInput(MAIL_VALID,resources, InputType.EMAIL,true), null)
        assertEquals(InputValidator.checkInput(MAIL_INVALID_1,resources, InputType.EMAIL,true),resources.getString(R.string.emailInvalide))
        assertEquals(InputValidator.checkInput(MAIL_INVALID_2,resources, InputType.EMAIL,true), resources.getString(R.string.emailInvalide))
    }

    @Test fun test_empty()
    {
        assertEquals(InputValidator.checkInput(EMPTY_FIELD,resources, InputType.NAME,true),resources.getString(R.string.champvide))
    }

    @Test fun test_montant()
    {
        assertEquals(InputValidator.checkInput(MONTANT_VALID,resources, InputType.MONTANT,true),null)
        assertEquals(InputValidator.checkInput(MONTANT_INVALID_1,resources, InputType.MONTANT,true),resources.getString(R.string.montantNegatif))
        assertEquals(InputValidator.checkInput(MONTANT_INVALID_2,resources, InputType.MONTANT,true),resources.getString(R.string.montantInvalide))
    }

    @Test fun test_code()
    {
        assertEquals(InputValidator.checkInput(CODE_VALID,resources, InputType.CODE,true),null)
        assertEquals(InputValidator.checkInput(CODE_INVALID_1,resources, InputType.CODE,true),resources.getString(R.string.codeInvalide))
        assertEquals(InputValidator.checkInput(CODE_INVALID_2,resources, InputType.CODE,true),resources.getString(R.string.codeInvalide))
    }

    @Test fun test_wilaya()
    {
        assertEquals(InputValidator.checkInput(WILAYA_ALGER,resources, InputType.WILAYA,true),null)
        assertEquals(InputValidator.checkInput(WILAYA_UNSELECTED,resources, InputType.WILAYA,true),resources.getString(R.string.wilayaInvalide))
    }

    @Test fun test_accountNumber()
    {

        assertEquals(InputValidator.checkInput(ACCOUNTNUMBER_VALID,resources, InputType.ACCOUNTNUMBER,true),null)
        assertEquals(InputValidator.checkInput(ACCOUNTNUMBER_INVALID,resources, InputType.ACCOUNTNUMBER,true),resources.getString(R.string.accountNumberInvalide))

    }

}
