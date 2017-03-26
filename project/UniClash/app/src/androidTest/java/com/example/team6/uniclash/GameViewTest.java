/*
User story 8 testing
    tests GameView buttons
 */

package com.example.team6.uniclash;

import android.app.Application;
import android.app.Fragment;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.method.Touch;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GameViewTest extends Fragment{

    @Rule
    public ActivityTestRule<GameView> gameViewActivityTestRule = new ActivityTestRule<>(GameView.class);

    //ShopButton tests
    @Test   //checks for expected shop popup info dialogue title
    public void testShopButtonDialogue(){
        onView(withId(R.id.shopButton)).perform(click());

        onView(withText("Shop")).inRoot(isDialog()).check((matches(isDisplayed())));
    }
    @Test   //checks if back button on dialogue works
    public void testShopBackButton(){
        onView(withId(R.id.shopButton)).perform(click());

        pressBack();    //presses hardware back button, in this case equivalent to back button on popup as it is only the one button
    }

    //WaveNumButton tests
    @Test //checks for expected wave info dialogue title
    public void testWaveButton(){
        onView(withId(R.id.waveButton)).perform(click());

        onView(withText("Study this for the incoming wave")).inRoot(isDialog()).check((matches(isDisplayed())));
    }
    @Test   //checks if back button on dialogue works
    public void testWaveBackButton(){
        onView(withId(R.id.waveButton)).perform(click());

        pressBack();    //presses hardware back button, in this case equivalent to back button on popup as it is only the one button
    }

    @Test //test Pause button goes to settings
    public void testPauseButton(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SettingsMenu.class.getName(), null, false);
        onView(withId(R.id.pauseButton)).perform(click());
        SettingsMenu pauseToSettings = (SettingsMenu) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        assertNotNull(pauseToSettings);
        pauseToSettings.finish();
    }

    @Test //test StartWave button
    public void testStartWaveButton(){
        onView(withId(R.id.startWaveButton)).perform(click()); //only tests for button click (actual functionality not yet implemented)
    }
}
