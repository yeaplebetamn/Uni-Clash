package com.example.team6.uniclash;


import android.support.test.runner.AndroidJUnit4;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.content.Intent;

/**
 * Created by Marjorie on 3/24/2017.
 *
 * Tests activity switching starting with MainMenu.class
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivitySwitchingTest extends Fragment {

    @Rule
    public ActivityTestRule<MainMenu> mActivityRule = new ActivityTestRule<>(MainMenu.class);

    @Test
    public void ensureMainMenuSwitching() {
        //MainMenu to Settings activity switch
        onView(withId(R.id.SettingsButton)).perform(click());

        //Check if Settings.class has been launched
        intended(hasComponent(SettingsMenu.class.getName()));

    }
}
