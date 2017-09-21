//Test Scenarios from User Story 11
package edu.vcu.team6.uniclash;

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

    private GameActivity gameActivity;

    @Rule
    public ActivityTestRule<GameActivity> mActivityTestRule = new ActivityTestRule<>(GameActivity.class);

    @Test
    // Code modified from http://stackoverflow.com/questions/34294745/calling-a-method-of-the-tested-activity-from-a-test-using-espresso-and-see-its-r
    // Tests that the initial health displayed text is "A+ Health"
    // For Scenario 1
    public void testInitialHealth() throws Throwable {
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A+ Health")));
        }


    @Test
    // Tests that the health after being hit for 10 points damage is displayed as "A- Health"
    // For Scenario 2
    public void testAttack1() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(10);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A- Health")));
    }

    @Test
    // Tests that the health after being hit for 12 points damage is displayed as "B+ Health"
    public void testAttack2() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(12);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("B+ Health")));
    }

    @Test
    // Tests that the health after being hit for 15 points damage is displayed as "B- Health"
    public void testAttack3() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(15);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("B- Health")));
    }

    @Test
    // Tests that the health after being hit for 99 points damage is displayed as "F Health"
    public void testAttack4() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(99);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("F Health")));
    }

    @Test
    // Tests that the health after being hit for 100 points damage is displayed as "Withdraw"
    public void testAttack5() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(100);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("Withdraw")));
    }

    @Test
    // Tests that the health after being hit for 150 points damage is displayed as "Withdraw"
    public void testAttack6() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(150);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("Withdraw")));
    }

    @Test
    // Tests that the health after being healed 10 points is displayed as "A+ Health"
    // For Scenario 3, tests the methods that will be used with the shop menu in later iterations.
    public void testHeal1() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseHealed(10);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A+ Health")));
    }

    @Test
    // Tests that the health after being damaged 10 points then healed 10 points is displayed as "A+ Health"
    public void testHeal2() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameActivity = mActivityTestRule.getActivity();
                gameActivity.baseAttacked(10);
                gameActivity.baseHealed(10);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A+ Health")));
    }

    @Test
    // Tests that the health after being damaged 15 points then healed 10 points is displayed as "A- Health"
    public void testHeal3() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(15);
                mActivityTestRule.getActivity().baseHealed(10);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("A- Health")));
    }

    @Test
    // Tests that the health after being damaged 150 points then healed 10 points is displayed as "Withdraw"
    public void testHeal4() throws Throwable{
        mActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().baseAttacked(150);
                mActivityTestRule.getActivity().baseHealed(10);
                onView(withId(R.id.healthBar));
            }
        });
        onView(withId(R.id.healthBar)).check(ViewAssertions.matches(withText("Withdraw")));
    }
}
