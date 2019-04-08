package jluzon.mov.urjc.xorapp;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.base.MainThread;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
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
    public ActivityTestRule<LoginActivity> mLoginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void Login_sameActivity() {
        // Type text and then press the button.

        String playerName = "Jorge";
        onView(withId(R.id.name)).perform(typeText(playerName),closeSoftKeyboard());
        onView(withId(R.id.name)).check(matches(withText(playerName)));
        onView(withId(R.id.start)).perform(click());

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

        //pasando nivel 2
        onView(withId(R.id.solutionButton))
                .perform(click());
        onView(withId(R.id.game3))
                .perform(click());

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

        pressBack();
    }
}
