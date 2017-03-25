package com.example.team6.uniclash;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.net.URL;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.io.Resources.getResource;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Ruchi on 3/24/2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SettingsMenuTest{
    @Rule
        public ActivityTestRule<SettingsMenu> mActivityTestRule = new ActivityTestRule<>(SettingsMenu.class);
    @Test
            public void TeamSelectSpider() {
           onView(withId(R.id.spidersRB)).perform(click());
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("team");
           assertEquals("Spiders",in);
            }
    @Test
    public void TeamSelectRams() {
        onView(withId(R.id.ramsRB)).perform(click()).check(matches(withText("Rams")));

    }
    @Test
    public void TeamSelectHokies() {
        onView(withId(R.id.hokiesRB)).perform(click()).check(matches(withText("Hokies")));
    }



}
