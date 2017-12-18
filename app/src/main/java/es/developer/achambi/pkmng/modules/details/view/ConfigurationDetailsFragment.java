package es.developer.achambi.pkmng.modules.details.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseDialogFragment;
import es.developer.achambi.pkmng.modules.calculator.DamageCalculatorActivity;
import es.developer.achambi.pkmng.modules.create.EditConfigurationActivity;
import es.developer.achambi.pkmng.modules.details.databuilder.ConfigurationDetailsDataBuilder;
import es.developer.achambi.pkmng.modules.details.view.representation.DetailsConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class ConfigurationDetailsFragment extends BaseDialogFragment
        implements View.OnClickListener{
    private static final String CONFIGURATION_ARGUMENT_KEY = "CONFIGURATION_ARGUMENT_KEY";
    private PokemonConfig pokemonConfig;
    private DetailsConfigurationRepresentation configurationRepresentation;

    public static ConfigurationDetailsFragment newInstance( PokemonConfig config ) {
        Bundle args = new Bundle();
        args.putParcelable(CONFIGURATION_ARGUMENT_KEY, config);

        ConfigurationDetailsFragment fragment = new ConfigurationDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.configuration_details_fragment_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pokemonConfig = getArguments().getParcelable(CONFIGURATION_ARGUMENT_KEY);
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        if(!isViewRecreated()) {
            ConfigurationDetailsDataBuilder dataBuilder = new ConfigurationDetailsDataBuilder();
            configurationRepresentation =
                    dataBuilder.buildViewRepresentation(getResources(), pokemonConfig);
        }
        
        populateView(view);
        view.findViewById(R.id.details_edit_configuration_action_button).setOnClickListener(this);
        view.findViewById(R.id.details_damage_calculator_action_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_edit_configuration_action_button:
                startActivity(
                        EditConfigurationActivity.getStartIntent(getActivity(), pokemonConfig) );
                dismiss();
                break;
            case R.id.details_damage_calculator_action_button:
                startActivity(
                        DamageCalculatorActivity.getStartIntent(getActivity(), pokemonConfig));
                dismiss();
                break;
        }
    }
    
    private void populateView(View rootView) {
        TextView pokemonName = rootView.findViewById(R.id.pokemon_name_text);
        TextView configurationName = rootView.findViewById(R.id.pokemon_config_name_text);
        TextView pokemonType = rootView.findViewById(R.id.pokemon_type_text);

        TextView item = rootView.findViewById(R.id.pokemon_item_text);
        TextView ability = rootView.findViewById(R.id.pokemon_ability_text);
        TextView nature = rootView.findViewById(R.id.pokemon_nature_text);

        TextView pokemonHP = rootView.findViewById(R.id.pokemon_hp_text);
        TextView pokemonAttack = rootView.findViewById(R.id.pokemon_atk_text);
        TextView pokemonDefense = rootView.findViewById(R.id.pokemon_def_text);
        TextView pokemonSpAttack = rootView.findViewById(R.id.pokemon_spa_text);
        TextView pokemonSpDefense = rootView.findViewById(R.id.pokemon_spd_text);
        TextView pokemonSpeed = rootView.findViewById(R.id.pokemon_speed_text);

        TextView move0 = rootView.findViewById(R.id.configuration_details_move_0);
        TextView move1 = rootView.findViewById(R.id.configuration_details_move_1);
        TextView move2 = rootView.findViewById(R.id.configuration_details_move_2);
        TextView move3 = rootView.findViewById(R.id.configuration_details_move_3);

        pokemonName.setText(configurationRepresentation.pokemonName);
        configurationName.setText(configurationRepresentation.name);
        pokemonType.setText(configurationRepresentation.type);

        item.setText(configurationRepresentation.item);
        ability.setText(configurationRepresentation.ability);
        nature.setText(configurationRepresentation.nature);
   
        pokemonHP.setText(configurationRepresentation.hp);
        pokemonAttack.setText(configurationRepresentation.attack);
        pokemonDefense.setText(configurationRepresentation.defense);
        pokemonSpAttack.setText(configurationRepresentation.spAttack);
        pokemonSpDefense.setText(configurationRepresentation.spDefense);
        pokemonSpeed.setText(configurationRepresentation.speed);

        move0.setText(configurationRepresentation.move0);
        move1.setText(configurationRepresentation.move1);
        move2.setText(configurationRepresentation.move2);
        move3.setText(configurationRepresentation.move3);
    }
}
