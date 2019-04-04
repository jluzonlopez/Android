package jluzon.mov.urjc.xorapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
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
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void passLevel_sameActivity() {
        LoginActivity activity = mActivityRule.getActivity();
        // Type text and then press the button.
        onView(withId(R.id.name)).perform(typeText("juan"));

        /*onView(withId(R.id.ent0))
                .perform(click());
        onView(withId(R.id.ent1))
                .perform(click());*/
        /*onView(withId(R.id.solutionButton))
                .perform(click());*/

    }
}
