package com.algovin373.project.moviecatalog.ui.activity

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.idleresource.EspressoIdlingResource
import com.algovin373.project.moviecatalog.utils.dataMovie
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailMovieActivityTest {
    private val dummyData = dataMovie()[0]

    @Rule @JvmField
    var activityDetailMovieRule: ActivityTestRule<DetailMovieActivity> =
        object : ActivityTestRule<DetailMovieActivity>(DetailMovieActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, DetailMovieActivity::class.java)
                result.putExtra("ID", 384018) /** Using sample Movie with ID = 384018 **/
                return result
            }
    }

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadDetailMovie() {
        Thread.sleep(2000)
        /** Title Movie **/
        onView(withId(R.id.title_catalog_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.title_catalog_movie)).check(matches(withText(dummyData.titleMovie)))

        /** Date Release Movie **/
        onView(withId(R.id.date_release_catalog_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.date_release_catalog_movie)).check(matches(withText(dummyData.dateReleaseMovie)))

        /** Status Release Movie **/
        onView(withId(R.id.status_release_catalog_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.status_release_catalog_movie)).check(matches(withText(dummyData.statusMovie)))

        /** Run Time Release Movie **/
        onView(withId(R.id.runtime_release_catalog_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.runtime_release_catalog_movie)).check(matches(withText(dummyData.runtimeMovie)))

        /** Vote Average Movie **/
        onView(withId(R.id.vote_average_release_catalog_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.vote_average_release_catalog_movie)).check(matches(withText(dummyData.voteAverageMovie)))

        /** Overview Movie **/
        onView(withId(R.id.overview_catalog_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.overview_catalog_movie)).check(matches(withText(dummyData.overviewMovie)))

        /** All Data RecyclerView Cast, Similar, and Recommendation Movie **/
        onView(withId(R.id.rv_cast_movie)).check(matches(isDisplayed()))
//        onView(withId(R.id.rv_similar_movie)).check(matches(isDisplayed()))
//        onView(withId(R.id.rv_recommendation_movie)).check(matches(isDisplayed()))

    }
}