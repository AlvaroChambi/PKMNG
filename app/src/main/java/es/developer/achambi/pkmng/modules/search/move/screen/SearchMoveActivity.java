package es.developer.achambi.pkmng.modules.search.move.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class SearchMoveActivity extends BaseActivity{

    public static final Intent getStartIntent( Context context, Move currentMove ) {
        Intent intent = new Intent( context, SearchMoveActivity.class );
        Bundle args = SearchMoveFragment.getFragmentParams( currentMove );
        intent.putExtra( BASE_ARGUMENTS, args );
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
