package com.algovin373.project.moviecatalog.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.idleresource.EspressoIdlingResource
import com.algovin373.project.moviecatalog.retrofit.MyRetrofit
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
Scenario Instrumentation Testing :
Movie :
1. Testing to check all Data Movie RecyclerView is Displayed
2. Testing to check all every data movie is matched with sample data dummy ( using data API )

TVShow :
1. Testing to check all Data TVShow Banner RecyclerView is Displayed
2. Testing to check all every data tv show is matched with sample data dummy ( using data API )

Note : For detail data can testing in DetailMovieActivity and DetailTVShowActivity
       Fot data dummy, using data API with method ApiService
**/

class MainActivityTest {
    private var apiService = MyRetrofit.iniRetrofitMovie()
    private var apiServiceTVShow = MyRetrofit.iniRetrofitTVShow()

    @Rule @JvmField
    var mainActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    /* This is section to testing Data Movie */
    @Test
    fun toMovieActivityTest() {
        // Testing 1 :
        onView(withId(R.id.rv_movie_now_playing)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(allOf(withText(R.string.movie_popular), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(allOf(withText(R.string.movie_top_related), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(allOf(withText(R.string.movie_upcoming), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        
        // Testing 2 & 3
        /** Data Movie Now Playing in Banner Poster Movie **/
        apiService.getDataMovieNowPlaying()
            .map { it.dataMovie?.take(7) }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.poster_movie_now_playing)).check(matches(withText(it[i].posterMovie)))
                        onView(withId(R.id.title_movie_now_playing)).check(matches(withText(it[i].titleMovie)))
                    }
                },
                { }
            )

        /** 01 Data Movie Now Playing **/
        onView(allOf(withText(R.string.movie_now_playing), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(click())
        apiService.getDataMovieNowPlaying()
            .map { it.dataMovie }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_movie_catalog)).check(matches(withText(it[i].posterMovie)))
                        onView(withId(R.id.title_movie_catalog)).check(matches(withText(it[i].titleMovie)))
                        onView(withId(R.id.date_movie_catalog)).check(matches(withText(it[i].releaseDateMovie)))
                    }
                },
                {

                }
            )
        /** 02 Data Movie Popular **/
        onView(allOf(withText(R.string.movie_popular), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(click())
        apiService.getDataMoviePopular()
            .map { it.dataMovie }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_movie_catalog)).check(matches(withText(it[i].posterMovie)))
                        onView(withId(R.id.title_movie_catalog)).check(matches(withText(it[i].titleMovie)))
                        onView(withId(R.id.date_movie_catalog)).check(matches(withText(it[i].releaseDateMovie)))
                    }
                },
                {

                }
            )
        /** 03 Data Movie Top Related **/
        onView(allOf(withText(R.string.movie_top_related), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(click())
        apiService.getDataMovieTopRated()
            .map { it.dataMovie }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_movie_catalog)).check(matches(withText(it[i].posterMovie)))
                        onView(withId(R.id.title_movie_catalog)).check(matches(withText(it[i].titleMovie)))
                        onView(withId(R.id.date_movie_catalog)).check(matches(withText(it[i].releaseDateMovie)))
                    }
                },
                {

                }
            )
        /** 04 Data Movie Up Coming **/
        onView(allOf(withText(R.string.movie_upcoming), isDescendantOfA(withId(R.id.tab_layout_movie)))).perform(click())
        apiService.getDataMovieUpComing()
            .map { it.dataMovie }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_movie_catalog)).check(matches(withText(it[i].posterMovie)))
                        onView(withId(R.id.title_movie_catalog)).check(matches(withText(it[i].titleMovie)))
                        onView(withId(R.id.date_movie_catalog)).check(matches(withText(it[i].releaseDateMovie)))
                    }
                },
                {

                }
            )
    }

    @Test
    fun toTVShowActivityTest() {
        // Testing 1 :
        onView(withId(R.id.main_viewpager)).perform(ViewPagerActions.scrollToPage(1))
        onView(withId(R.id.viewpager_tvshow_banner)).check(matches(isDisplayed()))
        onView(withId(R.id.worm_dots_indicator_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(allOf(withText(R.string.tvShow_on_the_air), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(click())
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(allOf(withText(R.string.tvShow_popular), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(click())
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(allOf(withText(R.string.tvShow_top_related), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(click())
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))

        // Testing 2 & 3
        /** Data TVShow Airing Today in Banner Poster TVShow **/
        apiServiceTVShow.getDataTVShowAiringToday()
            .map { it.dataTVShow?.take(5) }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_tvshow_banner)).check(matches(withText(it[i].backgroundTVShow)))
                        onView(withId(R.id.title_tvshow_banner)).check(matches(withText(it[i].titleTVShow)))
                    }
                },
                { }
            )

        /** 01 Data TVShow Airing Today **/
        onView(allOf(withText(R.string.tvShow_airing_today), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(click())
        apiServiceTVShow.getDataTVShowAiringToday()
            .map { it.dataTVShow }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_tvshow_catalog)).check(matches(withText(it[i].backgroundTVShow)))
                        onView(withId(R.id.title_tvshow_catalog)).check(matches(withText(it[i].titleTVShow)))
                        onView(withId(R.id.date_tvshow_catalog)).check(matches(withText(it[i].firstDateTVShow)))
                    }
                },
                {

                }
            )
        /** 02 Data TVShow On The Air **/
        onView(allOf(withText(R.string.tvShow_on_the_air), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(click())
        apiServiceTVShow.getDataTVShowOnTheAirToday()
            .map { it.dataTVShow }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_tvshow_catalog)).check(matches(withText(it[i].backgroundTVShow)))
                        onView(withId(R.id.title_tvshow_catalog)).check(matches(withText(it[i].titleTVShow)))
                        onView(withId(R.id.date_tvshow_catalog)).check(matches(withText(it[i].firstDateTVShow)))
                    }
                },
                {

                }
            )

        /** 03 Data TVShow Popular **/
        onView(allOf(withText(R.string.tvShow_popular), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(click())
        apiServiceTVShow.getDataTVShowPopularToday()
            .map { it.dataTVShow }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_tvshow_catalog)).check(matches(withText(it[i].backgroundTVShow)))
                        onView(withId(R.id.title_tvshow_catalog)).check(matches(withText(it[i].titleTVShow)))
                        onView(withId(R.id.date_tvshow_catalog)).check(matches(withText(it[i].firstDateTVShow)))
                    }
                },
                {

                }
            )
        /** 04 Data TVShow Top Related **/
        onView(allOf(withText(R.string.tvShow_top_related), isDescendantOfA(withId(R.id.tab_layout_tvShow)))).perform(click())
        apiServiceTVShow.getDataTVShowTopRatedToday()
            .map { it.dataTVShow }
            .subscribe(
                {
                    for(i in 0 until it!!.size) {
                        onView(withId(R.id.image_tvshow_catalog)).check(matches(withText(it[i].backgroundTVShow)))
                        onView(withId(R.id.title_tvshow_catalog)).check(matches(withText(it[i].titleTVShow)))
                        onView(withId(R.id.date_tvshow_catalog)).check(matches(withText(it[i].firstDateTVShow)))
                    }
                },
                {

                }
            )
    }
}