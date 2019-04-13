package com.example.bookmanager;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bookmanager.ui.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.bookmanager.EspressoUtil.sleep;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void adminLoginTest() {
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.admin),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.nick),
                isDisplayed())).perform(typeText("test_admin_username"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pwd),
                isDisplayed())).perform(typeText("test_admin_password"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.login),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    @Test
    public void userLoginTest() {
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.normal),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.nick),
                isDisplayed())).perform(typeText("test_user_username"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pwd),
                isDisplayed())).perform(typeText("test_user_password"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.login),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

    @Test
    public void userRegisterTest() {
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.normal),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.regi),
                isDisplayed())).perform(click());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.name),
                isDisplayed())).perform(typeText("test_user_name"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.username),
                isDisplayed())).perform(typeText("test_user_username"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.pass),
                isDisplayed())).perform(typeText("test_user_password"), closeSoftKeyboard());
        sleep(500L);
        onView(Matchers.allOf(withId(R.id.save),
                isDisplayed())).perform(click());
        sleep(6000L);
    }

}
