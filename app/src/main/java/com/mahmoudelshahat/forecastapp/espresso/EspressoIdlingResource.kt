package com.mahmoudelshahat.nytimesmp.util


import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val   RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increase() {
        countingIdlingResource.increment();
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow)
            countingIdlingResource.decrement()
    }
}