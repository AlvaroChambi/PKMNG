package es.developer.achambi.pkmng;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import es.developer.achambi.pkmng.modules.overview.screen.OverviewActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public abstract class BaseAutomationTest {
    @Rule
    public ActivityTestRule<OverviewActivity> customActivityTestRule =
            new ActivityTestRule<>(OverviewActivity.class);

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
