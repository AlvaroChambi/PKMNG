package es.developer.achambi.pkmng.modules.search.ability.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public class SearchAbilityActivity extends BaseActivity{
    public static Intent getStartIntent( Context context, Ability currentAbility ) {
        Intent intent = new Intent( context, SearchAbilityActivity.class );
        Bundle args = SearchAbilityFragment.getFragmentParams( currentAbility );
        intent.putExtra( BASE_ARGUMENTS, args );
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
