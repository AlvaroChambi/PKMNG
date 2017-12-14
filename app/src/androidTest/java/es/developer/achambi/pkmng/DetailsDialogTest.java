package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class DetailsDialogTest extends BaseAutomationTest {

    @Test
    public void showDetailsDialogTest() {
        onView( withId(R.id.overview_recycler_view) )
            .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        onView(withId(R.id.pokemon_name_text)).check(matches(isDisplayed()));
        onView(withId(R.id.pokemon_type_text)).check(matches(isDisplayed()));
        onView(withId(R.id.pokemon_total_base_stats)).check(matches(isDisplayed()));
    }

    @Test
    public void showConfigurationDetailsDialogTest() {
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()));
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.pokemon_name_text)).check(matches(isDisplayed()));
        onView(withId(R.id.pokemon_type_text)).check(matches(isDisplayed()));

        onView(withId(R.id.configuration_details_move_0)).check(matches(isDisplayed()));
        onView(withId(R.id.configuration_details_move_1)).check(matches(isDisplayed()));
        onView(withId(R.id.configuration_details_move_2)).check(matches(isDisplayed()));
        onView(withId(R.id.configuration_details_move_3)).check(matches(isDisplayed()));
    }
}
