package com.example.bookmanager;

import android.support.test.rule.ActivityTestRule;

import com.example.bookmanager.ui.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.bookmanager.EspressoUtil.sleep;

/**
 * Test for Week2
 */
public class Week2Test {

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
                isDisplayed())).perform(typeText("iampinky"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pwd),
                isDisplayed())).perform(typeText("PinkyZombie"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.login),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * goto page by button click event
     *
     * @param buttonId
     */
    public void gotoPageByButtonClick(int buttonId) {
        sleep(500L);
        onView(Matchers.allOf(withId(buttonId),
                isDisplayed())).perform(click());
    }

    /**
     * test UserManager
     */
    @Test
    public void adminLoginAndGoFeaturePageForUserManager() {
        adminLogin();
        gotoPageByButtonClick(R.id.cpgl);
    }

    /**
     * test BookManager
     */
    @Test
    public void adminLoginAndGoFeaturePageForBookManager() {
        adminLogin();
        gotoPageByButtonClick(R.id.fcgl);
    }

    /**
     * test ActivityManager
     */
    @Test
    public void adminLoginAndGoFeaturePageForActivityManager() {
        adminLogin();
        gotoPageByButtonClick(R.id.mmm1);
    }

    /**
     * test MessageManager
     */
    @Test
    public void adminLoginAndGoFeaturePageForMessageManager() {
        adminLogin();
        gotoPageByButtonClick(R.id.mmm2);
    }

    /**
     * test add user
     */
    @Test
    public void adminLoginAndGoFeaturePageForAddUser() {
        adminLoginAndGoFeaturePageForUserManager();
        onView(Matchers.allOf(withId(R.id.add),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.name),
                isDisplayed())).perform(typeText("test_add_user_name"));
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.username),
                isDisplayed())).perform(typeText("test_add_user_username"));
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pass),
                isDisplayed())).perform(typeText("test_add_user_password"));
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.confirm),
                isDisplayed())).perform(typeText("test_add_user_confirm"));
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.save),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * test change password
     */
    @Test
    public void userLoginAndGoFeaturePageForChangePassword() {
        userLogin();
        gotoPageByButtonClick(R.id.cpgl);
    }

    /**
     * test BookManager
     */
    @Test
    public void userLoginAndGoFeaturePageForBorrowBooks() {
        userLogin();
        gotoPageByButtonClick(R.id.fcgl);
    }

    /**
     * test ActivityManager
     */
    @Test
    public void userLoginAndGoFeaturePageForLookActivities() {
        userLogin();
        gotoPageByButtonClick(R.id.mmm1);
    }

    /**
     * test MessageManager
     */
    @Test
    public void userLoginAndGoFeaturePageForPushMessage() {
        userLogin();
        gotoPageByButtonClick(R.id.mmm2);
    }

}
