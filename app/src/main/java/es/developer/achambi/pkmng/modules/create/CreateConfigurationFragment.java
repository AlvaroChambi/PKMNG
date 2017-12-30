package es.developer.achambi.pkmng.modules.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseRequestFragment;
import es.developer.achambi.pkmng.modules.create.view.MoveConfigurationRepresentation;
import es.developer.achambi.pkmng.modules.create.view.StatEVView;
import es.developer.achambi.pkmng.modules.details.databuilder.PokemonDetailsDataBuilder;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.view.SearchActivity;
import es.developer.achambi.pkmng.modules.overview.view.representation.OverviewPokemonRepresentation;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.ability.view.SearchAbilityActivity;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.item.view.SearchItemActivity;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.view.SearchMoveActivity;
import es.developer.achambi.pkmng.modules.search.nature.SearchNatureActivity;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

import static android.app.Activity.RESULT_OK;

public class CreateConfigurationFragment extends BaseRequestFragment implements View.OnClickListener {
    private static final String POKEMON_SAVED_STATE = "POKEMON_SAVED_STATE";
    private static final String ITEM_SAVED_STATE = "ITEM_SAVED_STATE";
    private static final String ABILITY_SAVED_STATE = "ABILITY_SAVED_STATE";
    private static final String NATURE_SAVED_STATE = "NATURE_SAVED_STATE";
    private static final String MOVE_0_SAVED_STATE = "MOVE_0_SAVED_STATE";
    private static final String MOVE_1_SAVED_STATE = "MOVE_1_SAVED_STATE";
    private static final String MOVE_2_SAVED_STATE = "MOVE_2_SAVED_STATE";
    private static final String MOVE_3_SAVED_STATE = "MOVE_3_SAVED_STATE";
    private static final String EV_SET_SAVED_STATE = "EV_SET_SAVED_STATE";

    private static final String POKEMON_ARGUMENT_KEY = "POKEMON_ARGUMENT_KEY";
    private static final int REPLACE_POKEMON_RESULT_CODE = 100;
    private static final int REPLACE_ITEM_RESULT_CODE = 101;
    private static final int REPLACE_ABILITY_RESULT_CODE = 102;
    private static final int REPLACE_NATURE_RESULT_CODE = 103;
    private static final int REPLACE_MOVE0_RESULT_CODE = 104;
    private static final int REPLACE_MOVE1_RESULT_CODE = 105;
    private static final int REPLACE_MOVE2_RESULT_CODE = 106;
    private static final int REPLACE_MOVE3_RESULT_CODE = 107;

    public static final String POKEMON_ACTIVITY_RESULT_DATA_KEY = "POKEMON_DATA_KEY";
    public static final String ITEM_ACTIVITY_RESULT_DATA_KEY = "ITEM_DATA_KEY";
    public static final String ABILITY_ACTIVITY_RESULT_DATA_KEY = "ABILITY_DATA_KEY";
    public static final String NATURE_ACTIVITY_RESULT_DATA_KEY = "NATURE_DATA_KEY";
    public static final String MOVE_ACTIVITY_RESULT_DATA_KEY = "MOVE_DATA_KEY";

    private Pokemon pokemon;
    private OverviewPokemonRepresentation pokemonRepresentation;

    private Item item;
    private Ability ability;
    private Nature nature;

    private MoveConfigurationRepresentation move0;
    private MoveConfigurationRepresentation move1;
    private MoveConfigurationRepresentation move2;
    private MoveConfigurationRepresentation move3;

