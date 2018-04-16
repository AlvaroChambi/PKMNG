package es.developer.achambi.pkmng.modules.search.item.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class SearchItemActivity extends BaseActivity{

    public static Intent getStartIntent( Context context, Item currentItem ) {
        Intent intent = new Intent( context, SearchItemActivity.class );
        Bundle args = SearchItemFragment.getFragmentArgs( currentItem );
        intent.putExtra( BASE_ARGUMENTS, args );
        return intent;
    }

    @Override
    public int getScreenTitle() {
        return R.string.search_item_activity_title;
    }

    @Override
    public BaseFragment getFragment(Bundle args) {
        return SearchItemFragment.newInstance( args );
    }
}
