package com.example.team6.uniclash;

//import com.example.team6.uniclash.R;
import android.os.Bundle;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EnemiesTest{
    GameView myGameView;

    private GameActivity gameActivity;
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState){
        myGameView =  inflater.inflate(R.layout.gameView,
                container,
                false
        );

    @Rule
    public ActivityTestRule<GameActivity> mActivityTestRule = new ActivityTestRule<>(GameActivity.class);

    @Test
    public void testInitialHealth() throws Throwable {
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onView(withId(R.id.gameView));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A+ Health")));
    }
}
