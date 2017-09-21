//package com.example.team6.uniclash;
//
////import com.example.team6.uniclash.R;
//import android.app.Fragment;
//import android.os.Bundle;
//import android.support.test.espresso.UiController;
//import android.support.test.espresso.ViewAction;
//import android.support.test.espresso.assertion.ViewAssertions;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import org.hamcrest.Matcher;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.InstrumentationRegistry.getContext;
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//
//// Modified from http://stackoverflow.com/questions/39229722/how-to-call-a-method-on-a-custom-view-in-espresso-test
//public class EnemiesTest implements ViewAction {
//
//    @Override
//    public Matcher<View> getConstraints(){
//        return isAssignableFrom(GameView.class);
//    }
//
//
//    @Override
//    public String getDescription(){
//        return "whatever";
//    }
//
////    @Override
////    public void perform(UiController uiController, View view){
////        GameView mygameView = (GameView) view;
////        GameView.();
////        // tadaaa
////    }
//
//}