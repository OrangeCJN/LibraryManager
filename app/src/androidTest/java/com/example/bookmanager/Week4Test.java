package com.example.bookmanager;

import android.support.test.rule.ActivityTestRule;

import com.example.bookmanager.ui.MainActivity;
import com.example.bookmanager.util.SPUtils;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bookmanager.EspressoUtil.sleep;

/**
 * Test for Week4
 */
public class Week4Test {

    /**
     * set showGuide
     */
    @Before
    public void resetGuideBoolean(){
        SPUtils.putBoolean("showGuide", false);
    }

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Login admin
     */
    @Test
    public void adminLogin() {
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.admin),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.nick),
                isDisplayed())).perform(typeText("admin1"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pwd),
                isDisplayed())).perform(typeText("admin1"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.login),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Login user
     */
    @Test
    public void userLogin() {
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.normal),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.nick),
                isDisplayed())).perform(typeText("666666"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pwd),
                isDisplayed())).perform(typeText("999999"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.login),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test user activities
     */
    @Test
    public void userActivities(){
        userLogin();
        onView(Matchers.allOf(withId(R.id.mmm1),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.et1),
                isDisplayed())).perform(typeText("test_query"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.btn_search),
                isDisplayed())).perform(click());
        sleep(2500L);
        onView(Matchers.allOf(withId(R.id.btn_clear),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test user scan QRCode
     */
    @Test
    public void userScanQRCode(){
        userLogin();
        onView(Matchers.allOf(withId(R.id.fcgl),
                isDisplayed())).perform(click());
        sleep(1000L);
        onView(Matchers.allOf(withId(R.id.scan),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test comment manager
     */
    @Test
    public void adminCommentManager(){
        adminLogin();
        onView(Matchers.allOf(withId(R.id.mmm2),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.sort),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withText("Reverse order"),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test admin books statistical
     */
    @Test
    public void adminBooksStatistical(){
        adminLogin();
        onView(Matchers.allOf(withId(R.id.statistical),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withText("Books Statistical"),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test admin message statistical
     */
    @Test
    public void adminMessagesStatistical(){
        adminLogin();
        onView(Matchers.allOf(withId(R.id.statistical),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withText("Messages Statistical"),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test admin artifact
     */
    @Test
    public void adminArtifact(){
        adminLogin();
        onView(Matchers.allOf(withId(R.id.artifact),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

}
