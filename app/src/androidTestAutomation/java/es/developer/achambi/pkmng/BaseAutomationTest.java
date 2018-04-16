package es.developer.achambi.pkmng;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.pkmng.idling.ExecutorIdlingResource;
import es.developer.achambi.pkmng.modules.overview.screen.OverviewActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public abstract class BaseAutomationTest {
    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<OverviewActivity> customActivityTestRule =
            new ActivityTestRule<>(OverviewActivity.class);

    @Before
    public void setup() {
        idlingResource = new ExecutorIdlingResource(AppWiring.executor);
        Espresso.registerIdlingResources( idlingResource );
    }

    @After
    public void tearDown() {
        Espresso.unregisterIdlingResources( idlingResource );
    }

    /**
     * Thread sleep in milliseconds
     */
    public void delay( long delay ) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
