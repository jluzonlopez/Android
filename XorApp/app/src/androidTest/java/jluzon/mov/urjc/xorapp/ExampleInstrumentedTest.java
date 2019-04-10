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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("jluzon.mov.urjc.xorapp", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class,false,false);

    @Test
    public void passLevelsActivity() {
        // Pasamos el intent queq necesitamos

        Intent intent = new Intent();
        intent.putExtra("name","Jorge");
        mMainActivityRule.launchActivity(intent);

        MainActivity activity = mMainActivityRule.getActivity();

        String playerName = activity.levelViews.getPlayerName();
        int level;

        if(!playerName.equals("Jorge")){
            Assert.fail("Wrong name loaded"+playerName);
        }

        level = activity.levelViews.getCurrentLevel();
        if(level != 0){
            Assert.fail("Wrong level loaded"+level);
        }

        //pasando nivel 1
        onView(withId(R.id.ent1))
                .perform(click())
                .check(matches((isEnabled())));
        onView(withId(R.id.solutionButton))
                .perform(click());

        onView(withId(R.id.ent0))
                .perform(click())
                .check(matches((isEnabled())));
        onView(withId(R.id.ent1))
                .perform(click())
                .check(matches((isEnabled())));
        onView(withId(R.id.solutionButton))
                .perform(click());
        onView(withId(R.id.game3))
                .perform(click());

        level = activity.levelViews.getCurrentLevel();
        if(level != 1){
            Assert.fail("Wrong level loaded"+level);
        }

        //pasando nivel 2
        onView(withId(R.id.solutionButton))
                .perform(click());
        onView(withId(R.id.game3))
                .perform(click());

        level = activity.levelViews.getCurrentLevel();
        if(activity.levelViews.getCurrentLevel() != 2){
            Assert.fail("Wrong level loaded");
        }

        //pasando nivel 3
        onView(withId(R.id.solutionButton))
                .perform(click());
        onView(withId(R.id.ent2))
                .perform(click())
                .check(matches((isEnabled())));
        onView(withId(R.id.ent3))
                .perform(click())
                .check(matches((isEnabled())));
        onView(withId(R.id.solutionButton))
                .perform(click());
        onView(withId(R.id.game3))
                .perform(click());

        level = activity.levelViews.getCurrentLevel();
        if(level != 3){
            Assert.fail("Wrong level loaded"+level);
        }

        //pasando nivel 4
        onView(withId(R.id.ent2))
                .perform(click())
                .check(matches((isEnabled())));
        onView(withId(R.id.ent3))
                .perform(click())
                .check(matches((isEnabled())));
        onView(withId(R.id.solutionButton))
                .perform(click());
        onView(withId(R.id.game3))
                .perform(click());
    }
}
