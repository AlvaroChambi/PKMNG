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

public class CreateConfigurationTest extends BaseAutomationTest {
    @Test
    public void changeCurrentPokemonTest() {
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, scrollTo()));
        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        onView(withId(R.id.details_create_config_action_button)).perform(click());
        onView(withId(R.id.pokemon_image_view)).perform(click());

        onView( withId(R.id.overview_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_choose_pokemon_action_button)).perform(click());

        onView( withText("Pikachu") ).check(matches(isDisplayed()));
    }
}