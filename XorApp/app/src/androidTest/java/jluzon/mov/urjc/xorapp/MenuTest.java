package jluzon.mov.urjc.xorapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MenuTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("jluzon.mov.urjc.xorapp", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void testMenu() {
        int level;

        Intent intent = new Intent();
        intent.putExtra("name", "Jorge");
        mMainActivityRule.launchActivity(intent);
        MainActivity activity = mMainActivityRule.getActivity();

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText("Level 0"))
                .perform(click());

        level = activity.levelViews.getCurrentLevel();
        if (level != 0) {
            Assert.fail("Wrong level loaded" + level);
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText("Level 1"))
                .perform(click());

        level = activity.levelViews.getCurrentLevel();
        if (level != 1) {
            Assert.fail("Wrong level loaded" + level);
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText("Level 2"))
                .perform(click());

        level = activity.levelViews.getCurrentLevel();
        if (level != 2) {
            Assert.fail("Wrong level loaded" + level);
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText("Level 3"))
                .perform(click());

        level = activity.levelViews.getCurrentLevel();
        if (level != 3) {
            Assert.fail("Wrong level loaded" + level);
        }
    }
}