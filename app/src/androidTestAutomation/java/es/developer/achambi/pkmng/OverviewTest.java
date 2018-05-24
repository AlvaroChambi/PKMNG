package es.developer.achambi.pkmng;

import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;

import es.developer.achambi.pkmng.perftesting.common.PerfTest;
import es.developer.achambi.pkmng.perftesting.testrules.EnablePostTestDumpsys;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@PerfTest
public class OverviewTest extends BaseAutomationTest {
    @Rule
    public EnablePostTestDumpsys mEnablePostTestDumpsys = new EnablePostTestDumpsys();

    @Test
    @PerfTest
    public void test() {
        onView(withId(R.id.base_search_recycler_view)).check(matches(isDisplayed()));
        RecyclerView recyclerView = customActivityTestRule.getActivity()
                .findViewById(R.id.base_search_recycler_view);

        RecyclerView.SmoothScroller smoothScroller =
                new LinearSmoothScroller(customActivityTestRule.getActivity());
        smoothScroller.setTargetPosition(recyclerView.getAdapter().getItemCount());

        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
        while (smoothScroller.isRunning()) {
            delay(300);
        }
    }
}
