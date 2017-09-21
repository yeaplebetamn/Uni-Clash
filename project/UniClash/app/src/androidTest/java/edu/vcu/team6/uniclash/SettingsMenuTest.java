package edu.vcu.team6.uniclash;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.openLinkWithText;
import static android.support.test.espresso.core.deps.guava.io.Resources.getResource;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;

/**
 * Testing Scenarios from user story 3
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SettingsMenuTest {
    @Rule
        public ActivityTestRule<SettingsMenu> mActivityTestRule = new ActivityTestRule<>(SettingsMenu.class);

    @Test
    public void TeamSelectSpider()  {
        onView(withId(R.id.spidersRB));
        mActivityTestRule.getActivity().setTeam("Spiders");
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    mActivityTestRule.getActivity().openFileInput("team")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString);
            }
            assertEquals("Spiders",stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Test
    public void TeamSelectRams() {
        mActivityTestRule.getActivity().setTeam("Rams");
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    mActivityTestRule.getActivity().openFileInput("team")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString);
            }
            assertEquals("Rams",stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void TeamSelectHokies() {
        mActivityTestRule.getActivity().setTeam("Hokies");
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    mActivityTestRule.getActivity().openFileInput("team")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString);
            }
            assertEquals("Hokies", stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
