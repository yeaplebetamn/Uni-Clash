package edu.vcu.team6.uniclash;

import android.app.Fragment;
import android.app.Instrumentation;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import android.support.test.rule.ActivityTestRule;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainMenuTest extends Fragment {


    @Rule
    public ActivityTestRule<MainMenu> mActivityTestRule = new ActivityTestRule<>(MainMenu.class);

    /* Test help :http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
    */


  /*  @Test
    public void testOpenNextActivity(){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LevelSelectMenu.class.getName(), null, false);

        // open current activity.
        MainMenu myActivity = (MainMenu) getActivity();
        final Button button = (Button) myActivity.findViewById(com.example.team6.uniclash.R.id.PlayButton);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                button.performClick();
            }
        });

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
            LevelSelectMenu levelSelectMenuActivity = (LevelSelectMenu) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 100000);
        // next activity is opened and captured.
        assertNotNull(levelSelectMenuActivity);
        levelSelectMenuActivity.finish();
    }
*/
    @Test

      public void testPlayButton()
        {
            Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LevelSelectMenu.class.getName(), null, false);
            onView(withId(R.id.PlayButton)).perform(click());
            LevelSelectMenu levelSelectMenuActivity = (LevelSelectMenu) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
            assertNotNull(levelSelectMenuActivity);
            levelSelectMenuActivity.finish();
        }

    @Test

    public void testSettingsButton()
    {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SettingsMenu.class.getName(), null, false);
        onView(withId(R.id.SettingsButton)).perform(click());
        SettingsMenu levelSelectMenuActivity = (SettingsMenu) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        assertNotNull(levelSelectMenuActivity);
        levelSelectMenuActivity.finish();
    }
}


