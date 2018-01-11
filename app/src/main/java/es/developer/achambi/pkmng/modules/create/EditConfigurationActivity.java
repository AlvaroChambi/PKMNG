package es.developer.achambi.pkmng.modules.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.create.view.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class EditConfigurationActivity extends BaseActivity {

    public static Intent getStartIntent( Context context, PokemonConfig pokemonConfig ) {
        Intent intent = new Intent( context, EditConfigurationActivity.class );
        intent.putExtra( BASE_ARGUMENTS, ConfigurationFragment.getFragmentArgs( pokemonConfig ) );

        return intent;
    }

    @Override
    public int getScreenTitle() {
        return R.string.edit_configuration_activity_title;
    }

    @Override
    public BaseFragment getFragment(Bundle args) {
        return ConfigurationFragment.newInstance(args);
    }
}
