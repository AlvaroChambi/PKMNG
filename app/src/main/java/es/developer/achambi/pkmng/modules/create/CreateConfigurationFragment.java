package es.developer.achambi.pkmng.modules.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseRequestFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public class CreateConfigurationFragment extends BaseRequestFragment{
    private static final String POKEMON_ARGUMENT_KEY = "POKEMON_ARGUMENT_KEY";

    public static CreateConfigurationFragment newInstance( Bundle args ) {
        CreateConfigurationFragment fragment = new CreateConfigurationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static Bundle getFragmentArgs( Pokemon pokemon ) {
        Bundle args = new Bundle();
        args.putParcelable(POKEMON_ARGUMENT_KEY, pokemon);

        return args;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.create_configuration_fragment_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void doRequest() {

    }
}
