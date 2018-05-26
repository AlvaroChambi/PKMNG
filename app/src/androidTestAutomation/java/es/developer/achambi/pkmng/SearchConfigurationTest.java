package es.developer.achambi.pkmng;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.BeforeClass;
import org.junit.Test;

import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.pkmng.modules.ConfigurationDataAssembler;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.configuration.data.MockConfigurationDataAccess;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class SearchConfigurationTest extends BaseAutomationTest {
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
    public void searchConfigurationQueryTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_damage_calculator_action_button)).perform(click());
        onView(withId(R.id.left_pokemon_image_view)).perform(click());

        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( allOf( withId(R.id.pokemon_details_dialog_title),
                withText("Filled") ) ).check(matches( isDisplayed() ));
        Espresso.pressBack();
        onView(withId(R.id.overview_search_action)).perform(click());
        onView(withId(R.id.search_src_text)).perform( clearText(),
                typeText("Special"),
                pressImeActionButton() );
        delay(500);// don't know why :(
        onView( allOf( withId(R.id.pokemon_config_name_text), withText("Special") ) )
                .check(matches(isDisplayed()) );
        Espresso.closeSoftKeyboard();
        Espresso.pressBack();
        Espresso.pressBack();

        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,scrollTo() ) );
        onView( withId(R.id.base_search_recycler_view) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0,click() ) );
        onView( allOf( withId(R.id.pokemon_details_dialog_title),
                withText("Filled") ) ).check(matches( isDisplayed() ));
        Espresso.pressBack();
    }
}
