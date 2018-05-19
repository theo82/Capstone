package theo.tziomakas.news;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import activities.StartActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

 @Rule
 public ActivityTestRule<StartActivity>  mMainActivityTestRule
         = new ActivityTestRule<>(StartActivity.class);


 @Test
 public void checkRegisterButton(){
     onView(withId(R.id.start_reg_button))
             .perform(click());

     onView(withId(R.id.reg_display_name)).perform(clearText());
     onView(withId(R.id.reg_display_name)).perform(typeText("Theo"),closeSoftKeyboard());

     onView(withId(R.id.reg_email)).perform(clearText());
     onView(withId(R.id.reg_email)).perform(typeText("theo@gmail.com"),closeSoftKeyboard());

     onView(withId(R.id.reg_password)).perform(clearText());
     onView(withId(R.id.reg_password)).perform(typeText("larissa"),closeSoftKeyboard());

     onView(withId(R.id.reg_create_btn))
             .perform(click());


 }


 @Test
 public void checkLoginButton(){
  onView(withId(R.id.start_login_button))
          .perform(click());


     onView(withId(R.id.login_email)).perform(clearText());
     onView(withId(R.id.login_email)).perform(typeText("theo@gmail.com"),closeSoftKeyboard());

     onView(withId(R.id.login_password)).perform(clearText());
     onView(withId(R.id.login_password)).perform(typeText("larissa"),closeSoftKeyboard());

     onView(withId(R.id.login_create_btn))
             .perform(click());


 }
}
