package com.artion.androiddemos;

import android.os.SystemClock;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.artion.androiddemos.acts.AnimationDemo;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by caijinsheng on 17/1/13.
 */
@RunWith(AndroidJUnit4.class)
public class DemosTest {
    @Rule
    public ActivityTestRule mRule = new ActivityTestRule(Demos.class);

    @Test
    public void testItemClick() {
//        onView(withId(R.id.button1)).perform(click());
//        onView(withId(R.id.button2)).perform(click());
//        onView(withId(R.id.button3)).perform(click());

        onData(allOf(is(instanceOf(String.class)), itemWithName("ImageLockDemo"))).inAdapterView(withId(R.id.listview)).perform(click());

        SystemClock.sleep(1000);
    }

    public static Matcher<Object> itemWithName(final String name) {
        return new BoundedMatcher<Object, String>(String.class) {
            @Override
            protected boolean matchesSafely(String item) {
                return item != null
                        && !TextUtils.isEmpty(item)
                        && item.equals(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("SearchItem has Name: " + name);
            }
        };
    }
}
