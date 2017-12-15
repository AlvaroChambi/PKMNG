package es.developer.achambi.pkmng.modules.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseRequestFragment;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class EditConfigurationFragment extends BaseRequestFragment{
    private static final String CONFIGURATION_ARGUMENT_KEY = "CONFIGURATION_ARGUMENT_KEY";

    public static EditConfigurationFragment newInstance( Bundle args ) {
        EditConfigurationFragment fragment = new EditConfigurationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static Bundle getFragmentArgs( PokemonConfig config ) {
        Bundle args = new Bundle();
        args.putParcelable( CONFIGURATION_ARGUMENT_KEY, config );

        return args;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.edit_configuration_fragment_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void doRequest() {

    }
}
