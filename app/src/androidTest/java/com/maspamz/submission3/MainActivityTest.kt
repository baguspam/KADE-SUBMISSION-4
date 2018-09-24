package com.maspamz.submission3

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.widget.Spinner
import com.maspamz.submission3.R.id.*
import com.maspamz.submission3.R.layout.item_club
import com.maspamz.submission3.config.data.Favorite
import com.maspamz.submission3.config.data.FavoriteClub
import com.maspamz.submission3.config.db.DatabaseConfig
import com.maspamz.submission3.config.db.database
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.dropTable
import org.jetbrains.anko.design.snackbar
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.file.Files.delete

/**
 * Created by Maspamz on 15/09/2018.
 *
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testAppBehaviour(){
        Thread.sleep(2000)

        //Match Next
            onView(withId(l_spinner)).check(matches(isDisplayed()))
            onView(withId(l_spinner)).perform(click())
            onView(withText("English Premier League")).perform(click())
        Thread.sleep(2000)
            onView(withId(lrv_view_match)).check(matches(isDisplayed()))
            onView(withId(lrv_view_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        Thread.sleep(2000)
            onView(withId(add_to_favorite)).check(matches(isDisplayed()))
            onView(withId(add_to_favorite)).perform(click())
            onView(withText(R.string.add_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        //End Match Next

        //Match Last
            onView(withId(layout_bottom)).check(matches(isDisplayed()))
            onView(withId(navigation_match_last)).perform(click())
        Thread.sleep(2000)
            onView(withId(l_spinner)).check(matches(isDisplayed()))
            onView(withId(l_spinner)).perform(click())
            onView(withText("English League Championship")).perform(click())
        Thread.sleep(2000)
            onView(withId(lrv_view_match)).check(matches(isDisplayed()))
            onView(withId(lrv_view_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        Thread.sleep(2000)
            onView(withId(add_to_favorite)).check(matches(isDisplayed()))
            onView(withId(add_to_favorite)).perform(click())
            onView(withText(R.string.add_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        //End Match Last

        //Favorite Match
        onView(withId(layout_bottom)).check(matches(isDisplayed()))
        onView(withId(navigation_favorite_match)).perform(click())
        for(number in 0..1) {
            Thread.sleep(2000)
            onView(withId(lrv_view_favorite)).check(matches(isDisplayed()))
            onView(withId(lrv_view_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(number, click()))
            onView(withId(add_to_favorite)).check(matches(isDisplayed()))
            onView(withId(add_to_favorite)).perform(click())
            onView(withText(R.string.remove_favorite))
                    .check(matches(isDisplayed()))
            pressBack()
        }

        onView(withId(l_swipe_favorite)).perform(ViewActions.swipeDown())
        //End

        //Club
            onView(withId(layout_bottom)).check(matches(isDisplayed()))
            onView(withId(navigation_club)).perform(click())
        Thread.sleep(2000)
            onView(withId(l_spinner)).check(matches(isDisplayed()))
            onView(withId(l_spinner)).perform(click())
            onView(withText("German Bundesliga")).perform(click())
        Thread.sleep(3000)
            onView(withId(lrv_view_club)).check(matches(isDisplayed()))
            onView(withId(lrv_view_club)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(2000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText(R.string.add_favorite))
                .check(matches(isDisplayed()))
        pressBack()
        //End Club

        //Favorite Club
            onView(withId(layout_bottom)).check(matches(isDisplayed()))
            onView(withId(navigation_favorite_club)).perform(click())
        Thread.sleep(2000)
            onView(withId(lrv_view_favorite)).check(matches(isDisplayed()))
            onView(withId(lrv_view_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
            onView(withId(add_to_favorite)).check(matches(isDisplayed()))
            onView(withId(add_to_favorite)).perform(click())
            onView(withText(R.string.remove_favorite))
                    .check(matches(isDisplayed()))
            pressBack()

        onView(withId(l_swipe_favorite)).perform(ViewActions.swipeDown())
        //End



    }

}