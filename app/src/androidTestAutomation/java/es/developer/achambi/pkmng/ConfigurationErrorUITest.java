package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.BeforeClass;
import org.junit.Test;

import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.pkmng.modules.ConfigurationDataAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.configuration.data.MockErrorConfigurationDataAccess;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ConfigurationErrorUITest extends BaseAutomationTest {
    @BeforeClass
    public static void beforeClass() {
        ConfigurationDataAssembler mockAssembler = new ConfigurationDataAssembler(){
            @Override
            public IConfigurationDataAccess getConfigurationDataAccess() {
                return new MockErrorConfigurationDataAccess();
            }
        };
        AppWiring.createConfigurationAssembler.setConfigurationDataAssembler( mockAssembler );
        AppWiring.searchConfigurationAssembler.setConfigurationDataAssembler( mockAssembler );
        AppWiring.damageCalculatorAssembler.setDataAssembler( mockAssembler );
    }

    @Test
    public void saveConfigurationFailedTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.details_create_config_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_edit_text ))
                .perform( typeText( "Test" ) );
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText("Failed to create configuration")).check(matches(isDisplayed()));
    }

    @Test
    public void updateConfigurationFailedTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_edit_text )).perform( clearText(),
                typeText( "Test" ) );
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText("Failed to update configuration")).check(matches(isDisplayed()));
    }

    @Test
    public void updateLeftConfigurationFailedTest() {
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

        onView(withText("Failed to update configuration")).check(matches(isDisplayed()));
    }

    @Test
    public void updateRightConfigurationFailedTest() {
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

        onView(withText("Failed to update configuration")).check(matches(isDisplayed()));
    }
}
