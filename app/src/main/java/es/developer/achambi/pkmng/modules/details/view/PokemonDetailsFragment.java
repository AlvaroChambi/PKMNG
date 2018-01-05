package es.developer.achambi.pkmng.modules.details.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseDialogFragment;
import es.developer.achambi.pkmng.modules.create.view.CreateConfigurationActivity;
import es.developer.achambi.pkmng.modules.create.view.CreateConfigurationFragment;
import es.developer.achambi.pkmng.modules.details.databuilder.PokemonDetailsDataBuilder;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.view.OverviewFragment;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;

public class PokemonDetailsFragment extends BaseDialogFragment implements View.OnClickListener {
    private static final String POKEMON_ARGUMENT_KEY = "POKEMON_ARGUMENT_KEY";
    private static final String USE_CONTEXT_ARGUMENT_KEY = "USE_CONTEXT_ARGUMENT_KEY";

    private Pokemon pokemon;
    private OverviewPokemonRepresentation pokemonRepresentation;

    public static PokemonDetailsFragment newInstance( Pokemon pokemon,
                                                      OverviewFragment.UseContext useContext ) {
        Bundle args = new Bundle();
        args.putParcelable(POKEMON_ARGUMENT_KEY, pokemon);
        args.putInt(USE_CONTEXT_ARGUMENT_KEY, useContext.ordinal());

        PokemonDetailsFragment fragment = new PokemonDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.pokemon_details_fragment_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        pokemon = getArguments().getParcelable(POKEMON_ARGUMENT_KEY);
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        if(!isViewRecreated()) {
            PokemonDetailsDataBuilder dataBuilder = new PokemonDetailsDataBuilder();
            pokemonRepresentation =
                    dataBuilder.buildViewRepresentation(getResources(), pokemon);
        }

        populateView(view);
        Button createConfigButton = view.findViewById(R.id.details_create_config_action_button);
        Button choosePokemonButton = view.findViewById(R.id.details_choose_pokemon_action_button);

        actionButtonsSetup( createConfigButton, choosePokemonButton,
                OverviewFragment.UseContext
                .values()[getArguments().getInt(USE_CONTEXT_ARGUMENT_KEY)] );
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.details_create_config_action_button:
                Intent intent = CreateConfigurationActivity.getStartIntent( getActivity(), pokemon );
                startActivity(intent);
                dismiss();
                break;
            case R.id.details_choose_pokemon_action_button:
                Intent dataIntent = getActivity().getIntent();
                dataIntent.putExtra(CreateConfigurationFragment.POKEMON_ACTIVITY_RESULT_DATA_KEY,
                        pokemon );
                getActivity().setResult(Activity.RESULT_OK, dataIntent);
                getActivity().finish();
                dismiss();
                break;
        }
    }

    private void actionButtonsSetup( Button createConfigButton, Button choosePokemonButton,
             OverviewFragment.UseContext useContext ) {
        switch ( useContext ) {
            case OVERVIEW_SEARCH_CONTEXT:
                createConfigButton.setVisibility(View.VISIBLE);
                choosePokemonButton.setVisibility(View.GONE);
                createConfigButton.setOnClickListener(this);
                break;
            case REPLACE_SEARCH_CONTEXT:
                choosePokemonButton.setVisibility(View.VISIBLE);
                createConfigButton.setVisibility(View.GONE);
                choosePokemonButton.setOnClickListener(this);
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
}
