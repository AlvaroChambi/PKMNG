package es.developer.achambi.pkmng.modules.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class DamageCalculatorFragment extends BaseFragment {
    private static final String CONFIGURATION_ARGUMENT_KEY = "CONFIGURATION_ARGUMENT_KEY";

    public static DamageCalculatorFragment newInstance( Bundle args ) {
        DamageCalculatorFragment fragment = new DamageCalculatorFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static Bundle getFragmentArgs( PokemonConfig config ) {
        Bundle args = new Bundle();
        args.putParcelable(CONFIGURATION_ARGUMENT_KEY, config);

        return args;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.damage_calculator_fragment_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {

    }
}
