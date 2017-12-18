package es.developer.achambi.pkmng.modules.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseRequestFragment;
import es.developer.achambi.pkmng.modules.details.databuilder.PokemonDetailsDataBuilder;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;
import es.developer.achambi.pkmng.modules.overview.view.SearchActivity;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;

import static android.app.Activity.RESULT_OK;

public class CreateConfigurationFragment extends BaseRequestFragment implements View.OnClickListener {
    private static final String POKEMON_ARGUMENT_KEY = "POKEMON_ARGUMENT_KEY";
    public static final String POKEMON_ACTIVITY_RESULT_DATA_KEY = "POKEMON_DATA_KEY";
    private static final int REPLACE_POKEMON_RESULT_CODE = 100;

    private Pokemon pokemon;
    private OverviewPokemonRepresentation pokemonRepresentation;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pokemon = getArguments().getParcelable(POKEMON_ARGUMENT_KEY);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.create_configuration_fragment_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        if(!isViewRecreated()) {
            PokemonDetailsDataBuilder dataBuilder = new PokemonDetailsDataBuilder();
            pokemonRepresentation =
                    dataBuilder.buildViewRepresentation(getResources(), pokemon);
        }

        populateView(view);
        view.findViewById(R.id.pokemon_image_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.pokemon_image_view:
                startActivityForResult(SearchActivity.getStartIntent(
                        getActivity(), SearchFilter.POKEMON_FILTER ), REPLACE_POKEMON_RESULT_CODE );
                break;
        }
    }

    private void populateView(View rootView) {
        TextView pokemonName = rootView.findViewById(R.id.pokemon_name_text);
        TextView pokemonType = rootView.findViewById(R.id.pokemon_type_text);
        TextView baseStats = rootView.findViewById(R.id.pokemon_total_base_stats);

        TextView pokemonHP = rootView.findViewById(R.id.pokemon_hp_text);
        TextView pokemonAttack = rootView.findViewById(R.id.pokemon_atk_text);
        TextView pokemonDefense = rootView.findViewById(R.id.pokemon_def_text);
        TextView pokemonSpAttack = rootView.findViewById(R.id.pokemon_spa_text);
        TextView pokemonSpDefense = rootView.findViewById(R.id.pokemon_spd_text);
        TextView pokemonSpeed = rootView.findViewById(R.id.pokemon_speed_text);

        pokemonName.setText(pokemonRepresentation.name);
        pokemonType.setText(pokemonRepresentation.type);
        baseStats.setText(pokemonRepresentation.totalStats);
        pokemonHP.setText(pokemonRepresentation.hp);
        pokemonAttack.setText(pokemonRepresentation.attack);
        pokemonDefense.setText(pokemonRepresentation.defense);
        pokemonSpAttack.setText(pokemonRepresentation.spAttack);
        pokemonSpDefense.setText(pokemonRepresentation.spDefense);
        pokemonSpeed.setText(pokemonRepresentation.speed);
    }

    @Override
    public void doRequest() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK &&
                requestCode == REPLACE_POKEMON_RESULT_CODE ) {
            pokemon = data.getParcelableExtra( POKEMON_ACTIVITY_RESULT_DATA_KEY );

            PokemonDetailsDataBuilder dataBuilder = new PokemonDetailsDataBuilder();
            pokemonRepresentation =
                    dataBuilder.buildViewRepresentation(getResources(), pokemon);

            populateView(getView());
        }
    }
}
