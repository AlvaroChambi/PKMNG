package es.developer.achambi.pkmng.modules.details.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseDialogFragment;
import es.developer.achambi.pkmng.core.ui.QuickDetailPopup;
import es.developer.achambi.pkmng.core.ui.view.AbilityView;
import es.developer.achambi.pkmng.core.ui.view.ItemView;
import es.developer.achambi.pkmng.core.ui.view.NatureView;
import es.developer.achambi.pkmng.core.ui.view.TypeView;
import es.developer.achambi.pkmng.modules.calculator.view.DamageCalculatorActivity;
import es.developer.achambi.pkmng.modules.calculator.view.DamageCalculatorFragment;
import es.developer.achambi.pkmng.modules.create.EditConfigurationActivity;
import es.developer.achambi.pkmng.modules.details.view.presentation.ConfigurationDetailsPresentation;
import es.developer.achambi.pkmng.modules.details.view.presentation.MovePresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.view.OverviewFragment;

public class ConfigurationDetailsFragment extends BaseDialogFragment
        implements View.OnClickListener {
    private static final String CONFIGURATION_ARGUMENT_KEY = "CONFIGURATION_ARGUMENT_KEY";
    private static final String USE_CONTEXT_ARGUMENT_KEY = "USE_CONTEXT_ARGUMENT_KEY";
    private static final int UPDATE_CONFIGURATION_REQUEST_CODE = 100;
    private PokemonConfig pokemonConfig;
    private ConfigurationDetailsPresentation configurationPresentation;

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
            configurationPresentation = ConfigurationDetailsPresentation.Builder
                    .buildPresentation(getActivity(), pokemonConfig);
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
            case R.id.configuration_details_move_0:
                displayMoveQuickDetails( configurationPresentation.move0, v );
                break;
            case R.id.configuration_details_move_1:
                displayMoveQuickDetails( configurationPresentation.move1, v );
                break;
            case R.id.configuration_details_move_2:
                displayMoveQuickDetails( configurationPresentation.move2, v );
                break;
            case R.id.configuration_details_move_3:
                displayMoveQuickDetails( configurationPresentation.move3, v );
                break;
        }
    }

    private void displayMoveQuickDetails( MovePresentation move, View anchor ) {
        View quickDetail = LayoutInflater.from(getActivity())
                .inflate(R.layout.move_quick_detail_view, null);
        TextView power = quickDetail.findViewById( R.id.move_quick_detail_power_text );
        TextView accuracy = quickDetail.findViewById( R.id.move_quick_detail_accuracy_text );
        TextView category = quickDetail.findViewById( R.id.move_quick_detail_category_text );
        TextView name = quickDetail.findViewById( R.id.move_quick_detail_name_text );
        TextView type = quickDetail.findViewById( R.id.move_quick_detail_type_text );
        power.setText( move.power );
        accuracy.setText( move.accuracy );
        category.setText( move.category.name );
        name.setText( move.name );
        type.setText( move.type.name );

        QuickDetailPopup.displayDetails( quickDetail, anchor );
    }
    
    private void populateView(View rootView) {
        TextView pokemonName = rootView.findViewById(R.id.pokemon_name_text);
        TextView configurationName = rootView.findViewById(R.id.pokemon_config_name_text);
        TypeView pokemonType = rootView.findViewById(R.id.pokemon_type_text);

        ItemView item = rootView.findViewById(R.id.pokemon_item_text);
        AbilityView ability = rootView.findViewById(R.id.pokemon_ability_text);
        NatureView nature = rootView.findViewById(R.id.pokemon_nature_text);

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

        pokemonName.setText(configurationPresentation.pokemon.name);
        configurationName.setText(configurationPresentation.name);
        pokemonType.setType(configurationPresentation.pokemon.type);

        item.setItem(configurationPresentation.item.name,
                configurationPresentation.item.description,
                configurationPresentation.item.empty);
        ability.setAbility(configurationPresentation.ability.name,
                configurationPresentation.ability.description,
                configurationPresentation.ability.empty);
        nature.setNature(configurationPresentation.nature.name,
                configurationPresentation.nature.details.increased,
                configurationPresentation.nature.details.decreased,
                configurationPresentation.nature.empty);
   
        pokemonHP.setText(configurationPresentation.stats.hp);
        pokemonAttack.setText(configurationPresentation.stats.attack);
        pokemonDefense.setText(configurationPresentation.stats.defense);
        pokemonSpAttack.setText(configurationPresentation.stats.spAttack);
        pokemonSpDefense.setText(configurationPresentation.stats.spDefense);
        pokemonSpeed.setText(configurationPresentation.stats.speed);

        populateMove( configurationPresentation.move0, move0 );
        populateMove( configurationPresentation.move1, move1 );
        populateMove( configurationPresentation.move2, move2 );
        populateMove( configurationPresentation.move3, move3 );
    }

    private void populateMove( MovePresentation move, TextView moveView ) {
        if( move.empty ) {
            moveView.setText( move.name );
            moveView.setBackgroundTintList( ContextCompat.getColorStateList(
                    getActivity(), R.color.primary_innactive ) );
            moveView.setOnClickListener( null );
        } else {
            moveView.setText( move.name );
            moveView.setBackgroundTintList( move.type.backgroundColor );
            moveView.setOnClickListener( this );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTargetFragment().onActivityResult( getTargetRequestCode(), resultCode, data );
        dismiss();
    }
}
