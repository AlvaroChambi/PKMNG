package es.developer.achambi.pkmng.modules.search.move.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;

public class SearchMoveActivity extends BaseActivity{

    public static final Intent getStartIntent( Context context ) {
        Intent intent = new Intent( context, SearchMoveActivity.class );
        return intent;
    }

    @Override
    public int getScreenTitle() {
        return R.string.search_move_activity_title;
    }

    @Override
    public BaseFragment getFragment(Bundle args) {
        return SearchMoveFragment.newInstance(args);
    }
}
