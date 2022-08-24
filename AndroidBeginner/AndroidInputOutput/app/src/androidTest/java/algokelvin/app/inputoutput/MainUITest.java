package algokelvin.app.inputoutput;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.lang.Thread.sleep;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainUITest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkUI() {
        onView(withId(R.id.title))
                .check(matches(withText("Input Your Name")));
    }

    @Test
    public void addName() throws InterruptedException {
        onView(withId(R.id.edt_input_name))
                .perform(typeText("Kelvin Herwanda Tandrio"), closeSoftKeyboard());
        onView(withId(R.id.btn_login))
                .perform(click());

        sleep(1500);

        // This view is in a different Activity, no need to tell Espresso.
        // Check Your Name
        onView(withId(R.id.txt_name)).check(matches(withText("Kelvin Herwanda Tandrio")));
    }

}
