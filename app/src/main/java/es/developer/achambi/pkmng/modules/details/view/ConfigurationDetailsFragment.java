package es.developer.achambi.pkmng.modules.details.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseDialogFragment;
import es.developer.achambi.pkmng.core.ui.view.TypeView;
import es.developer.achambi.pkmng.modules.calculator.view.DamageCalculatorActivity;
import es.developer.achambi.pkmng.modules.calculator.view.DamageCalculatorFragment;
import es.developer.achambi.pkmng.modules.create.EditConfigurationActivity;
import es.developer.achambi.pkmng.modules.details.databuilder.ConfigurationDetailsDataBuilder;
import es.developer.achambi.pkmng.modules.details.view.representation.DetailsConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.view.OverviewFragment;

public class ConfigurationDetailsFragment extends BaseDialogFragment
        implements View.OnClickListener{
    private static final String CONFIGURATION_ARGUMENT_KEY = "CONFIGURATION_ARGUMENT_KEY";
    private static final String USE_CONTEXT_ARGUMENT_KEY = "USE_CONTEXT_ARGUMENT_KEY";
    private static final int UPDATE_CONFIGURATION_REQUEST_CODE = 100;
    private PokemonConfig pokemonConfig;
    private DetailsConfigurationRepresentation configurationRepresentation;

    public static ConfigurationDetailsFragment newInstance(PokemonConfig config,
                                                           OverviewFragment.UseContext useContext ) {
        Bundle args = new Bundle();
        args.putParcelable(CONFIGURATION_ARGUMENT_KEY, config);
        args.putInt( USE_CONTEXT_ARGUMENT_KEY, useContext.ordinal() );
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
                    dataBuilder.buildViewRepresentation(getActivity(), pokemonConfig);
        }
        
        populateView(view);
        view.findViewById(R.id.details_edit_configuration_action_button).setOnClickListener(this);
        view.findViewById(R.id.details_damage_calculator_action_button).setOnClickListener(this);
        view.findViewById(R.id.details_choose_configuration_action_button).setOnClickListener(this);
        actionButtonsSetup( view, OverviewFragment.UseContext
                .values()[getArguments().getInt(USE_CONTEXT_ARGUMENT_KEY)] );
    }

    public void actionButtonsSetup( View rootView, OverviewFragment.UseContext useContext ) {
        switch ( useContext ) {
            case OVERVIEW_SEARCH_CONTEXT:
                rootView.findViewById(R.id.details_edit_configuration_action_button).setVisibility(
                        View.VISIBLE );
                rootView.findViewById(R.id.details_damage_calculator_action_button).setVisibility(
                        View.VISIBLE );
                rootView.findViewById(R.id.details_choose_configuration_action_button).setVisibility(
                        View.GONE );
                break;
            case REPLACE_SEARCH_CONTEXT:
                rootView.findViewById(R.id.details_edit_configuration_action_button).setVisibility(
                        View.GONE );
                rootView.findViewById(R.id.details_damage_calculator_action_button).setVisibility(
                        View.GONE );
                rootView.findViewById(R.id.details_choose_configuration_action_button).setVisibility(
                        View.VISIBLE );
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_edit_configuration_action_button:
                startActivityForResult(
                        EditConfigurationActivity.getStartIntent(getActivity(), pokemonConfig),
                        UPDATE_CONFIGURATION_REQUEST_CODE );
                break;
            case R.id.details_damage_calculator_action_button:
                startActivityForResult(
                        DamageCalculatorActivity.getStartIntent(getActivity(), pokemonConfig),
                        UPDATE_CONFIGURATION_REQUEST_CODE );
                break;
            case R.id.details_choose_configuration_action_button:
                Intent dataIntent = getActivity().getIntent();
                dataIntent.putExtra(DamageCalculatorFragment.POKEMON_CONFIGURATION_EXTRA_KEY,
                        pokemonConfig );
                getActivity().setResult(Activity.RESULT_OK, dataIntent);
                getActivity().finish();
                dismiss();
                break;
        }
    }
    
    private void populateView(View rootView) {
        TextView pokemonName = rootView.findViewById(R.id.pokemon_name_text);
        TextView configurationName = rootView.findViewById(R.id.pokemon_config_name_text);
        TypeView pokemonType = rootView.findViewById(R.id.pokemon_type_text);

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
        //TODO
        //pokemonType.setType(configurationRepresentation.type);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTargetFragment().onActivityResult( getTargetRequestCode(), resultCode, data );
        dismiss();
    }
}
