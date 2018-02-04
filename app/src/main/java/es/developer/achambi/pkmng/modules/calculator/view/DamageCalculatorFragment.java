package es.developer.achambi.pkmng.modules.calculator.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.core.ui.FloatingActionMenu;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.calculator.model.Damage;
import es.developer.achambi.pkmng.modules.calculator.view.presentation.CalculatorPokemonPresentation;
import es.developer.achambi.pkmng.modules.calculator.presenter.DamageCalculatorPresenter;
import es.developer.achambi.pkmng.modules.calculator.view.presentation.MoveDamagePresentation;
import es.developer.achambi.pkmng.modules.create.view.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.overview.view.SearchActivity;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.view.SearchMoveActivity;

public class DamageCalculatorFragment extends BaseFragment implements View.OnClickListener,
        FloatingActionMenu.MenuOptionClickedListener {
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
        setupPresenter();
        if( savedInstanceState == null ) {
            presenter.setLeftPokemon(
                    (PokemonConfig) getArguments().getParcelable( CONFIGURATION_ARGUMENT_KEY ) );
        }
    }

    @Override
    public ViewPresenter setupPresenter() {
        if( presenter == null ) {
            presenter = new DamageCalculatorPresenter();
        }
        return presenter;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        leftPresentation = new PresentationBuilder().build( presenter.getLeftPokemon() );
        rightPresentation = new PresentationBuilder().build( presenter.getRightPokemon() );

        view.findViewById(R.id.left_pokemon_image_view).setOnClickListener(this);
        view.findViewById(R.id.right_pokemon_image_view).setOnClickListener(this);
        view.findViewById(R.id.attack_direction_image_view).setOnClickListener(this);
        view.findViewById(R.id.move_0_damage_result_view).setOnClickListener(this);
        view.findViewById(R.id.move_1_damage_result_view).setOnClickListener(this);
        view.findViewById(R.id.move_2_damage_result_view).setOnClickListener(this);
        view.findViewById(R.id.move_3_damage_result_view).setOnClickListener(this);
        FloatingActionMenu actionMenu = view.findViewById(R.id.configuration_floating_save_menu);
        actionMenu.setListener( this );
        populateConfiguration( view );
        populateAttackDirection();
        requestDamageResults();
    }

    private void populateDamageResult( MoveDamageView rootView,
                                       MoveDamagePresentation presentation ) {
        if( !presentation.empty ) {
            rootView.setIsEmpty(presentation.empty);
            rootView.setMoveName( presentation.name );
            rootView.setMoveType( presentation.type );
            rootView.setMoveCategory( presentation.category );
            rootView.setMovePower( presentation.power );
            rootView.setMoveEffect( presentation.effect );
            rootView.setMoveResult( presentation.result );
        }
    }

    private void populateConfiguration( View rootView ) {
        ImageView leftView = rootView.findViewById(R.id.left_pokemon_image_view);
        if( !leftPresentation.empty ) {
            TextView leftConfigurationName = rootView.findViewById(R.id.left_pokemon_configuration_name);
            leftConfigurationName.setText( leftPresentation.name );
            leftView.setImageResource( R.drawable.pokemon_placeholder );
            leftView.setColorFilter(null);
        } else {
            leftView.setImageResource( R.drawable.ic_add_circle_black_24dp );
            leftView.setColorFilter(ContextCompat.getColor(
                    getActivity(), R.color.text_primary));
        }

        ImageView rightView = rootView.findViewById(R.id.right_pokemon_image_view);
        if( !rightPresentation.empty ) {
            TextView rightConfigurationName = rootView.findViewById(R.id.right_pokemon_configuration_name);
            rightConfigurationName.setText( rightPresentation.name );
            rightView.setImageResource( R.drawable.pokemon_placeholder );
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
        PresentationBuilder builder = new PresentationBuilder();
        populateDamageResult( (MoveDamageView)getView().findViewById(R.id.move_0_damage_result_view),
                builder.build( presenter.getDamageResult( presenter.getMove0() ) ) );
        populateDamageResult( (MoveDamageView)getView().findViewById(R.id.move_1_damage_result_view),
                builder.build( presenter.getDamageResult( presenter.getMove1() ) ) );
        populateDamageResult( (MoveDamageView)getView().findViewById(R.id.move_2_damage_result_view),
                builder.build( presenter.getDamageResult( presenter.getMove2() ) ) );
        populateDamageResult( (MoveDamageView)getView().findViewById(R.id.move_3_damage_result_view),
                builder.build( presenter.getDamageResult( presenter.getMove3() ) ) );
    }

    private void requestDamageResults( int orientation ) {
        requestDamageResults();
        ((MoveDamageView)getView().findViewById(R.id.move_0_damage_result_view))
                .setOrientation(orientation);
        ((MoveDamageView)getView().findViewById(R.id.move_1_damage_result_view))
                .setOrientation(orientation);
        ((MoveDamageView)getView().findViewById(R.id.move_2_damage_result_view))
                .setOrientation(orientation);
        ((MoveDamageView)getView().findViewById(R.id.move_3_damage_result_view))
                .setOrientation(orientation);
    }

    private void changeAttackDirection() {
        if( presenter.isLeftRightDirection() ) {
            presenter.setAttackDirection( !presenter.isLeftRightDirection() );
            ImageView attackDirection = getView().findViewById(R.id.attack_direction_image_view);
            attackDirection.setRotationY(0);
            requestDamageResults( MoveDamageView.RIGHT_ORIENTATION );
        } else {
            presenter.setAttackDirection( !presenter.isLeftRightDirection() );
            ImageView attackDirection = getView().findViewById(R.id.attack_direction_image_view);
            attackDirection.setRotationY(180);
            requestDamageResults( MoveDamageView.LEFT_ORIENTATION );
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
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.left_pokemon_image_view:
                startActivityForResult(SearchActivity.getStartIntent(
                        getActivity(), SearchFilter.CONFIGURATION_FILTER) ,
                        LEFT_POKEMON_REQUEST_CODE);
                break;
            case R.id.right_pokemon_image_view:
                startActivityForResult(SearchActivity.getStartIntent(
                        getActivity(), SearchFilter.CONFIGURATION_FILTER) ,
                        RIGHT_POKEMON_REQUEST_CODE);
                break;
            case R.id.attack_direction_image_view:
                presenter.setAttackDirection( !presenter.isLeftRightDirection() );
                changeAttackDirection();
                break;
            case R.id.move_0_damage_result_view:
                startActivityForResult( SearchMoveActivity.getStartIntent( getActivity() ),
                        MOVE_0_CHANGE_REQUEST_CODE );
                break;
            case R.id.move_1_damage_result_view:
                startActivityForResult( SearchMoveActivity.getStartIntent( getActivity() ),
                        MOVE_1_CHANGE_REQUEST_CODE );
                break;
            case R.id.move_2_damage_result_view:
                startActivityForResult( SearchMoveActivity.getStartIntent( getActivity() ),
                        MOVE_2_CHANGE_REQUEST_CODE );
                break;
            case R.id.move_3_damage_result_view:
                startActivityForResult( SearchMoveActivity.getStartIntent( getActivity() ),
                        MOVE_3_CHANGE_REQUEST_CODE );
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == Activity.RESULT_OK ) {
            PresentationBuilder builder = new PresentationBuilder();
            if( requestCode == LEFT_POKEMON_REQUEST_CODE ) {
                presenter.setLeftPokemon( (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                leftPresentation = builder.build( (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                populateConfiguration( getView() );
                requestDamageResults();
            } else if( requestCode == RIGHT_POKEMON_REQUEST_CODE ) {
                presenter.setRightPokemon( (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                rightPresentation = builder.build( (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                populateConfiguration( getView() );
                requestDamageResults();
            } else if( requestCode == MOVE_0_CHANGE_REQUEST_CODE ) {
                Move move = data.getParcelableExtra(
                        ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY );
                presenter.updateMove0( move );
                populateDamageResult( (MoveDamageView)
                                getView().findViewById(R.id.move_0_damage_result_view),
                        builder.build( presenter.getDamageResult( move ) ) );
            } else if( requestCode == MOVE_1_CHANGE_REQUEST_CODE ) {
                Move move = data.getParcelableExtra(
                        ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY );
                presenter.updateMove1( move );
                populateDamageResult( (MoveDamageView)
                                getView().findViewById(R.id.move_1_damage_result_view),
                        builder.build( presenter.getDamageResult( move ) ) );
            } else if( requestCode == MOVE_2_CHANGE_REQUEST_CODE ) {
                Move move = data.getParcelableExtra(
                        ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY );
                presenter.updateMove2( move );
                populateDamageResult( (MoveDamageView)
                                getView().findViewById(R.id.move_2_damage_result_view),
                        builder.build( presenter.getDamageResult( move ) ) );
            } else if( requestCode == MOVE_3_CHANGE_REQUEST_CODE ) {
                Move move = data.getParcelableExtra(
                        ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY );
                presenter.updateMove3( move );
                populateDamageResult( (MoveDamageView)
                                getView().findViewById(R.id.move_3_damage_result_view),
                        builder.build( presenter.getDamageResult( move ) ) );
            }
        }
    }

    @Override
    public void onMenuOptionClicked(int id) {
        Intent data = new Intent();
        Toast updatedToast = Toast.makeText(getActivity(),
                R.string.configuration_updated_toast_message,
                Toast.LENGTH_SHORT);
        switch ( id ) {
            case R.id.configuration_floating_save_button_left:
                presenter.saveLeftConfiguration();
                data.putExtra( ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY,
                        presenter.getLeftPokemon() );
                getActivity().setResult( Activity.RESULT_OK, data );
                updatedToast.show();
                getActivity().finish();
                break;
            case R.id.configuration_floating_save_button_right:
                presenter.saveRightConfiguration();
                data.putExtra( ConfigurationFragment.POKEMON_CONFIG_RESULT_DATA_KEY,
                        presenter.getRightPokemon() );
                getActivity().setResult( Activity.RESULT_OK, data );
                updatedToast.show();
                getActivity().finish();
                break;
            case R.id.configuration_floating_save_button_middle:
                getActivity().setResult( Activity.RESULT_CANCELED );
                presenter.saveBothConfigurations();
                getActivity().finish();
                break;
        }
    }

    public class PresentationBuilder {
        public CalculatorPokemonPresentation build( PokemonConfig pokemonConfig ) {
            return new CalculatorPokemonPresentation( pokemonConfig.getName(),
                    pokemonConfig.getId() == -1 );
        }

        public MoveDamagePresentation build( Damage damage ) {
            boolean empty = damage.getMoveName().equals("");
            if(empty) {
                return buildEmpty();
            } else {
                return new MoveDamagePresentation(
                        damage.getMoveName(),
                        formatType( damage.getType() ),
                        formatCategory( damage.getCategory() ),
                        "Power " + damage.getPower(),
                        buildEffectivenessText( damage.getEffectivenessModifier() ),
                        formatDamage( damage ),
                        damage.getMoveName().equals("") );
            }
        }

        private String buildEffectivenessText( float modifier ) {
            String result = "";
            if( modifier >= 2 ) {
                result = "SuperEffective: x" + modifier;
            } else if( modifier == 1 ) {
                result = "Effective: x" + modifier;
            } else if ( modifier < 1 ) {
                result = "Not Effective: x" + modifier;
            }
            return result;
        }

        private MoveDamagePresentation buildEmpty() {
            return new MoveDamagePresentation("", "", "", "", "", "", true);
        }

        private String formatDamage( Damage damage ) {
            Pair<Float, Float> damageResult = damage.getMoveDamage();
            return "Guaranteed " + damage.getHitsToKO() + "HKO  " +
                    round( damageResult.first, 2 ) + " ~ " +
                    round( damageResult.second, 2 );
        }

        private float round(float d, int decimalPlace) {
            BigDecimal bd = new BigDecimal(Float.toString(d));
            bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
            return bd.floatValue();
        }

        private String formatCategory( Move.Category category ) {
            switch (category) {
                case SPECIAL:
                    return "Special";
                case PHYSICAL:
                    return "Physical";
            }
            return "";
        }

        private String formatType( Type type ) {
            switch ( type ) {
                case GROUND:
                    return "Ground";
                case ELECTRIC:
                    return "Electric";
            }
            return "";
        }
    }
}
