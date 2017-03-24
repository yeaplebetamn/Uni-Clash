package com.example.team6.uniclash;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HealthTest {

    @Rule
    public ActivityTestRule<GameView> mActivityTestRule = new ActivityTestRule<>(GameView.class);

    @Test
    public void testInitialHealth(){
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A+ Health")));
    }

    @Test
    public void testAttack1(){
        //baseAttacked(){}
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A+ Health")));
    }


}
