package es.developer.achambi.pkmng.viewactions;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CoordinatesProvider;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.view.View;

import static android.support.test.espresso.action.ViewActions.actionWithAssertions;

public class CustomViewActions {
    /**
     * The distance of a swipe's start position from the view's edge, in terms of the view's length.
     * We do not start the swipe exactly on the view's edge, but somewhat more inward, since swiping
     * from the exact edge may behave in an unexpected way (e.g. may open a navigation drawer).
     */
    private static final float EDGE_FUZZ_FACTOR = 0.083f;

    /**
     * Returns an action that performs a swipe bottom-to-top across the horizontal center of the view.
     * The swipe doesn't start at the very edge of the view, but has a bit of offset.<br>
     * <br>
     * View constraints:
     * <ul>
     * <li>must be displayed on screen
     * <ul>
     */
    public static ViewAction swipeUp( GeneralLocation startCoordinates ) {
        return actionWithAssertions(new GeneralSwipeAction(Swipe.FAST,
                translate(startCoordinates, 0, -EDGE_FUZZ_FACTOR),
                GeneralLocation.TOP_CENTER, Press.FINGER));
    }

    /**
     * Translates the given coordinates by the given distances. The distances are given in term
     * of the view's size -- 1.0 means to translate by an amount equivalent to the view's length.
     */
    static CoordinatesProvider translate(final CoordinatesProvider coords,
                                         final float dx, final float dy) {
        return new CoordinatesProvider() {
            @Override
            public float[] calculateCoordinates(View view) {
                float xy[] = coords.calculateCoordinates(view);
                xy[0] += dx * view.getWidth();
                xy[1] += dy * view.getHeight();
                return xy;
            }
        };
    }
}
