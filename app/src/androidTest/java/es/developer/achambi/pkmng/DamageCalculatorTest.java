package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
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
        onView(withText(R.string.damage_calculator_empty_opponent_text)).check(matches(isDisplayed()));
        onView(withId(R.id.left_pokemon_image_view)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(allOf( withId(R.id.left_pokemon_configuration_name), withText("") )).check(
                matches(isDisplayed()));
        onView(withText(R.string.damage_calculator_empty_opponent_text)).check(matches(isDisplayed()));
    }

    @Test
    public void testRightPokemonChange() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(allOf( withId(R.id.right_pokemon_configuration_name), withText("")))
                .check(matches(isDisplayed()));
        onView(withText(R.string.damage_calculator_empty_opponent_text)).check(matches(isDisplayed()));
        onView(withId(R.id.right_pokemon_image_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(allOf( withId(R.id.right_pokemon_configuration_name), withText("Special") ))
                .check(matches(isDisplayed()));

        onView(withText(R.string.damage_calculator_damage_result_title))
                .check(matches(isDisplayed()));
        onView( allOf( isDescendantOfA(withId(R.id.move_0_damage_result_view)),
                withText("Thunderbolt") ) ).check(matches(isDisplayed()));
        onView( allOf( isDescendantOfA(withId(R.id.move_1_damage_result_view)),
                withText("Grass knot") ) ).check(matches(isDisplayed()));
        onView( allOf( isDescendantOfA(withId(R.id.move_2_damage_result_view)),
                withText("Signal Beam") ) ).check(matches(isDisplayed()));
        onView( allOf( isDescendantOfA(withId(R.id.move_3_damage_result_view)),
                withText("Hidden power") ) ).check(matches(isDisplayed()));
    }

    @Test
    public void testMove0Changed() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(allOf( withId(R.id.right_pokemon_configuration_name), withText("")))
                .check(matches(isDisplayed()));
        onView(withText(R.string.damage_calculator_empty_opponent_text)).check(matches(isDisplayed()));

        onView(withId(R.id.right_pokemon_image_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(  allOf( isDescendantOfA(withId(R.id.move_1_damage_result_view)),
                withId(R.id.move_damage_empty_view) ) ).check(matches(isDisplayed()));

        onView(withId(R.id.move_0_damage_result_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView( allOf( isDescendantOfA(withId(R.id.move_0_damage_result_view)),
                withText("Earthquake") ) ).check(matches(isDisplayed()));
    }

    @Test
    public void testMove1Changed() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(allOf( withId(R.id.right_pokemon_configuration_name), withText("")))
                .check(matches(isDisplayed()));
        onView(withText(R.string.damage_calculator_empty_opponent_text)).check(matches(isDisplayed()));

        onView(withId(R.id.right_pokemon_image_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(  allOf( isDescendantOfA(withId(R.id.move_1_damage_result_view)),
                withId(R.id.move_damage_empty_view) ) ).check(matches(isDisplayed()));

        onView(withId(R.id.move_1_damage_result_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView( allOf( isDescendantOfA(withId(R.id.move_1_damage_result_view)),
                withText("Earthquake") ) ).check(matches(isDisplayed()));
    }

    @Test
    public void testMove2Changed() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(allOf( withId(R.id.right_pokemon_configuration_name), withText("")))
                .check(matches(isDisplayed()));
        onView(withText(R.string.damage_calculator_empty_opponent_text)).check(matches(isDisplayed()));

        onView(withId(R.id.right_pokemon_image_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(  allOf( isDescendantOfA(withId(R.id.move_2_damage_result_view)),
                withId(R.id.move_damage_empty_view) ) ).check(matches(isDisplayed()));

        onView(withId(R.id.move_2_damage_result_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView( allOf( isDescendantOfA(withId(R.id.move_2_damage_result_view)),
                withText("Earthquake") ) ).check(matches(isDisplayed()));
    }

    @Test
    public void testMove3Changed() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(allOf( withId(R.id.right_pokemon_configuration_name), withText("")))
                .check(matches(isDisplayed()));
        onView(withText(R.string.damage_calculator_empty_opponent_text)).check(matches(isDisplayed()));

        onView(withId(R.id.right_pokemon_image_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(  allOf( isDescendantOfA(withId(R.id.move_3_damage_result_view)),
                withId(R.id.move_damage_empty_view) ) ).check(matches(isDisplayed()));

        onView(withId(R.id.move_3_damage_result_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView( allOf( isDescendantOfA(withId(R.id.move_3_damage_result_view)),
                withText("Earthquake") ) ).check(matches(isDisplayed()));
    }
}
