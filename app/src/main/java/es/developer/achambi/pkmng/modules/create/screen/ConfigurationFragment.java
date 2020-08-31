package es.developer.achambi.pkmng.modules.create.screen;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.AppWiring;
import es.developer.achambi.coreframework.threading.Error;
import es.developer.achambi.coreframework.threading.Response;
import es.developer.achambi.coreframework.threading.ResponseHandler;
import es.developer.achambi.coreframework.ui.BaseRequestFragment;
import es.developer.achambi.coreframework.ui.Presenter;
import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.ui.presentation.ItemPresentation;
import es.developer.achambi.pkmng.modules.ui.presentation.TypePresentation;
import es.developer.achambi.pkmng.modules.ui.screen.TypeView;
import es.developer.achambi.coreframework.utils.GlideApp;
import es.developer.achambi.pkmng.modules.create.presenter.ConfigurationAction;
import es.developer.achambi.pkmng.modules.create.presenter.ConfigurationPresenter;
import es.developer.achambi.pkmng.modules.details.view.presentation.ConfigurationDetailsPresentation;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.ability.screen.SearchAbilityActivity;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.item.screen.SearchItemActivity;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.screen.SearchMoveActivity;
import es.developer.achambi.pkmng.modules.search.nature.screen.SearchNatureActivity;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;
import es.developer.achambi.pkmng.modules.search.pokemon.screen.SearchPokemonActivity;

import static android.app.Activity.RESULT_OK;

