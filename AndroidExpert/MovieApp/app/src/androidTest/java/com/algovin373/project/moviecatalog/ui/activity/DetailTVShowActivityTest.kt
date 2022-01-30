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
import com.algovin373.project.moviecatalog.utils.dataTVShow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailTVShowActivityTest {
    private val dummyDataTVShow = dataTVShow()[0]

    @Rule
    @JvmField
    var activityDetailTVShowRule: ActivityTestRule<DetailTVShowActivity> =
        object : ActivityTestRule<DetailTVShowActivity>(DetailTVShowActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, DetailTVShowActivity::class.java)
                result.putExtra("ID", 79340) /** Using sample TV Show with ID = 79340 **/
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
        /** Title TV Show **/
        onView(withId(R.id.title_catalog_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.title_catalog_tv_show)).check(matches(withText(dummyDataTVShow.titleTVShow)))

        /** First Data TV Show **/
        onView(withId(R.id.date_release_catalog_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.date_release_catalog_tv_show)).check(matches(withText(dummyDataTVShow.firstDateTVShow)))

        /** Total Seasons TV Show **/
        onView(withId(R.id.seasons_catalog_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.seasons_catalog_tv_show)).check(matches(withText(dummyDataTVShow.seasonsTVShow)))

        /** Total Episodes TV Show **/
        onView(withId(R.id.episodes_release_catalog_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.episodes_release_catalog_tv_show)).check(matches(withText(dummyDataTVShow.episodesTVShow)))

        /** Overview TV Show **/
        onView(withId(R.id.overview_catalog_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.overview_catalog_tv_show)).check(matches(withText(dummyDataTVShow.overviewTVShow)))

        /** All Data RecyclerView Cast, Similar, and Recommendation TV Show **/
        onView(withId(R.id.rv_cast_tv_show)).check(matches(isDisplayed()))
//        onView(withId(R.id.rv_similar_tv_show)).check(matches(isDisplayed()))
//        onView(withId(R.id.rv_recommendation_tv_show)).check(matches(isDisplayed()))
    }
}