package es.developer.achambi.pkmng.modules.calculator.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseActivity;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class DamageCalculatorActivity extends BaseActivity{

    public static Intent getStartIntent( Context context, PokemonConfig pokemonConfig ) {
        Intent intent = new Intent( context, DamageCalculatorActivity.class );
        Bundle args = DamageCalculatorFragment.getFragmentArgs( pokemonConfig );
        intent.putExtra( BASE_ARGUMENTS, args );

        return intent;
    }

    @Override
    public int getScreenTitle() {
        return R.string.damage_calculator_activity_title;
    }

    @Override
    public BaseFragment getFragment( Bundle args ) {
        return DamageCalculatorFragment.newInstance(args);
    }
}
