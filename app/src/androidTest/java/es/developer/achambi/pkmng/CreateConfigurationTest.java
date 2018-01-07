package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class CreateConfigurationTest extends BaseAutomationTest {
    @Test
    public void changeCurrentPokemonTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        onView(withId(R.id.details_create_config_action_button)).perform(click());
        onView(withId(R.id.pokemon_image_view)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_choose_pokemon_action_button)).perform(click());

        onView( withText("Pikachu") ).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrentItemTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        onView(withId(R.id.details_create_config_action_button)).perform(click());
        onView(withId(R.id.configuration_item_frame)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_choose_item_action_button));

        onView(withText("eviolite")).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrentAbilityTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_ability_frame)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_choose_ability_action_button));

        onView(withText("overgrow")).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrentNatureTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_nature_frame)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText("modest")).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrentMove0Test() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_move_0_frame)).perform(click());
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView( allOf(
                isDescendantOfA(withId(R.id.configuration_move_0_frame)), withText("Earthquake") ) )
                .check( matches(isDisplayed()) );
    }

    @Test
    public void changeCurrentMove1Test() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_move_1_frame)).perform(click());
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView( allOf(
                isDescendantOfA(withId(R.id.configuration_move_1_frame)), withText("Earthquake") ) )
                .check( matches(isDisplayed()) );
    }

    @Test
    public void changeCurrentMove2Test() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_move_2_frame)).perform(click());
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView( allOf(
                isDescendantOfA(withId(R.id.configuration_move_2_frame)), withText("Earthquake") ) )
                .check( matches(isDisplayed()) );
    }

    @Test
    public void changeCurrentMove3Test() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_move_3_frame)).perform(click());
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView( allOf(
                isDescendantOfA(withId(R.id.configuration_move_3_frame)), withText("Earthquake") ) )
                .check( matches(isDisplayed()) );
    }

    @Test
    public void saveConfigurationTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button )).perform(click());
        onView(withId( R.id.create_configuration_dialog_edit_text )).perform( typeText( "Test" ) );
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView( withText("Test") ).check(matches(isDisplayed()));
    }
}
