package com.algovin373.project.moviecatalog.idleresource

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val resource = "GLOBAL"
    @JvmField val espressoTestIdlingResource = CountingIdlingResource(resource)

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        if (!espressoTestIdlingResource.isIdleNow) {
            espressoTestIdlingResource.decrement()
        }
    }

    fun getEspressoIdlingResource(): IdlingResource {
        return espressoTestIdlingResource
    }
}