public class ConfigurationFragment extends BaseRequestFragment
        implements View.OnClickListener, Screen {
    private static final String POKEMON_ARGUMENT_KEY = "POKEMON_ARGUMENT_KEY";
    private static final String CONFIGURATION_ARGUMENT_KEY = "CONFIGURATION_ARGUMENT_KEY";
    private static final int REPLACE_POKEMON_RESULT_CODE = 100;
    private static final int REPLACE_ITEM_RESULT_CODE = 101;
    private static final int REPLACE_ABILITY_RESULT_CODE = 102;
    private static final int REPLACE_NATURE_RESULT_CODE = 103;
    private static final int REPLACE_MOVE0_RESULT_CODE = 104;
    private static final int REPLACE_MOVE1_RESULT_CODE = 105;
    private static final int REPLACE_MOVE2_RESULT_CODE = 106;
    private static final int REPLACE_MOVE3_RESULT_CODE = 107;
    private static final int SAVE_CONFIGURATION_REQUEST_CODE = 108;

    private static final String CREATE_CONFIGURATION_DIALOG_TAG = "CREATE_CONFIGURATION_DIALOG_TAG";

    public static final String POKEMON_ACTIVITY_RESULT_DATA_KEY = "POKEMON_DATA_KEY";
    public static final String ITEM_ACTIVITY_RESULT_DATA_KEY = "ITEM_DATA_KEY";
    public static final String ABILITY_ACTIVITY_RESULT_DATA_KEY = "ABILITY_DATA_KEY";
    public static final String NATURE_ACTIVITY_RESULT_DATA_KEY = "NATURE_DATA_KEY";
    public static final String MOVE_ACTIVITY_RESULT_DATA_KEY = "MOVE_DATA_KEY";
    public static final String CONFIGURATION_NAME_DATA_KEY = "CONFIGURATION_NAME_DATA_KEY";
    public static final String POKEMON_CONFIG_RESULT_DATA_KEY = "PK_CONFIG_DATA_KEY";

    private ConfigurationDetailsPresentation configurationPresentation;

    private MoveConfigurationRepresentation move0;
    private MoveConfigurationRepresentation move1;
    private MoveConfigurationRepresentation move2;
    private MoveConfigurationRepresentation move3;
    private ItemPresentation item;
    private ConfigurationPresenter presenter;

    public static ConfigurationFragment newInstance( Bundle args ) {
        ConfigurationFragment fragment = new ConfigurationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static Bundle getFragmentArgs( PokemonConfig pokemonConfig ) {
        Bundle args = new Bundle();
        args.putParcelable( CONFIGURATION_ARGUMENT_KEY, pokemonConfig );
        return args;
    }

    public static Bundle getFragmentArgs( Pokemon pokemon ) {
        Bundle args = new Bundle();
        args.putParcelable(POKEMON_ARGUMENT_KEY, pokemon);

        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupPresenter();
        if( savedInstanceState == null ) {
            if( getArguments() != null && getArguments().containsKey( POKEMON_ARGUMENT_KEY ) ) {
                presenter.setPokemon( (Pokemon)getArguments().getParcelable(POKEMON_ARGUMENT_KEY) );
            } else if( getArguments() != null && getArguments().containsKey( CONFIGURATION_ARGUMENT_KEY ) ) {
                presenter.setPokemonConfiguration(
                        (PokemonConfig)getArguments().getParcelable( CONFIGURATION_ARGUMENT_KEY ) );
            }
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.configuration_fragment_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        if(presenter.getPokemonConfiguration().getId() != BasePokemon.EMPTY_ID) {
            if(!isViewRecreated()) {
                configurationPresentation = ConfigurationDetailsPresentation.Builder
                        .buildPresentation(getActivity(), presenter.getEditablePokemonConfig());
                MoveRepresentationBuilder builder = new MoveRepresentationBuilder();
                move0 = builder.build( presenter.getConfiguration().getMove0() );
                move1 = builder.build( presenter.getConfiguration().getMove1() );
                move2 = builder.build( presenter.getConfiguration().getMove2() );
                move3 = builder.build( presenter.getConfiguration().getMove3() );
                item = ItemPresentation.Builder.buildPresentation( getActivity(),
                        presenter.getConfiguration().getItem() );
            }

            populatePokemonView(view);
            populateItemView(item, view);
            populateAbilityView(view);
            populateNatureView(view);
            populateMoveView( view.findViewById(R.id.configuration_move_0_frame), move0 );
            populateMoveView( view.findViewById(R.id.configuration_move_1_frame), move1 );
            populateMoveView( view.findViewById(R.id.configuration_move_2_frame), move2 );
            populateMoveView( view.findViewById(R.id.configuration_move_3_frame), move3 );
            ConfigurationStatSetView configurationStatSetView =
                    view.findViewById(R.id.configuration_ev_set_view);
            configurationStatSetView.setProgressUpdateProvider( presenter );
            configurationStatSetView.displayStatSet( presenter.getStatsSet() );
            configurationStatSetView.displayNatureModifier( presenter.getNature() );
        }

        view.findViewById(R.id.pokemon_image_view).setOnClickListener(this);
        view.findViewById(R.id.configuration_item_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_nature_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_ability_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_move_0_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_move_1_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_move_2_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_move_3_frame).setOnClickListener(this);
        view.findViewById(R.id.configuration_floating_save_button_middle).setOnClickListener(this);
    }

    @Override
    public Presenter setupPresenter() {
        if( presenter == null ) {
            presenter = AppWiring.createConfigurationAssembler.getPresenterFactory()
                    .buildPresenter(this);
        }
        return presenter;
    }

    @Override
    public int getLoadingFrame() {
        return R.id.base_request_loading_frame;
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.pokemon_image_view:
                startActivityForResult(SearchPokemonActivity.getStartIntent( getActivity(),
                        presenter.getPokemon()  ), REPLACE_POKEMON_RESULT_CODE );
                break;
            case R.id.configuration_item_frame:
                startActivityForResult(SearchItemActivity.getStartIntent(getActivity(),
                        presenter.getItem()),
                        REPLACE_ITEM_RESULT_CODE);
                break;
            case R.id.configuration_ability_frame:
                startActivityForResult(SearchAbilityActivity.getStartIntent(getActivity(),
                        presenter.getAbility(), presenter.getPokemon().getId()),
                        REPLACE_ABILITY_RESULT_CODE);
                break;
            case R.id.configuration_nature_frame:
                startActivityForResult(SearchNatureActivity.getStartIntent(getActivity(),
                        presenter.getNature()),
                        REPLACE_NATURE_RESULT_CODE);
                break;
            case R.id.configuration_move_0_frame:
                startActivityForResult( SearchMoveActivity.getStartIntent(getActivity(),
                        presenter.getConfiguration().getMove0(), presenter.getPokemon().getId() ),
                        REPLACE_MOVE0_RESULT_CODE );
                break;
            case R.id.configuration_move_1_frame:
                startActivityForResult( SearchMoveActivity.getStartIntent(getActivity(),
                        presenter.getConfiguration().getMove1(), presenter.getPokemon().getId() ),
                        REPLACE_MOVE1_RESULT_CODE );
                break;
            case R.id.configuration_move_2_frame:
                startActivityForResult( SearchMoveActivity.getStartIntent(getActivity(),
                        presenter.getConfiguration().getMove2(), presenter.getPokemon().getId() ),
                        REPLACE_MOVE2_RESULT_CODE );
                break;
            case R.id.configuration_move_3_frame:
                startActivityForResult( SearchMoveActivity.getStartIntent(getActivity(),
                        presenter.getConfiguration().getMove3(), presenter.getPokemon().getId() ),
                        REPLACE_MOVE3_RESULT_CODE );
                break;
            case R.id.configuration_floating_save_button_middle:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                CreateConfigurationDialog dialog = CreateConfigurationDialog.newInstance(
                        presenter.getConfigurationName() );
                dialog.setTargetFragment( this, SAVE_CONFIGURATION_REQUEST_CODE );
                dialog.show( transaction,CREATE_CONFIGURATION_DIALOG_TAG );
                break;
        }
    }

    /**
     * On a pokemon change configuration values could've been changed, therefore an update of the
     * ui values is required
     */
    private void onCurrentPokemonChanged( View view ) {
        configurationPresentation = ConfigurationDetailsPresentation.Builder
                .buildPresentation(getActivity(), presenter.getEditablePokemonConfig());
        MoveRepresentationBuilder builder = new MoveRepresentationBuilder();
        move0 = builder.build( presenter.getConfiguration().getMove0() );
        move1 = builder.build( presenter.getConfiguration().getMove1() );
        move2 = builder.build( presenter.getConfiguration().getMove2() );
        move3 = builder.build( presenter.getConfiguration().getMove3() );
        item = ItemPresentation.Builder.buildPresentation( getActivity(),
                presenter.getConfiguration().getItem() );

        populatePokemonView(view);
        populateItemView(item, view);
        populateAbilityView(view);
        populateNatureView(view);
        populateMoveView( view.findViewById(R.id.configuration_move_0_frame), move0 );
        populateMoveView( view.findViewById(R.id.configuration_move_1_frame), move1 );
        populateMoveView( view.findViewById(R.id.configuration_move_2_frame), move2 );
        populateMoveView( view.findViewById(R.id.configuration_move_3_frame), move3 );
        ConfigurationStatSetView configurationStatSetView =
                view.findViewById(R.id.configuration_ev_set_view);
        configurationStatSetView.setProgressUpdateProvider( presenter );
        configurationStatSetView.displayStatSet( presenter.getStatsSet() );
        configurationStatSetView.displayNatureModifier( presenter.getNature() );
    }

    private void populatePokemonView( View rootView ) {
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

        pokemonLevel.setText(configurationPresentation.pokemon.level);
        pokemonName.setText(configurationPresentation.pokemon.name);
        pokemonType.setType(configurationPresentation.pokemon.type);
        baseStats.setText(configurationPresentation.pokemon.totalStats);
        pokemonHP.setText(configurationPresentation.stats.hp);
        pokemonAttack.setText(configurationPresentation.stats.attack);
        pokemonDefense.setText(configurationPresentation.stats.defense);
        pokemonSpAttack.setText(configurationPresentation.stats.spAttack);
        pokemonSpDefense.setText(configurationPresentation.stats.spDefense);
        pokemonSpeed.setText(configurationPresentation.stats.speed);
        GlideApp.with(this)
                .load(configurationPresentation.pokemon.image)
                .placeholder(configurationPresentation.pokemon.defaultImage)
                .into(pokemonIcon);
    }

    private void populateItemView(ItemPresentation item, View rootView) {
        TextView itemName = rootView.findViewById(R.id.configuration_item_name_text);
        ImageView itemIcon = rootView.findViewById(R.id.configuration_item_image_view);
        View empty = rootView.findViewById(R.id.configuration_item_empty_state);
        if( !item.empty ) {
            itemName.setText(presenter.getItem().getName());
            itemName.setVisibility(View.VISIBLE);
            itemIcon.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            GlideApp.with(this)
                    .load(item.image)
                    .placeholder(R.drawable.icon_placeholder)
                    .into(itemIcon);
        } else {
            itemName.setVisibility(View.GONE);
            itemIcon.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    private void populateAbilityView( View rootView ) {
        TextView abilityName = rootView.findViewById(R.id.configuration_ability_name_text);
        View empty = rootView.findViewById(R.id.configuration_ability_empty_state);
        if( presenter.getAbility().getId() != -1 ) {
            abilityName.setText(presenter.getAbility().getName());
            abilityName.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        } else {
            abilityName.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    private void populateNatureView( View rootView ) {
        TextView natureName = rootView.findViewById(R.id.configuration_nature_name_text);
        View empty = rootView.findViewById(R.id.configuration_nature_empty_state);
        if( presenter.getNature().getId() != -1 ) {
            natureName.setText(presenter.getNature().getName());
            natureName.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);

            ConfigurationStatSetView configurationStatSetView =
                    rootView.findViewById(R.id.configuration_ev_set_view);
            configurationStatSetView.displayNatureModifier( presenter.getNature() );
        } else {
            natureName.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    private void populateMoveView( View moveRootView, MoveConfigurationRepresentation move ) {
        TextView moveName = moveRootView.findViewById(R.id.move_view_name_text);
        TextView moveType = moveRootView.findViewById(R.id.move_view_type_text);
        TextView movePower = moveRootView.findViewById(R.id.move_view_power_text);
        if( !move.isEmpty ) {
            moveName.setText( move.name );
            movePower.setText( move.power );
            moveType.setText( move.type.name );
            moveRootView.setBackgroundTintList( move.type.backgroundColor );
            moveName.setVisibility(View.VISIBLE);
            movePower.setVisibility(View.VISIBLE);
            moveType.setVisibility(View.VISIBLE);
            moveRootView.findViewById(R.id.move_view_empty_state_image).setVisibility(View.GONE);
        } else {
            moveName.setVisibility(View.GONE);
            movePower.setVisibility(View.GONE);
            moveType.setVisibility(View.GONE);
            moveRootView.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),
                    R.color.primary ) );
            moveRootView.findViewById(R.id.move_view_empty_state_image).setVisibility(View.VISIBLE);
        }
    }

    private void saveConfigurationRequest( String configurationName ) {
       startLoading(TRANSPARENT_LOADING_BACKGROUND);
       presenter.saveConfigurationRequest( configurationName,
               new ResponseHandler<ConfigurationAction>() {
           @Override
           public void onSuccess(Response<ConfigurationAction> response) {
               handleSaveConfigurationResult( response.getData() );
               hideLoading();
           }

           @Override
           public void onError(Error error) {
               super.onError(error);
               showSnackBackError( error );
           }
       });
    }

    private void handleSaveConfigurationResult( ConfigurationAction action ) {
        Intent data = new Intent();
        switch ( action ) {
            case CREATE:
                data.putExtra( POKEMON_CONFIG_RESULT_DATA_KEY, presenter.getPokemonConfiguration() );
                getActivity().setResult(Activity.RESULT_OK, data);
                Toast createdToast = Toast.makeText(getActivity(),
                        R.string.configuration_created_toast_message,
                        Toast.LENGTH_SHORT);
                createdToast.setGravity( Gravity.CENTER, 0, 0 );
                createdToast.show();
                break;
            case UPDATE:
                data.putExtra( POKEMON_CONFIG_RESULT_DATA_KEY, presenter.getPokemonConfiguration() );
                getActivity().setResult(Activity.RESULT_OK, data);
                Toast updatedToast = Toast.makeText(getActivity(),
                        R.string.configuration_updated_toast_message,
                        Toast.LENGTH_SHORT);
                updatedToast.setGravity( Gravity.CENTER, 0, 0 );
                updatedToast.show();
                break;
            case NONE:
                getActivity().setResult( Activity.RESULT_CANCELED );
                Toast noChangesToast = Toast.makeText(getActivity(),
                        R.string.configuration_not_changed_toast_message,
                        Toast.LENGTH_SHORT);
                noChangesToast.setGravity( Gravity.CENTER, 0, 0 );
                noChangesToast.show();
                break;
        }
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK &&
                requestCode == REPLACE_POKEMON_RESULT_CODE ) {
            presenter.setPokemon( (Pokemon)data
                    .getParcelableExtra( POKEMON_ACTIVITY_RESULT_DATA_KEY ) );

            onCurrentPokemonChanged( getView() );
        } else if( resultCode == RESULT_OK &&
                    requestCode == REPLACE_ITEM_RESULT_CODE ) {
            Item resultItem = data.getParcelableExtra( ITEM_ACTIVITY_RESULT_DATA_KEY );
            item = ItemPresentation.Builder.buildPresentation(getActivity(), resultItem);
            presenter.setItem( resultItem );
            populateItemView( item, getView() );
        } else if( resultCode == RESULT_OK &&
                    requestCode == REPLACE_ABILITY_RESULT_CODE ) {
            presenter.setAbility( (Ability) data.getParcelableExtra( ABILITY_ACTIVITY_RESULT_DATA_KEY ) );
            populateAbilityView( getView() );
        } else if( resultCode == RESULT_OK &&
                    requestCode == SAVE_CONFIGURATION_REQUEST_CODE ) {
            saveConfigurationRequest( data.getStringExtra( CONFIGURATION_NAME_DATA_KEY ) );
        } else if( resultCode == RESULT_OK &&
                    requestCode == REPLACE_NATURE_RESULT_CODE ) {
            presenter.setNature( (Nature)data.getParcelableExtra( NATURE_ACTIVITY_RESULT_DATA_KEY ) );
            populateNatureView( getView() );
        } else  {
            if( resultCode == RESULT_OK ) {
                MoveRepresentationBuilder builder = new MoveRepresentationBuilder();
                Move move = data.getParcelableExtra( MOVE_ACTIVITY_RESULT_DATA_KEY );
                MoveConfigurationRepresentation movePresentation = builder.build( move );
                if( requestCode == REPLACE_MOVE0_RESULT_CODE ) {
                    move0 = builder.build( move );
                    presenter.setMove0( move );
                    populateMoveView( getView().findViewById(R.id.configuration_move_0_frame),
                            movePresentation );
                } else if( requestCode == REPLACE_MOVE1_RESULT_CODE ) {
                    move1 = builder.build( move );
                    presenter.setMove1( move );
                    populateMoveView( getView().findViewById(R.id.configuration_move_1_frame),
                            movePresentation );
                } else if( requestCode == REPLACE_MOVE2_RESULT_CODE ) {
                    move2 = builder.build( move );
                    presenter.setMove2( move );
                    populateMoveView( getView().findViewById(R.id.configuration_move_2_frame),
                            movePresentation );
                } else if( requestCode == REPLACE_MOVE3_RESULT_CODE ) {
                    move3 = builder.build( move );
                    presenter.setMove3( move );
                    populateMoveView( getView().findViewById(R.id.configuration_move_3_frame),
                            movePresentation );
                }
            }
        }
    }

    @Override
    public Lifecycle screenLifecycle() {
        return getLifecycle();
    }

    public class MoveRepresentationBuilder {
        public MoveConfigurationRepresentation build( Move move ) {
            return new MoveConfigurationRepresentation(
                    move.getId(),
                    move.getName(),
                    TypePresentation.Builder.build( getActivity(), move.getType() ),
                    "Pow. " + move.getPower(),
                    move.getName().equals("")
            );
        }
    }
}
