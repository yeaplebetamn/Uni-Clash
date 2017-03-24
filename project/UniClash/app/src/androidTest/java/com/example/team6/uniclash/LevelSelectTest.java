/*
User story 2 testing
    tests LevelSelectMenu to be sure each level button selected launches to the GameView.class
 */

package com.example.team6.uniclash;

import android.app.Application;
import android.app.Fragment;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LevelSelectTest extends Fragment {


    @Rule
    public ActivityTestRule<LevelSelectMenu> mActivityTestRule = new ActivityTestRule<>(LevelSelectMenu.class);


    @Test
    public void testLevel1Button()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(GameView.class.getName(), null, false);
        onView(withId(R.id.level1Button)).perform(click());
        GameView gameView = (GameView) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        assertNotNull(gameView);
        gameView.finish();
    }

    @Test
    public void testLevel2Button()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(GameView.class.getName(), null, false);
        onView(withId(R.id.level2Button)).perform(click());
        GameView gameView = (GameView) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        assertNotNull(gameView);
        gameView.finish();
    }

    @Test
    public void testLevel3Button()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(GameView.class.getName(), null, false);
        onView(withId(R.id.level3Button)).perform(click());
        GameView gameView = (GameView) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        assertNotNull(gameView);
        gameView.finish();
    }
}