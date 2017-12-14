package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.pokemon_name_text)).check(matches(isDisplayed()));
        onView(withId(R.id.pokemon_type_text)).check(matches(isDisplayed()));

        onView(withId(R.id.configuration_details_move_0)).check(matches(isDisplayed()));
        onView(withId(R.id.configuration_details_move_1)).check(matches(isDisplayed()));
        onView(withId(R.id.configuration_details_move_2)).check(matches(isDisplayed()));
        onView(withId(R.id.configuration_details_move_3)).check(matches(isDisplayed()));
    }

    @Test
    public void createConfigurationSelectedTest() {
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withText(R.string.create_config_activity_title)).check(matches(isDisplayed()));
    }

    @Test
    public void editConfigurationSelectedTest() {
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView(withText(R.string.edit_configuration_activity_title)).check(matches(isDisplayed()));
    }

    @Test
    public void damageCalculatorSelectedTest() {
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());

        onView(withText(R.string.damage_calculator_activity_title)).check(matches(isDisplayed()));
    }
}
