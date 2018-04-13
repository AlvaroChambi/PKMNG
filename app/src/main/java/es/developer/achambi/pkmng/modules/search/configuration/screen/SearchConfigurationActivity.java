package es.developer.achambi.pkmng.modules.search.configuration.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class SearchConfigurationActivity extends BaseActivity {
    @Override
    public int getScreenTitle() {
        return R.string.search_configuration_activity_title;
    }

    public static Intent getStartIntent( Context context, PokemonConfig config ) {
        Intent intent = new Intent( context, SearchConfigurationActivity.class );
        Bundle args = SearchConfigurationFragment.getFragmentArgs( config );
        intent.putExtra( BASE_ARGUMENTS, args );
        return intent;
    }

    @Override
    public BaseFragment getFragment(Bundle args) {
        return SearchConfigurationFragment.newInstance( args );
    }
}
