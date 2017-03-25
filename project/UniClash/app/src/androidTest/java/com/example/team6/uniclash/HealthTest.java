package com.example.team6.uniclash;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.R.attr.fragment;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.team6.uniclash.R.id.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HealthTest {

    @Rule
    public ActivityTestRule<GameView> mActivityTestRule = new ActivityTestRule<>(GameView.class);

    @Test
    public void testInitialHealth() throws Throwable {
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(0);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A+ Health")));
        }

    /*
    @Test
    public void testAttack1(){
        gameView.baseAttacked(1);
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A+ Health")));
    }

    @Test
    public void testAttack2(){
        gameView.baseAttacked(5);
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A- Health")));
    }

    @Test
    public void testAttack3(){
        gameView.baseAttacked(10);
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("B- Health")));
    }
*/

}
