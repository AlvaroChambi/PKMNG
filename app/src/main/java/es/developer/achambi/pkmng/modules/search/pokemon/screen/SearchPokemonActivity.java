package es.developer.achambi.pkmng.modules.search.pokemon.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.coreframework.ui.BaseActivity;
import es.developer.achambi.coreframework.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public class SearchPokemonActivity extends BaseActivity {
    @Override
    public int getScreenTitle() {
        return R.string.search_pokemon_activity_title;
    }

    @Override
    public BaseFragment getFragment(Bundle args) {
        return SearchPokemonFragment.newInstance( args );
    }

    public static Intent getStartIntent( Context context, Pokemon currentPokemon ) {
        Intent intent = new Intent( context, SearchPokemonActivity.class );
        Bundle args = SearchPokemonFragment.getFragmentArgs( currentPokemon );

        intent.putExtra(BASE_ARGUMENTS, args);
        return intent;
    }
}
