package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class SearchMoveTest extends BaseAutomationTest {
    @Test
    public void moveSearchTest() {
        onView( withId(R.id.base_search_recycler_view) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_edit_configuration_action_button)).perform(click());

        onView( withId(R.id.configuration_move_0_frame) )
                .perform(scrollTo(),
                        click());
        onView( allOf( withId( R.id.move_name_text ),
                withText( "Thunderbolt" ),
                isDescendantOfA( withId( R.id.base_search_header_frame ) ) ) )
                .check( matches( isDisplayed() ) );
    }
}
