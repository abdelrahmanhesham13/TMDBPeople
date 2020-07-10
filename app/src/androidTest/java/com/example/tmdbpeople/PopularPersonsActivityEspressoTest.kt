package com.example.tmdbpeople

import android.Manifest
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.example.tmdbpeople.views.activities.PopularPersonsActivity
import com.example.tmdbpeople.views.activities.SearchPersonsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PopularPersonsActivityEspressoTest {

    @get:Rule
    var activityRule: ActivityTestRule<PopularPersonsActivity> = ActivityTestRule(PopularPersonsActivity::class.java)

    @get:Rule
    var permissionRule =
        GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)


    @Test
    fun checkOpenActivityBySearch() {
        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.search_edit_text)).check(matches(isDisplayed()))
    }

    @Test
    fun checkTypeTextToSearchAndOpenDetails() {
        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.search_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.search_edit_text)).perform(typeText("Scarlett"))
        onView(withId(R.id.search_results_recycler)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.search_results_recycler))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.details_recycler)).check(matches(isDisplayed()))
    }

    @Test
    fun checkOpenActorImage() {
        checkClickingRecyclerViewOpenDetails()
        onView(withId(R.id.details_recycler))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(withId(R.id.details_recycler))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.person_image)).check(matches(isDisplayed()))
    }

    @Test
    fun checkDownloadActorImage() {
        checkOpenActorImage()
        onView(withId(R.id.download)).perform(click())
    }

    @Test
    fun checkRefreshButtonIsDisplayedAndWorking() {
        onView(withId(R.id.refresh)).perform(click());
    }

    @Test
    fun checkClickingRecyclerViewOpenDetails() {
        onView(withId(R.id.persons_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.persons_recycler))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.details_recycler)).check(matches(isDisplayed()))
    }

}