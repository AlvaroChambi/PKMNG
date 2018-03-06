package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class EditConfigurationTest extends BaseAutomationTest {

    @Test
    public void configurationPopulationEmptyValues() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView(withId(R.id.configuration_ability_empty_state)).check(matches(isDisplayed()));
        onView(withId(R.id.configuration_nature_empty_state)).check(matches(isDisplayed()));
        onView(withId(R.id.configuration_item_empty_state)).check(matches(isDisplayed()));
        onView( allOf( withId(R.id.move_view_empty_state_image),
                isDescendantOfA(withId(R.id.configuration_move_0_frame)) ) )
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView( allOf( withId(R.id.move_view_empty_state_image),
                isDescendantOfA(withId(R.id.configuration_move_1_frame)) ) )
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView( allOf( withId(R.id.move_view_empty_state_image),
                isDescendantOfA(withId(R.id.configuration_move_2_frame)) ) )
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView( allOf( withId(R.id.move_view_empty_state_image),
                isDescendantOfA(withId(R.id.configuration_move_3_frame)) ) )
                .perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @Test
    public void configurationPopulationFilledValues() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());
        onView(withText("eviolite")).check(matches(isDisplayed()));
        onView(withText("magic guard")).check(matches(isDisplayed()));
        onView(withText("modest")).check(matches(isDisplayed()));

        onView(withText("Hidden power"))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withText("Thunderbolt"))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withText("Signal Beam"))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withText("Grass knot"))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @Test
    public void configurationSaveValuesChanged() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_edit_text )).perform( clearText(),
                typeText( "Test" ) );
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView( withText("Test") ).check(matches(isDisplayed()));
    }

    @Test
    public void configurationPokemonTypeDetail() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());
        onView( withId( R.id.pokemon_type_text ) ).perform( click() );

        onView( withId(R.id.type_quick_detail_top_text) ).inRoot( isPlatformPopup() )
                .check( matches( isDisplayed() ) );
        onView( withId(R.id.type_quick_details_bottom_text) ).inRoot( isPlatformPopup() )
                .check( matches( isDisplayed() ) );
    }
}
