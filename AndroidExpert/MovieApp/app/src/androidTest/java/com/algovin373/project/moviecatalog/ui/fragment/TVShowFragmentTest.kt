package com.algovin373.project.moviecatalog.ui.fragment

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.algokelvin373.project.moviecatalog.testing.AlternativeFragmentActivity
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.idleresource.EspressoIdlingResource
import com.algovin373.project.moviecatalog.utils.RVItemCountAssertion
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*
Scenario Instrumentation Testing :
1. Testing to can display data TVShow in RecyclerView ( complete )
2. Testing to check that the Data TVShow is 10 in RecyclerView ( complete )
 */

class TVShowFragmentTest {
    @Rule
    @JvmField
    var fragmentTVShowRule: ActivityTestRule<AlternativeFragmentActivity> = ActivityTestRule(AlternativeFragmentActivity::class.java)
    private val tvShowFragment = TVShowFragment()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        fragmentTVShowRule.activity.setFragment(tvShowFragment)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadCourses() {
        /** Data TVShow Airing Today in Banner Poster TVShow **/
        Espresso.onView(withId(R.id.viewpager_tvshow_banner)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.worm_dots_indicator_tvshow)).check(matches(isDisplayed()))

        /** Data TVShow Airing Today **/
        Espresso.onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed())) // Point No. 1
        Espresso.onView(withId(R.id.rv_tvShow)).check(RVItemCountAssertion(20)) // Point No. 2

        /** Data TVShow On The Air **/
        Espresso.onView(allOf(withText(R.string.tvShow_on_the_air),isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed())) // Point No. 1
        Espresso.onView(withId(R.id.rv_tvShow)).check(RVItemCountAssertion(20)) // Point No. 2

        /** Data TVShow Popular **/
        Espresso.onView(allOf(withText(R.string.tvShow_popular), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed())) // Point No. 1
        Espresso.onView(withId(R.id.rv_tvShow)).check(RVItemCountAssertion(20)) // Point No. 2

        /** Data TvShow Top Related **/
        Espresso.onView(allOf(withText(R.string.tvShow_top_related), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed())) // Point No. 1
        Espresso.onView(withId(R.id.rv_tvShow)).check(RVItemCountAssertion(20)) // Point No. 2
    }
}