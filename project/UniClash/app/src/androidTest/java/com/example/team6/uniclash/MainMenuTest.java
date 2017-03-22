package com.example.team6.uniclash;

import android.app.Fragment;
import android.app.Instrumentation;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainMenuTest extends Fragment {

    @Rule
    public ActivityTestRule<MainMenu> mActivityTestRule = new ActivityTestRule<>(MainMenu.class);
    /*public void displayTest(){
        //ensure that the UI is displaying correctly
        ViewInteraction PlayButton = onView(
        )
*/
    @Test
    public void testOpenNextActivity(){
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LevelSelectMenu.class.getName(), null, false);

        // open current activity.
        MainMenu myActivity = (MainMenu) getActivity();
        final Button button = (Button) myActivity.findViewById(R.id.PlayButton);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                button.performClick();
            }
        });

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        TeamSelectMenu teamSelectMenuActivity = (TeamSelectMenu) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(teamSelectMenuActivity);
        teamSelectMenuActivity.finish();
    }
}


