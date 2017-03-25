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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GameViewTest extends Fragment{

    @Rule
    public ActivityTestRule<GameView> gameViewActivityTestRule = new ActivityTestRule<>(GameView.class);

    @Test   //test shop button
    public void testShopButton{
        //Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SHOPMENU.class.getName()), null, false);
        //onView(withID(R.id.shopButton)).perform(click());
        //GameView gameView = (GameView) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        //assertNotNull(gameView);
        //gameView.finish();
    }

    @Test //test WaveNum button
    public void testWaveNumButton(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(GameView.class.getName(), null, false);

        TouchUtils.clickView(this, WaveNum);
    }

}
