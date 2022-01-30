package com.algovin373.project.moviecatalog.ui.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.algokelvin373.project.moviecatalog.testing.AlternativeFragmentActivity
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.idleresource.EspressoIdlingResource
import com.algovin373.project.moviecatalog.utils.RVItemCountAssertion
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*
Scenario Instrumentation Testing :
1. Testing to can display data Movie in RecyclerView ( complete )
2. Testing to check that the Data Movie is n in RecyclerView ( complete )
 */

class MovieFragmentTest {
    @Rule @JvmField
    var fragmentMovieRule: ActivityTestRule<AlternativeFragmentActivity> = ActivityTestRule(AlternativeFragmentActivity::class.java)
    private val movieFragment = MovieFragment()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        fragmentMovieRule.activity.setFragment(movieFragment)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadCourses() {
        /** Data Movie Now Playing in Banner Poster Movie **/
        onView(withId(R.id.rv_movie_now_playing)).check(matches(isDisplayed())) // Point No. 1
        onView(withId(R.id.rv_movie_now_playing)).check(RVItemCountAssertion(7)) // Point No. 2

        /** Data Movie Now Playing **/
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed())) // Point No. 1
        onView(withId(R.id.rv_movie)).check(RVItemCountAssertion(20)) // Point No. 2

        /** Data Movie Popular **/
        onView(Matchers.allOf(withText(R.string.movie_popular), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(ViewActions.click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed())) // Point No. 1
        onView(withId(R.id.rv_movie)).check(RVItemCountAssertion(20)) // Point No. 2

        /** Data Movie Top Related **/
        onView(Matchers.allOf(withText(R.string.movie_top_related), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(ViewActions.click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed())) // Point No. 1
        onView(withId(R.id.rv_movie)).check(RVItemCountAssertion(20)) // Point No. 2

        /** Data Movie Up Coming **/
        onView(Matchers.allOf(withText(R.string.movie_upcoming), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(ViewActions.click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed())) // Point No. 1
        onView(withId(R.id.rv_movie)).check(RVItemCountAssertion(20)) // Point No. 2
    }
}