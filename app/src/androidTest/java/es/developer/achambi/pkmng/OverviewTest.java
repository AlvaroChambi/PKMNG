package es.developer.achambi.pkmng;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import es.developer.achambi.pkmng.modules.overview.view.OverviewActivity;
import es.developer.achambi.pkmng.perftesting.TestListener;
import es.developer.achambi.pkmng.perftesting.common.PerfTest;
import es.developer.achambi.pkmng.perftesting.testrules.EnablePostTestDumpsys;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest

@PerfTest
public class OverviewTest {
    @Rule
    public ActivityTestRule<OverviewActivity> customActivityTestRule =
            new ActivityTestRule<>(OverviewActivity.class);

    @Rule
    public EnablePostTestDumpsys mEnablePostTestDumpsys = new EnablePostTestDumpsys();

    @BeforeClass
    public static void setup() {
        TestListener testListener = new TestListener();
        try {
            testListener.testRunStarted(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @PerfTest
    public void test() throws InterruptedException{
        onView(withId(R.id.overview_recycler_view)).check(matches(isDisplayed()));
        RecyclerView recyclerView = (RecyclerView) customActivityTestRule.getActivity()
                .findViewById(R.id.overview_recycler_view);

        RecyclerView.SmoothScroller smoothScroller =
                new LinearSmoothScroller(customActivityTestRule.getActivity());
        smoothScroller.setTargetPosition(recyclerView.getAdapter().getItemCount());

        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
        while (smoothScroller.isRunning()) {
            Thread.sleep(300);
        }
    }

    @AfterClass
    public static void tearDown() {
        TestListener testListener = new TestListener();
        try {
            testListener.testRunFinished(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
