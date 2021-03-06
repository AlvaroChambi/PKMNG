package es.developer.achambi.pkmng.modules.calculator.screen;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import es.developer.achambi.coreframework.ui.FloatingActionMenu;
import es.developer.achambi.coreframework.ui.Presenter;
import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.coreframework.utils.GlideApp;
import es.developer.achambi.pkmng.modules.calculator.screen.presentation.CalculatorPokemonPresentation;
import es.developer.achambi.pkmng.modules.calculator.presenter.DamageCalculatorPresenter;
import es.developer.achambi.pkmng.modules.calculator.screen.presentation.MoveDamagePresentation;
import es.developer.achambi.pkmng.modules.create.presenter.ConfigurationAction;
import es.developer.achambi.pkmng.modules.create.screen.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.configuration.screen.SearchConfigurationActivity;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.screen.SearchMoveActivity;

public class DamageCalculatorFragment extends BaseRequestFragment implements View.OnClickListener,
        FloatingActionMenu.MenuOptionClickedListener, Screen {
    private static final String CONFIGURATION_ARGUMENT_KEY = "CONFIGURATION_ARGUMENT_KEY";
    public static final String POKEMON_CONFIGURATION_EXTRA_KEY = "LEFT_POKEMON_EXTRA_KEY";
    private static final int LEFT_POKEMON_REQUEST_CODE = 100;
    private static final int RIGHT_POKEMON_REQUEST_CODE = 101;
    private static final int MOVE_0_CHANGE_REQUEST_CODE = 102;
    private static final int MOVE_1_CHANGE_REQUEST_CODE = 103;
    private static final int MOVE_2_CHANGE_REQUEST_CODE = 104;
    private static final int MOVE_3_CHANGE_REQUEST_CODE = 105;

    private DamageCalculatorPresenter presenter;
    private CalculatorPokemonPresentation leftPresentation;
    private CalculatorPokemonPresentation rightPresentation;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( savedInstanceState == null ) {
            presenter.setLeftPokemon(
                    (PokemonConfig) getArguments().getParcelable( CONFIGURATION_ARGUMENT_KEY ) );
        }
    }

    @Override
    public Presenter setupPresenter() {
        if( presenter == null ) {
            presenter = AppWiring.damageCalculatorAssembler.getPresenterFactory()
                    .buildPresenter(this);
        }
        return presenter;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        leftPresentation = CalculatorPokemonPresentation.Builder
                .buildPresentation( getActivity(), presenter.getLeftPokemon() );
        rightPresentation = CalculatorPokemonPresentation.Builder
                .buildPresentation( getActivity(), presenter.getRightPokemon() );

        view.findViewById(R.id.left_pokemon_image_view).setOnClickListener(this);
        view.findViewById(R.id.right_pokemon_image_view).setOnClickListener(this);
        view.findViewById(R.id.attack_direction_image_view).setOnClickListener(this);
        view.findViewById(R.id.move_0_damage_result_view).setOnClickListener(this);
        view.findViewById(R.id.move_1_damage_result_view).setOnClickListener(this);
        view.findViewById(R.id.move_2_damage_result_view).setOnClickListener(this);
        view.findViewById(R.id.move_3_damage_result_view).setOnClickListener(this);
        FloatingActionMenu actionMenu = view.findViewById(R.id.damage_calculator_floating_save_menu);
        actionMenu.setListener( this );
        populateConfiguration( view );
        populateAttackDirection();
        requestDamageResults();
    }

    private void populateDamageResult( MoveDamageView rootView,
                                       MoveDamagePresentation presentation ) {
        rootView.setIsEmpty(presentation.empty);
        if( !presentation.empty ) {
            rootView.setMoveName( presentation.name );
            rootView.setMoveType( presentation.type );
            rootView.setMoveCategory( presentation.category.name );
            rootView.setMovePower( presentation.power );
            rootView.setMoveEffect( presentation.effect );
            rootView.setMoveResult( presentation.result );
        }
    }

    private void populateConfiguration( View rootView ) {
        ImageView leftView = rootView.findViewById(R.id.left_pokemon_image_view);
        TextView leftConfigurationName = rootView.findViewById(R.id.left_pokemon_configuration_name);
        if( !leftPresentation.empty ) {
            leftConfigurationName.setText( leftPresentation.name );
            GlideApp.with(this)
                    .load(leftPresentation.image)
                    .placeholder(R.drawable.icon_placeholder)
                    .into(leftView);
            leftView.setColorFilter(null);
        } else {
            leftView.setImageResource( R.drawable.ic_add_circle_black_24dp );
            leftView.setColorFilter(ContextCompat.getColor(
                    getActivity(), R.color.text_primary));
        }

        ImageView rightView = rootView.findViewById(R.id.right_pokemon_image_view);
        TextView rightConfigurationName = rootView.findViewById(R.id.right_pokemon_configuration_name);

        if( !rightPresentation.empty ) {
            rightConfigurationName.setText( rightPresentation.name );
            GlideApp.with(this)
                    .load(rightPresentation.image)
                    .placeholder(R.drawable.icon_placeholder)
                    .into(rightView);
            rightView.setColorFilter(null);
            rootView.findViewById( R.id.damage_results_frame_view ).setVisibility( View.VISIBLE );
            rootView.findViewById(R.id.empty_opponent_text).setVisibility( View.GONE );
            rootView.findViewById(R.id.configuration_floating_save_button_main)
                    .setVisibility(View.VISIBLE);
        } else {
            rightView.setImageResource( R.drawable.ic_add_circle_black_24dp );
            rightView.setColorFilter(ContextCompat.getColor(
                    getActivity(), R.color.text_primary));
            rootView.findViewById(R.id.empty_opponent_text).setVisibility( View.VISIBLE );
            rootView.findViewById( R.id.damage_results_frame_view ).setVisibility( View.GONE );
            rootView.findViewById(R.id.configuration_floating_save_button_main)
                    .setVisibility(View.GONE);
        }
    }

    private void requestDamageResults() {
        populateDamageResult( (MoveDamageView)getView().findViewById(R.id.move_0_damage_result_view),
                MoveDamagePresentation.Builder.buildPresentation(
                        getActivity(), presenter.getDamageResult( presenter.getMove0() ) ) );
        populateDamageResult( (MoveDamageView)getView().findViewById(R.id.move_1_damage_result_view),
                MoveDamagePresentation.Builder.buildPresentation(
                        getActivity(), presenter.getDamageResult( presenter.getMove1() ) ) );
        populateDamageResult( (MoveDamageView)getView().findViewById(R.id.move_2_damage_result_view),
                MoveDamagePresentation.Builder.buildPresentation(
                        getActivity(), presenter.getDamageResult( presenter.getMove2() ) ) );
        populateDamageResult( (MoveDamageView)getView().findViewById(R.id.move_3_damage_result_view),
                MoveDamagePresentation.Builder.buildPresentation(
                        getActivity(), presenter.getDamageResult( presenter.getMove3() ) ) );
    }

    private void changeAttackDirection() {
        if( presenter.isLeftRightDirection() ) {
            presenter.setAttackDirection( !presenter.isLeftRightDirection() );
            requestDamageResults();
        } else {
            presenter.setAttackDirection( !presenter.isLeftRightDirection() );
            requestDamageResults();
        }
    }

    private void populateAttackDirection() {
        if( presenter.isLeftRightDirection() ) {
            ImageView attackDirection = getView().findViewById(R.id.attack_direction_image_view);
            attackDirection.setRotationY(0);
        } else {
            ImageView attackDirection = getView().findViewById(R.id.attack_direction_image_view);
            attackDirection.setRotationY(180);
        }
    }

    @Override
    public int getLoadingFrame() {
        return R.id.base_request_loading_frame;
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.left_pokemon_image_view:
                startActivityForResult( SearchConfigurationActivity.getStartIntent(
                        getActivity(), presenter.getLeftPokemon() ) ,
                        LEFT_POKEMON_REQUEST_CODE );
                break;
            case R.id.right_pokemon_image_view:
                startActivityForResult( SearchConfigurationActivity.getStartIntent(
                        getActivity(), presenter.getRightPokemon() ) ,
                        RIGHT_POKEMON_REQUEST_CODE);
                break;
            case R.id.attack_direction_image_view:
                changeAttackDirection();
                populateAttackDirection();
                break;
            case R.id.move_0_damage_result_view:
                startActivityForResult( SearchMoveActivity.getStartIntent( getActivity(),
                        presenter.getMove0(), presenter.getPokemon().getId() ),
                        MOVE_0_CHANGE_REQUEST_CODE );
                break;
            case R.id.move_1_damage_result_view:
                startActivityForResult( SearchMoveActivity.getStartIntent( getActivity(),
                        presenter.getMove1(), presenter.getPokemon().getId() ),
                        MOVE_1_CHANGE_REQUEST_CODE );
                break;
            case R.id.move_2_damage_result_view:
                startActivityForResult( SearchMoveActivity.getStartIntent( getActivity(),
                        presenter.getMove2(), presenter.getPokemon().getId() ),
                        MOVE_2_CHANGE_REQUEST_CODE );
                break;
            case R.id.move_3_damage_result_view:
                startActivityForResult( SearchMoveActivity.getStartIntent( getActivity(),
                        presenter.getMove3(), presenter.getPokemon().getId() ),
                        MOVE_3_CHANGE_REQUEST_CODE );
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == Activity.RESULT_OK ) {
            if( requestCode == LEFT_POKEMON_REQUEST_CODE ) {
                presenter.setLeftPokemon( (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                leftPresentation = CalculatorPokemonPresentation.Builder
                        .buildPresentation( getActivity(), (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                populateConfiguration( getView() );
                requestDamageResults();
            } else if( requestCode == RIGHT_POKEMON_REQUEST_CODE ) {
                presenter.setRightPokemon( (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                rightPresentation = CalculatorPokemonPresentation.Builder
                        .buildPresentation( getActivity(), (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                populateConfiguration( getView() );
                requestDamageResults();
            } else if( requestCode == MOVE_0_CHANGE_REQUEST_CODE ) {
                Move move = data.getParcelableExtra(
                        ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY );
                presenter.updateMove0( move );
                populateDamageResult( (MoveDamageView)
                                getView().findViewById(R.id.move_0_damage_result_view),
                        MoveDamagePresentation.Builder.buildPresentation(
                                getActivity(), presenter.getDamageResult( move ) ) );
            } else if( requestCode == MOVE_1_CHANGE_REQUEST_CODE ) {
                Move move = data.getParcelableExtra(
                        ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY );
                presenter.updateMove1( move );
                populateDamageResult( (MoveDamageView)
                                getView().findViewById(R.id.move_1_damage_result_view),
                        MoveDamagePresentation.Builder.buildPresentation(
                                getActivity(), presenter.getDamageResult( move ) ) );
            } else if( requestCode == MOVE_2_CHANGE_REQUEST_CODE ) {
                Move move = data.getParcelableExtra(
                        ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY );
                presenter.updateMove2( move );
                populateDamageResult( (MoveDamageView)
                                getView().findViewById(R.id.move_2_damage_result_view),
                        MoveDamagePresentation.Builder.buildPresentation(
                                getActivity(), presenter.getDamageResult( move ) ) );
            } else if( requestCode == MOVE_3_CHANGE_REQUEST_CODE ) {
                Move move = data.getParcelableExtra(
                        ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY );
                presenter.updateMove3( move );
                populateDamageResult( (MoveDamageView)
                                getView().findViewById(R.id.move_3_damage_result_view),
                        MoveDamagePresentation.Builder.buildPresentation(
                                getActivity(), presenter.getDamageResult( move ) ) );
            }
        }
    }

    @Override
    public void onMenuOptionClicked(int id) {
        final Intent data = new Intent();

        switch ( id ) {
            case R.id.configuration_floating_save_button_left:
                startLoading(TRANSPARENT_LOADING_BACKGROUND);
                presenter.saveLeftConfiguration(new ResponseHandler<ConfigurationAction>() {
                    @Override
                    public void onSuccess(Response<ConfigurationAction> response) {
                        hideLoading();
                        data.putExtra( ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY,
                                presenter.getLeftPokemon() );
                        getActivity().setResult( Activity.RESULT_OK, data );
                        showResultToast( response.getData() );
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Error error) {
                        super.onError(error);
                        showSnackBackError(error);
                    }
                });
                break;
            case R.id.configuration_floating_save_button_right:
                startLoading(TRANSPARENT_LOADING_BACKGROUND);
                presenter.saveRightConfiguration(new ResponseHandler<ConfigurationAction>() {
                    @Override
                    public void onSuccess(Response<ConfigurationAction> response) {
                        hideLoading();
                        data.putExtra( ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY,
                                presenter.getRightPokemon() );
                        getActivity().setResult( Activity.RESULT_OK, data );
                        showResultToast( response.getData() );
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Error error) {
                        super.onError(error);
                        showSnackBackError(error);
                    }
                });
                break;
            case R.id.configuration_floating_save_button_middle:
                getActivity().setResult( Activity.RESULT_CANCELED );
                getActivity().finish();
                break;
        }
    }

    private void showResultToast( ConfigurationAction action ) {
        int textResource = View.NO_ID;
        switch (action) {
            case UPDATE:
                textResource = R.string.configuration_updated_toast_message;
                break;
            case NONE:
                textResource = R.string.configuration_not_changed_toast_message;
                break;
        }
        Toast toast = Toast.makeText(getActivity(),
                textResource,
                Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public Lifecycle screenLifecycle() {
        return getLifecycle();
    }
}
