package com.tharwa.solid.tharwa.ScreenTests

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.LinearLayoutCompat
import android.view.View.VISIBLE
import com.tharwa.solid.tharwa.View.VirementTharwaActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.tharwa.solid.tharwa.R
import junit.framework.Assert.assertEquals
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf


@RunWith(AndroidJUnit4::class) @LargeTest
class VirementVersTharwaScreenTest
{

    private val THARWA_ACCOUNT = "000001"
    private val balance_big = "250000"
    private val balance_small = "2500"

    @Rule
    @JvmField var mActivityRule = ActivityTestRule<VirementTharwaActivity>(
            VirementTharwaActivity::class.java)

    @Test fun perfomClick()
    {
        onView(withId(R.id.virer)).perform(click())
        val codeInput:TextInputLayout = mActivityRule.activity.findViewById(R.id.numero_compte)
        assertEquals(codeInput.error,"Veuillez remplir ce champ")
    }


    @Test fun checkMotifPlaceVisibility()
    {

        onView(withId(R.id.numero_compte_editText))
                .check(matches(isDisplayed())).perform(typeText(THARWA_ACCOUNT),ViewActions.closeSoftKeyboard())
        onView(withId(R.id.montant_virement_editText))
                .check(matches(isDisplayed())).perform(typeText(balance_big), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.motif)).check(matches(isDisplayed()))

        val motif = mActivityRule.activity.findViewById<LinearLayoutCompat>(R.id.motif)
        assertEquals(motif.visibility,VISIBLE)
    }



}