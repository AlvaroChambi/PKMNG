package es.developer.achambi.pkmng.modules.search.nature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;

public class SearchNatureActivity extends BaseActivity{
    public static Intent getStartIntent( Context context ) {
        Intent intent = new Intent( context, SearchNatureActivity.class );
        return intent;
    }

    @Override
    public int getScreenTitle() {
        return R.string.search_nature_activity_title;
    }

    @Override
    public BaseFragment getFragment(Bundle args) {
        return SearchNatureFragment.newInstance( args );
    }
}
