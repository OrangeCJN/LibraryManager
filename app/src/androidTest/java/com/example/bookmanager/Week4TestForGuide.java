package com.example.bookmanager;

import android.support.test.rule.ActivityTestRule;

import com.example.bookmanager.ui.GuideActivity;
import com.example.bookmanager.util.SPUtils;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bookmanager.EspressoUtil.sleep;

/**
 * Test for Week4 Guide
 */
public class Week4TestForGuide {

    /**
     * set showGuide
     */
    @Before
    public void resetGuideBoolean(){
        SPUtils.putBoolean("showGuide", true);
    }

    @Rule
    public ActivityTestRule<GuideActivity> mGuideActivityActivityTestRule = new ActivityTestRule<>(GuideActivity.class);

    /**
     * Test guide activity skip
     */
    @Test
    public void guideActivitySkip(){
        sleep(1000L);
        onView(Matchers.allOf(withId(R.id.pager),
                isDisplayed())).perform(swipeLeft());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.skip),
                isDisplayed())).perform(click());
        sleep(3000L);
    }

    /**
     * Test guide activity enter
     */
    @Test
    public void guideActivityEnter(){
        sleep(1000L);
        onView(Matchers.allOf(withId(R.id.pager),
                isDisplayed())).perform(swipeLeft());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pager),
                isDisplayed())).perform(swipeLeft());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pager),
                isDisplayed())).perform(swipeLeft());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pager),
                isDisplayed())).perform(swipeLeft());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.enter),
                isDisplayed())).perform(click());
        sleep(3000L);
    }

}
