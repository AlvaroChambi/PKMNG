package es.developer.achambi.pkmng.modules.overview.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;

public class SearchActivity extends BaseActivity {
    public static Intent getStartIntent( Context context, SearchFilter searchFilter,
                                         Pokemon currentPokemon ) {
        Intent intent = new Intent( context, SearchActivity.class );
        Bundle args = OverviewFragment.getFragmentArgs( searchFilter,
                OverviewFragment.UseContext.REPLACE_SEARCH_CONTEXT, currentPokemon );

        intent.putExtra(BASE_ARGUMENTS, args);
        return intent;
    }

    public static Intent getStartIntent( Context context, SearchFilter searchFilter,
                                         PokemonConfig pokemonConfig ) {
        Intent intent = new Intent( context, SearchActivity.class );
        Bundle args = OverviewFragment.getFragmentArgs( searchFilter,
                OverviewFragment.UseContext.REPLACE_SEARCH_CONTEXT, pokemonConfig );

        intent.putExtra(BASE_ARGUMENTS, args);
        return intent;
    }

    @Override
    public BaseFragment getFragment(Bundle args ) {
        return OverviewFragment.newInstance(args);
    }

    @Override
    public int getScreenTitle() {
        return R.string.search_activity_title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
    }
}
