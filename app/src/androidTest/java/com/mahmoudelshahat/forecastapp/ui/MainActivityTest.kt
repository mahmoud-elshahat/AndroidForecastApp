package com.mahmoudelshahat.forecastapp.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mahmoudelshahat.forecastapp.R
import com.mahmoudelshahat.nytimesmp.util.EspressoIdlingResource
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {


    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun testDataIsFetchedAndLoadingGone() {
//        //check if loading text, progress bar successfully gone.
        onView(withId(R.id.loadingGroup)).check(matches(not(isDisplayed())))

        //check that main layout is  visible after network call finished
        onView(withId(R.id.weather_text_temperature)).check(matches(isDisplayed()))
        onView(withId(R.id.weather_text_location)).check(matches(isDisplayed()))
    }
}
