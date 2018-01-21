package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class DamageCalculatorTest extends BaseAutomationTest {
    @Test
    public void testLeftPokemonChange() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(allOf( withId(R.id.left_pokemon_configuration_name), withText("Special")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.left_pokemon_image_view)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(allOf( withId(R.id.left_pokemon_configuration_name), withText("") )).check(
                matches(isDisplayed()));
    }

    @Test
    public void testRightPokemonChange() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(allOf( withId(R.id.right_pokemon_configuration_name), withText("")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.right_pokemon_image_view)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(allOf( withId(R.id.right_pokemon_configuration_name), withText("Special") ))
                .check(matches(isDisplayed()));
    }
}
