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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bookmanager.EspressoUtil.sleep;

/**
 * Test for Week3
 */
public class Week3Test {

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
     * Test change password
     */
    @Test
    public void userChangePassword(){
        userLogin();
        onView(Matchers.allOf(withId(R.id.cpgl),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.sname),
                isDisplayed())).perform(typeText("test_sname"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.old_pass),
                isDisplayed())).perform(typeText("test_old_pass"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.new_pass),
                isDisplayed())).perform(typeText("test_new_pass"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.confirm),
                isDisplayed())).perform(typeText("test_confirm"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.save),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test activity manager
     */
    @Test
    public void adminActivityManager(){
        adminLogin();
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
     * Test push comment
     */
    @Test
    public void userPushComment(){
        userLogin();
        onView(Matchers.allOf(withId(R.id.mmm2),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.add),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.content),
                isDisplayed())).perform(typeText("test_push_comment"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.anonymous),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.save),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test user goto books
     */
    @Test
    public void userGotoBooks() {
        userLogin();
        onView(Matchers.allOf(withId(R.id.fcgl),
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
     * Test user goto books and filter
     */
    @Test
    public void userGotoBooksAndFilter() {
        userLogin();
        onView(Matchers.allOf(withId(R.id.fcgl),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.rb_1),
                isDisplayed())).perform(click());
        sleep(1500L);
        onView(Matchers.allOf(withId(R.id.rb_2),
                isDisplayed())).perform(click());
        sleep(1500L);
        onView(Matchers.allOf(withId(R.id.rb_3),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test user move back
     */
    @Test
    public void userMoveBack() {
        userLogin();
        sleep(1500L);
        onView(Matchers.allOf(withContentDescription("Navigate up"),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    /**
     * Test admin move back
     */
    @Test
    public void adminMoveBack() {
        adminLogin();
        sleep(1500L);
        onView(Matchers.allOf(withContentDescription("Navigate up"),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

}
