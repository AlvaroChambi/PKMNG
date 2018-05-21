package es.developer.achambi.pkmng;

import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.pkmng.modules.ConfigurationDataAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.configuration.data.MockConfigurationDataAccess;
import es.developer.achambi.pkmng.viewactions.CustomViewActions;
import es.developer.achambi.pkmng.viewactions.ToastMatcher;

import static android.support.test.espresso.Espresso.onView;
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
import static org.hamcrest.Matchers.not;

public class CreateConfigurationTest extends BaseAutomationTest {
    @BeforeClass
    public static void beforeClass() {
        ConfigurationDataAssembler mockAssembler = new ConfigurationDataAssembler(){
            @Override
            public IConfigurationDataAccess getConfigurationDataAccess() {
                return new MockConfigurationDataAccess();
            }
        };
        AppWiring.createConfigurationAssembler.setConfigurationDataAssembler( mockAssembler );
        AppWiring.searchConfigurationAssembler.setConfigurationDataAssembler( mockAssembler );
    }

    @Test
    public void changeCurrentPokemonTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        onView(withId(R.id.details_create_config_action_button)).perform(click());
        onView(withId(R.id.pokemon_image_view)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        CustomViewActions.click( GeneralLocation.TOP_CENTER )));
        onView(withId(R.id.details_choose_pokemon_action_button)).perform(click());

        onView( withText("bulbasaur") ).check(matches(isDisplayed()));
        onView( withText("level 50") ).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrentItemTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_item_empty_state)).perform(click());

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_choose_item_action_button));

        onView(withText("master-ball")).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrentAbilityTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_ability_empty_state)).perform(click());

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_choose_ability_action_button));

        onView(withId(R.id.details_choose_ability_action_button));
        onView(withText("blaze")).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrentNatureTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId(R.id.configuration_nature_empty_state)).perform(click());

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText("hardy")).check(matches(isDisplayed()));
    }

    @Test
    public void changeCurrentMove0Test() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView( allOf( withId(R.id.move_view_empty_state_image),
                isDescendantOfA(withId(R.id.configuration_move_0_frame)) ) ).perform(scrollTo(),
                click());

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView( allOf(
                isDescendantOfA(withId(R.id.configuration_move_0_frame)), withText("mega-punch") ) )
                .check( matches(isDisplayed()) );
    }

    @Test
    public void changeCurrentMove1Test() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView( allOf( withId(R.id.move_view_empty_state_image),
                isDescendantOfA(withId(R.id.configuration_move_1_frame)) ) ).perform( scrollTo(),
                click());

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView( allOf(
                isDescendantOfA(withId(R.id.configuration_move_1_frame)), withText("mega-punch") ) )
                .check( matches(isDisplayed()) );
    }

    @Test
    public void changeCurrentMove2Test() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView( allOf( withId(R.id.move_view_empty_state_image),
                isDescendantOfA(withId(R.id.configuration_move_2_frame)) ) ).perform(scrollTo(),
                click());

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView( allOf(
                isDescendantOfA(withId(R.id.configuration_move_2_frame)), withText("mega-punch") ) )
                .check( matches(isDisplayed()) );
    }

    @Test
    public void changeCurrentMove3Test() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView( allOf( withId(R.id.move_view_empty_state_image),
                isDescendantOfA(withId(R.id.configuration_move_3_frame)) ) ).perform(scrollTo(),
                click());

        onView(withId(R.id.base_search_header_frame)).check(matches(not(isDisplayed())));
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView( allOf(
                isDescendantOfA(withId(R.id.configuration_move_3_frame)), withText("mega-punch") ) )
                .check( matches(isDisplayed()) );
    }

    @Test
    public void saveConfigurationTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_edit_text ))
                .perform( typeText( "Test" ) );
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText(R.string.configuration_created_toast_message))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView( withText("Test") ).check(matches(isDisplayed()));
    }

    @Test
    public void configurationPokemonTypeDetail() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(3,
                        CustomViewActions.click(GeneralLocation.TOP_CENTER)));
        onView(withId(R.id.details_create_config_action_button)).perform(click());
        onView( withId( R.id.pokemon_type_text ) ).perform( click() );

        onView( withId(R.id.type_quick_detail_top_text) ).inRoot( isPlatformPopup() )
                .check( matches( isDisplayed() ) );
        onView( withId(R.id.type_quick_details_bottom_text) ).inRoot( isPlatformPopup() )
                .check( matches( isDisplayed() ) );
    }
}
