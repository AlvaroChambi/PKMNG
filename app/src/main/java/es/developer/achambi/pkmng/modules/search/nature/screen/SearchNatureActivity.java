package es.developer.achambi.pkmng.modules.search.nature.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class SearchNatureActivity extends BaseActivity{
    public static Intent getStartIntent(Context context, Nature currentNature ) {
        Intent intent = new Intent( context, SearchNatureActivity.class );
        Bundle args = SearchNatureFragment.getFragmentArgs( currentNature );
        intent.putExtra( BASE_ARGUMENTS, args );
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
