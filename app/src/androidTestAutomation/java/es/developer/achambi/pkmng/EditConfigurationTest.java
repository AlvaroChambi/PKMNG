package es.developer.achambi.pkmng;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.BeforeClass;
import org.junit.Test;

import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.pkmng.modules.ConfigurationDataAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.configuration.data.MockConfigurationDataAccess;
import es.developer.achambi.pkmng.viewactions.CustomViewActions;
import es.developer.achambi.pkmng.viewactions.ToastMatcher;

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
    @BeforeClass
    public static void beforeClass() {
        ConfigurationDataAssembler mockAssembler = new ConfigurationDataAssembler(){
            @Override
            public IConfigurationDataAccess getConfigurationDataAccess() {
                return new MockConfigurationDataAccess();
            }
        };
        AppWiring.searchConfigurationAssembler.setConfigurationDataAssembler( mockAssembler );
    }

    @Test
    public void configurationPopulationEmptyValues() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        assertEmptyValues();
    }

    @Test
    public void configurationPopulationFilledValues() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());
        assertInitialValues();
    }

    @Test
    public void changeCurrentPokemonTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        assertInitialValues();

        onView(withId(R.id.pokemon_image_view)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
                        CustomViewActions.click( GeneralLocation.TOP_CENTER )));
        onView(withId(R.id.details_choose_pokemon_action_button)).perform(click());

        onView( withText("ivysaur") ).check(matches(isDisplayed()));

        assertEmptyValues();
    }

    @Test
    public void changeCurrentPokemonWithSamePokemon() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        assertInitialValues();

        onView(withId(R.id.pokemon_image_view)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        CustomViewActions.click( GeneralLocation.TOP_CENTER )));
        onView(withId(R.id.details_choose_pokemon_action_button)).perform(click());

        assertInitialValues();
    }

    @Test
    public void configurationSaveItemChanged() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        //change item
        onView(withId(R.id.configuration_item_frame)).perform(click());
        onView(withId(R.id.base_search_header_frame)).check(matches(isDisplayed()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_choose_item_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText(R.string.configuration_updated_toast_message))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void configurationSaveAbilityChanged() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        //change ability
        onView(withId(R.id.configuration_ability_frame)).perform(click());
        onView(withId(R.id.base_search_header_frame)).check(matches(isDisplayed()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_choose_ability_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText(R.string.configuration_updated_toast_message))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void configurationSaveNatureChanged() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        //change nature
        onView(withId(R.id.configuration_nature_frame)).perform(click());
        onView(withId(R.id.base_search_header_frame)).check(matches(isDisplayed()));
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText(R.string.configuration_updated_toast_message))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void configurationSaveMoveChanged() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        //change move
        onView( allOf( withId(R.id.move_view_power_text),
                isDescendantOfA(withId(R.id.configuration_move_0_frame)) ) ).perform(scrollTo(),
                click());
        onView(withId(R.id.base_search_header_frame)).check(matches(isDisplayed()));
        onView(withId(R.id.base_search_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText(R.string.configuration_updated_toast_message))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void configurationSaveNameChanged() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());

        onView(withId(R.id.create_configuration_dialog_edit_text))
                .perform(clearText(), typeText("Updated"));

        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText(R.string.configuration_updated_toast_message))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void configurationSaveValuesNotChanged() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView(withId( R.id.configuration_floating_save_button_middle)).perform(click());
        onView(withId( R.id.create_configuration_dialog_save_button )).perform(click());

        onView(withText(R.string.configuration_not_changed_toast_message))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
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

    @Test
    public void configurationHpStatValueUpdate() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView( allOf( withId(R.id.ev_stat_value_text),
                isDescendantOfA( withId( R.id.configuration_hp_ev_stat_bar ) ) ) )
                .perform( clearText(), typeText( "252" ) );
        Espresso.pressBack();

        onView( allOf( withId(R.id.ev_stat_value_text),
                withText("252"),
                isDescendantOfA( withId( R.id.configuration_hp_ev_stat_bar ) ) ) )
                .check(matches(isDisplayed()));
        onView( allOf( withId(R.id.ev_stat_total_preview_value_text),
                withText("142"),
                isDescendantOfA( withId( R.id.configuration_hp_ev_stat_bar ) ) ) )
                .check(matches( isDisplayed() ));
    }

    @Test
    public void configurationStatValueUpdatePositiveNature() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView(withId(R.id.configuration_nature_empty_state)).perform(click());
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        onView( allOf( withId(R.id.ev_stat_value_text),
                isDescendantOfA( withId( R.id.configuration_attack_ev_stat_bar ) ) ) )
                .perform( clearText(), typeText( "252" ) );
        Espresso.pressBack();

        onView( allOf( withId(R.id.ev_stat_value_text),
                withText("252"),
                isDescendantOfA( withId( R.id.configuration_attack_ev_stat_bar ) ) ) )
                .check(matches(isDisplayed()));
        onView( allOf( withId(R.id.ev_stat_total_preview_value_text),
                withText("117"),
                isDescendantOfA( withId( R.id.configuration_attack_ev_stat_bar ) ) ) )
                .check(matches( isDisplayed() ));
    }

    @Test
    public void configurationOutOfBounds() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView( allOf( withId(R.id.ev_stat_value_text),
                isDescendantOfA( withId( R.id.configuration_hp_ev_stat_bar ) ) ) )
                .perform( clearText(), typeText( "252" ) );
        Espresso.pressBack();
        onView( allOf( withId(R.id.ev_stat_value_text),
                isDescendantOfA( withId( R.id.configuration_attack_ev_stat_bar ) ) ) )
                .perform( clearText(), typeText( "252" ) );
        Espresso.pressBack();
        onView( allOf( withId(R.id.ev_stat_value_text),
                isDescendantOfA( withId( R.id.configuration_defense_ev_stat_bar ) ) ) )
                .perform( clearText(), typeText( "66" ) );
        Espresso.pressBack();


        onView( allOf( withId(R.id.ev_stat_value_text),
                withText("6"),
                isDescendantOfA( withId( R.id.configuration_defense_ev_stat_bar ) ) ) )
                .check(matches(isDisplayed()));
        onView( allOf( withId(R.id.ev_stat_total_preview_value_text),
                withText("61"),
                isDescendantOfA( withId( R.id.configuration_defense_ev_stat_bar ) ) ) )
                .check(matches( isDisplayed() ));
    }

    private void assertInitialValues() {
        onView(withText("eviolite")).perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withText("magic guard")).perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withText("modest")).perform(scrollTo())
                .check(matches(isDisplayed()));

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

    private void assertEmptyValues() {
        onView(withId(R.id.configuration_ability_empty_state)).perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withId(R.id.configuration_nature_empty_state)).perform(scrollTo())
                .check(matches(isDisplayed()));
        onView(withId(R.id.configuration_item_empty_state)).perform(scrollTo())
                .check(matches(isDisplayed()));
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
}
