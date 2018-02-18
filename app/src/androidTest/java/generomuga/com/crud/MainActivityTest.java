package generomuga.com.crud;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsAnything.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickCreateButton() throws Exception{
        onView(withId(R.id.editTextMessage)).perform(typeText("Test"));

        int i;
        for (i = 0; i < 4;  i++){
            onView(withId(R.id.buttonSend)).perform(click());
        }
    }

    @Test
    public void deleteButton()throws Exception{
        onData(anything()).inAdapterView(withId(R.id.listViewMessage)).onChildView(withId(R.id.buttonDelete)).atPosition(0).perform(click());
    }
}