    private StatsSet evSet;

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
        evSet = new StatsSet();
        if( savedInstanceState != null ) {
            pokemon = savedInstanceState.getParcelable( POKEMON_SAVED_STATE );
            item = savedInstanceState.getParcelable( ITEM_SAVED_STATE );
            ability = savedInstanceState.getParcelable( ABILITY_SAVED_STATE );
            nature = savedInstanceState.getParcelable( NATURE_SAVED_STATE );
            move0 = savedInstanceState.getParcelable( MOVE_0_SAVED_STATE );
            move1 = savedInstanceState.getParcelable( MOVE_1_SAVED_STATE );
            move2 = savedInstanceState.getParcelable( MOVE_2_SAVED_STATE );
            move3 = savedInstanceState.getParcelable( MOVE_3_SAVED_STATE );
            evSet = savedInstanceState.getParcelable( EV_SET_SAVED_STATE );
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.create_configuration_fragment_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        if(!isViewRecreated()) {
            pokemonRepresentation = new PokemonDetailsDataBuilder()
                    .buildViewRepresentation(getResources(), pokemon);
        }

        populatePokemonView(view);
        populateItemView(view);
        populateAbilityView(view);
        populateNatureView(view);
        populateMoveView( view.findViewById(R.id.configuration_move_0_frame), move0 );
        populateMoveView( view.findViewById(R.id.configuration_move_1_frame), move1 );
        populateMoveView( view.findViewById(R.id.configuration_move_2_frame), move2 );
        populateMoveView( view.findViewById(R.id.configuration_move_3_frame), move3 );
        populateEvSetView(view);

        view.findViewById(R.id.pokemon_image_view).setOnClickListener(this);
        view.findViewById(R.id.configuration_item_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_nature_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_ability_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_move_0_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_move_1_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_move_2_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_move_3_frame).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.pokemon_image_view:
                startActivityForResult(SearchActivity.getStartIntent(
                        getActivity(), SearchFilter.POKEMON_FILTER ), REPLACE_POKEMON_RESULT_CODE );
                break;
            case R.id.configuration_item_frame:
                startActivityForResult(SearchItemActivity.getStartIntent(getActivity()),
                        REPLACE_ITEM_RESULT_CODE);
                break;
            case R.id.configuration_ability_frame:
                startActivityForResult(SearchAbilityActivity.getStartIntent(getActivity()),
                        REPLACE_ABILITY_RESULT_CODE);
                break;
            case R.id.configuration_nature_frame:
                startActivityForResult(SearchNatureActivity.getStartIntent(getActivity()),
                        REPLACE_NATURE_RESULT_CODE);
                break;
            case R.id.configuration_move_0_frame:
                startActivityForResult( SearchMoveActivity.getStartIntent(getActivity()),
                        REPLACE_MOVE0_RESULT_CODE );
                break;
            case R.id.configuration_move_1_frame:
                startActivityForResult( SearchMoveActivity.getStartIntent(getActivity()),
                        REPLACE_MOVE1_RESULT_CODE );
                break;
            case R.id.configuration_move_2_frame:
                startActivityForResult( SearchMoveActivity.getStartIntent(getActivity()),
                        REPLACE_MOVE2_RESULT_CODE );
                break;
            case R.id.configuration_move_3_frame:
                startActivityForResult( SearchMoveActivity.getStartIntent(getActivity()),
                        REPLACE_MOVE3_RESULT_CODE );
                break;
        }
    }

    private void populateEvSetView( View rootView ) {
        StatEVView hp = rootView.findViewById(R.id.configuration_hp_ev_stat_bar);
        StatEVView attack = rootView.findViewById(R.id.configuration_attack_ev_stat_bar);
        StatEVView defense = rootView.findViewById(R.id.configuration_defense_ev_stat_bar);
        StatEVView spAttack = rootView.findViewById(R.id.configuration_sp_attack_ev_stat_bar);
        StatEVView spDefense = rootView.findViewById(R.id.configuration_sp_defense_ev_stat_bar);
        StatEVView speed = rootView.findViewById(R.id.configuration_speed_ev_stat_bar);

        hp.setBaseValue( pokemon.getHP() );
        hp.setValue( evSet.getHP() );
        attack.setBaseValue( pokemon.getAttack() );
        attack.setValue( evSet.getAttack() );
        defense.setBaseValue( pokemon.getDefense() );
        defense.setValue( evSet.getDefense() );
        spAttack.setBaseValue( pokemon.getSpAttack() );
        spAttack.setValue( evSet.getSpAttack() );
        spDefense.setBaseValue( pokemon.getSPDefense() );
        spDefense.setValue( evSet.getSPDefense() );
        speed.setBaseValue( pokemon.getSpeed() );
        speed.setValue( evSet.getSpeed() );
    }

    private void populatePokemonView(View rootView) {
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

    private void populateItemView(View rootView) {
        if( item != null ) {
            TextView itemName = rootView.findViewById(R.id.configuration_item_name_text);
            itemName.setText(item.getName());
            itemName.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.configuration_item_empty_state).setVisibility(View.GONE);
        }
    }

    private void populateAbilityView( View rootView ) {
        if( ability != null ) {
            TextView abilityName = rootView.findViewById(R.id.configuration_ability_name_text);
            abilityName.setText(ability.getName());
            abilityName.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.configuration_ability_empty_state).setVisibility(View.GONE);
        }
    }

    private void populateNatureView( View rootView ) {
        if( nature != null ) {
            TextView natureName = rootView.findViewById(R.id.configuration_nature_name_text);
            natureName.setText(nature.getName());
            natureName.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.configuration_nature_empty_state).setVisibility(View.GONE);
        }
    }

    private void populateMoveView( View moveRootView, MoveConfigurationRepresentation move ) {
        if( move != null ) {
            TextView moveName = moveRootView.findViewById(R.id.move_view_name_text);
            TextView moveType = moveRootView.findViewById(R.id.move_view_type_text);
            TextView movePower = moveRootView.findViewById(R.id.move_view_power_text);

            moveName.setText(move.name);
            movePower.setText(move.power);
            moveType.setText(move.type);
            moveName.setVisibility(View.VISIBLE);
            movePower.setVisibility(View.VISIBLE);
            moveType.setVisibility(View.VISIBLE);
            moveRootView.findViewById(R.id.move_view_empty_state_image).setVisibility(View.GONE);
        }
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

            populatePokemonView( getView() );
        } else if( resultCode == RESULT_OK &&
                    requestCode == REPLACE_ITEM_RESULT_CODE ) {
            item = data.getParcelableExtra( ITEM_ACTIVITY_RESULT_DATA_KEY );
            populateItemView( getView() );
        } else if( resultCode == RESULT_OK &&
                    requestCode == REPLACE_ABILITY_RESULT_CODE ) {
            ability = data.getParcelableExtra( ABILITY_ACTIVITY_RESULT_DATA_KEY );
            populateAbilityView( getView() );
        } else if( resultCode == RESULT_OK &&
                    requestCode == REPLACE_NATURE_RESULT_CODE ) {
            nature = data.getParcelableExtra( NATURE_ACTIVITY_RESULT_DATA_KEY );
            populateNatureView( getView() );
        } else  {
            if( resultCode == RESULT_OK ) {
                MoveRepresentationBuilder builder = new MoveRepresentationBuilder();
                Move move = data.getParcelableExtra( MOVE_ACTIVITY_RESULT_DATA_KEY );
                MoveConfigurationRepresentation movePresentation = builder.build( move );
                if( requestCode == REPLACE_MOVE0_RESULT_CODE ) {
                    move0 = builder.build( move );
                    populateMoveView( getView().findViewById(R.id.configuration_move_0_frame),
                            movePresentation );
                } else if( requestCode == REPLACE_MOVE1_RESULT_CODE ) {
                    move1 = builder.build( move );
                    populateMoveView( getView().findViewById(R.id.configuration_move_1_frame),
                            movePresentation );
                } else if( requestCode == REPLACE_MOVE2_RESULT_CODE ) {
                    move2 = builder.build( move );
                    populateMoveView( getView().findViewById(R.id.configuration_move_2_frame),
                            movePresentation );
                } else if( requestCode == REPLACE_MOVE3_RESULT_CODE ) {
                    move3 = builder.build( move );
                    populateMoveView( getView().findViewById(R.id.configuration_move_3_frame),
                            movePresentation );
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable( POKEMON_SAVED_STATE, pokemon );
        outState.putParcelable( ITEM_SAVED_STATE, item );
        outState.putParcelable( ABILITY_SAVED_STATE, ability );
        outState.putParcelable( NATURE_SAVED_STATE, nature );
        outState.putParcelable( MOVE_0_SAVED_STATE, move0 );
        outState.putParcelable( MOVE_1_SAVED_STATE, move1 );
        outState.putParcelable( MOVE_2_SAVED_STATE, move2 );
        outState.putParcelable( MOVE_3_SAVED_STATE, move3 );
        outState.putParcelable( EV_SET_SAVED_STATE, evSet );
    }

    public class MoveRepresentationBuilder {
        public MoveConfigurationRepresentation build( Move move ) {
            MoveConfigurationRepresentation representation = new MoveConfigurationRepresentation(
                    move.getId(),
                    move.getName(),
                    formatType( move.getType() ),
                    "Pow. " + move.getPower()
            );
            return representation;
        }

        private String formatType( Pokemon.Type type ) {
            switch (type) {

                case ELECTRIC:
                    return "ELECTRIC";
                case GROUND:
                    return "GROUND";
                case EMPTY:
                    return "";
            }
            return "";
        }
    }
}
