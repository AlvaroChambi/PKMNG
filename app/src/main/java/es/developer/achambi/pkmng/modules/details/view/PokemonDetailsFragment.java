package es.developer.achambi.pkmng.modules.details.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseDialogFragment;
import es.developer.achambi.pkmng.core.ui.screen.TypeView;
import es.developer.achambi.pkmng.core.utils.GlideApp;
import es.developer.achambi.pkmng.modules.create.screen.CreateConfigurationActivity;
import es.developer.achambi.pkmng.modules.create.screen.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.presentation.PokemonPresentation;

public class PokemonDetailsFragment extends BaseDialogFragment implements View.OnClickListener {
    private static final String POKEMON_ARGUMENT_KEY = "POKEMON_ARGUMENT_KEY";
    private static final String USE_CONTEXT_ARGUMENT_KEY = "USE_CONTEXT_ARGUMENT_KEY";
    private static final int CREATE_CONFIGURATION_REQUEST_CODE = 101;

    private Pokemon pokemon;
    private PokemonPresentation pokemonPresentation;

    public static PokemonDetailsFragment newInstance( Pokemon pokemon,
                                                      DetailsUseContext useContext ) {
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
            pokemonPresentation = PokemonPresentation.Builder
                    .buildPresentation(getActivity(), pokemon);
        }

        populateView(view);
        Button createConfigButton = view.findViewById(R.id.details_create_config_action_button);
        Button choosePokemonButton = view.findViewById(R.id.details_choose_pokemon_action_button);

        actionButtonsSetup( createConfigButton, choosePokemonButton,
                DetailsUseContext
                .values()[getArguments().getInt(USE_CONTEXT_ARGUMENT_KEY)] );
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.details_create_config_action_button:
                Intent intent = CreateConfigurationActivity.getStartIntent( getActivity(), pokemon );
                startActivityForResult( intent, CREATE_CONFIGURATION_REQUEST_CODE );
                break;
            case R.id.details_choose_pokemon_action_button:
                Intent dataIntent = getActivity().getIntent();
                dataIntent.putExtra(ConfigurationFragment.POKEMON_ACTIVITY_RESULT_DATA_KEY,
                        pokemon );
                getActivity().setResult(Activity.RESULT_OK, dataIntent);
                getActivity().finish();
                dismiss();
                break;
        }
    }

    private void actionButtonsSetup( Button createConfigButton, Button choosePokemonButton,
             DetailsUseContext useContext ) {
        switch ( useContext ) {
            case SELECT_CONTEXT:
                createConfigButton.setVisibility(View.VISIBLE);
                choosePokemonButton.setVisibility(View.GONE);
                createConfigButton.setOnClickListener(this);
                break;
            case REPLACE_CONTEXT:
                choosePokemonButton.setVisibility(View.VISIBLE);
                createConfigButton.setVisibility(View.GONE);
                choosePokemonButton.setOnClickListener(this);
                break;
        }
    }

    private void populateView(View rootView) {
        ImageView pokemonIcon = rootView.findViewById(R.id.pokemon_image_view);
        TextView pokemonName = rootView.findViewById(R.id.pokemon_name_text);
        TypeView pokemonType = rootView.findViewById(R.id.pokemon_type_text);
        TextView baseStats = rootView.findViewById(R.id.pokemon_total_base_stats);

        TextView pokemonHP = rootView.findViewById(R.id.pokemon_hp_text);
        TextView pokemonAttack = rootView.findViewById(R.id.pokemon_atk_text);
        TextView pokemonDefense = rootView.findViewById(R.id.pokemon_def_text);
        TextView pokemonSpAttack = rootView.findViewById(R.id.pokemon_spa_text);
        TextView pokemonSpDefense = rootView.findViewById(R.id.pokemon_spd_text);
        TextView pokemonSpeed = rootView.findViewById(R.id.pokemon_speed_text);
        TextView pokemonLevel = rootView.findViewById(R.id.pokemon_level_text);

        pokemonName.setText(pokemonPresentation.name);
        pokemonType.setType(pokemonPresentation.type);
        baseStats.setText(pokemonPresentation.totalStats);
        pokemonHP.setText(pokemonPresentation.stats.hp);
        pokemonAttack.setText(pokemonPresentation.stats.attack);
        pokemonDefense.setText(pokemonPresentation.stats.defense);
        pokemonSpAttack.setText(pokemonPresentation.stats.spAttack);
        pokemonSpDefense.setText(pokemonPresentation.stats.spDefense);
        pokemonSpeed.setText(pokemonPresentation.stats.speed);
        GlideApp.with(this)
                .load(pokemonPresentation.image)
                .placeholder(pokemonPresentation.defaultImage)
                .into(pokemonIcon);
        pokemonLevel.setText(pokemonPresentation.level);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTargetFragment().onActivityResult( getTargetRequestCode(), resultCode, data );
        dismiss();
    }
}
