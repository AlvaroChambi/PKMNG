package es.developer.achambi.pkmng.modules.search.ability.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;

public class SearchAbilityActivity extends BaseActivity{
    public static Intent getStartIntent( Context context ) {
        Intent intent = new Intent( context, SearchAbilityActivity.class );
        return intent;
    }

    @Override
    public int getScreenTitle() {
        return R.string.search_ability_activity_title;
    }

    @Override
    public BaseFragment getFragment(Bundle args) {
        return SearchAbilityFragment.newInstance( args );
    }
}
