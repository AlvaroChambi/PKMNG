package es.developer.achambi.pkmng;

import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

import es.developer.achambi.pkmng.viewactions.CustomViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class DamageCalculatorUITest extends BaseAutomationTest {
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

        onView(allOf( withId(R.id.left_pokemon_configuration_name),
                withText(R.string.text_empty_placeholder) ))
                .check(
                matches(isDisplayed()));
        onView(withText(R.string.damage_calculator_empty_opponent_text))
                .check(matches(isDisplayed()));
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

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView( allOf( isDescendantOfA(withId(R.id.move_0_damage_result_view)),
                withText("mega-punch") ) ).check(matches(isDisplayed()));
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

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView( allOf( isDescendantOfA(withId(R.id.move_1_damage_result_view)),
                withText("mega-punch") ) ).check(matches(isDisplayed()));
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

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView( allOf( isDescendantOfA(withId(R.id.move_2_damage_result_view)),
                withText("mega-punch") ) ).check(matches(isDisplayed()));
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

        /*scroll until the view isn't covered by the save button, scroll are performed async, so we
        * have to wait a certain amount of time*/
        onView(withId(R.id.damage_results_frame_view))
                .perform(CustomViewActions.swipeUp(GeneralLocation.BOTTOM_LEFT));
        delay(100);
        onView(withId(R.id.move_3_damage_result_view)).perform(click());

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0, click() ) );

        onView( allOf( isDescendantOfA(withId(R.id.move_3_damage_result_view)),
                withText("mega-punch") ) ).check(matches(isDisplayed()));
    }

    @Test
    public void testAttackDirectionChange() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(withId(R.id.right_pokemon_image_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(withId(R.id.attack_direction_image_view)).perform(click());

        onView(  allOf( isDescendantOfA(withId(R.id.move_0_damage_result_view)),
                withId(R.id.move_damage_empty_view) ) ).check(matches(isDisplayed()));
        onView(  allOf( isDescendantOfA(withId(R.id.move_1_damage_result_view)),
                withId(R.id.move_damage_empty_view) ) ).check(matches(isDisplayed()));
        onView(  allOf( isDescendantOfA(withId(R.id.move_2_damage_result_view)),
                withId(R.id.move_damage_empty_view) ) ).check(matches(isDisplayed()));
        onView(  allOf( isDescendantOfA(withId(R.id.move_3_damage_result_view)),
                withId(R.id.move_damage_empty_view) ) ).check(matches(isDisplayed()));
    }

    @Test
    public void testSaveLeftConfiguration() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());

        onView(withId(R.id.right_pokemon_image_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(withId(R.id.move_0_damage_result_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView(withId(R.id.configuration_floating_save_button_main)).perform(click());
        onView(withId(R.id.configuration_floating_save_button_left)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView( allOf( isDescendantOfA(withId(R.id.configuration_move_0_frame)),
                withText("mega-punch") ) ).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void testSaveRightConfiguration() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());

        onView(withId(R.id.right_pokemon_image_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView( withId(R.id.details_choose_configuration_action_button) ).perform(click());

        onView(withId(R.id.attack_direction_image_view)).perform(click());

        onView(withId(R.id.move_0_damage_result_view)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );

        onView(withId(R.id.configuration_floating_save_button_main)).perform(click());
        onView(withId(R.id.configuration_floating_save_button_right)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 1,click() ) );
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView( allOf( isDescendantOfA(withId(R.id.configuration_move_0_frame)),
                withText("mega-punch") ) ).perform(scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void testMoveTypeDetails() {
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
                withText("mega-punch") ) ).check(matches(isDisplayed()));
        onView( allOf( isDescendantOfA(withId(R.id.move_0_damage_result_view)),
                withId(R.id.move_damage_type_text) ) ).perform(click());

        onView( withId(R.id.type_quick_detail_top_text) ).inRoot( isPlatformPopup() )
                .check( matches( isDisplayed() ) );
        onView( withId(R.id.type_quick_details_bottom_text) ).inRoot( isPlatformPopup() )
                .check( matches( not(isDisplayed()) ) );
    }
}
