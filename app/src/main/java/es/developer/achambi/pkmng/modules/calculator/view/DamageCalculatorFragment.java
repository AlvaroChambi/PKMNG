package es.developer.achambi.pkmng.modules.calculator.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseFragment;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.calculator.view.presentation.CalculatorPokemonPresentation;
import es.developer.achambi.pkmng.modules.calculator.presenter.DamageCalculatorPresenter;
import es.developer.achambi.pkmng.modules.calculator.view.presentation.MoveDamagePresentation;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.SearchFilter;
import es.developer.achambi.pkmng.modules.overview.view.SearchActivity;

public class DamageCalculatorFragment extends BaseFragment implements View.OnClickListener {
    private static final String CONFIGURATION_ARGUMENT_KEY = "CONFIGURATION_ARGUMENT_KEY";
    public static final String POKEMON_CONFIGURATION_EXTRA_KEY = "LEFT_POKEMON_EXTRA_KEY";
    private static final int LEFT_POKEMON_REQUEST_CODE = 100;
    private static final int RIGHT_POKEMON_REQUEST_CODE = 101;

    private DamageCalculatorPresenter presenter;
    private CalculatorPokemonPresentation leftPresentation;
    private CalculatorPokemonPresentation rightPresentation;
    private MoveDamagePresentation move0Presentation;

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
        move0Presentation = new PresentationBuilder().build();

        view.findViewById(R.id.left_pokemon_image_view).setOnClickListener(this);
        view.findViewById(R.id.right_pokemon_image_view).setOnClickListener(this);
        view.findViewById(R.id.attack_direction_image_view).setOnClickListener(this);
        populateConfiguration( view );
        populateAttackDirection();
        populateDamageResult( view.findViewById(R.id.move_0_damage_result_view),
                move0Presentation );
    }

    private void populateDamageResult( View rootView, MoveDamagePresentation presentation ) {
        TextView name = rootView.findViewById(R.id.move_damage_name_text);
        TextView type = rootView.findViewById(R.id.move_damage_type_text);
        TextView category = rootView.findViewById(R.id.move_damage_category_text);
        TextView power = rootView.findViewById(R.id.move_damage_power_text);
        TextView effect = rootView.findViewById(R.id.move_damage_effect_text);
        TextView result = rootView.findViewById(R.id.move_damage_result_text);

        name.setText( presentation.name );
        type.setText( presentation.type );
        category.setText( presentation.category );
        power.setText( presentation.power );
        effect.setText( presentation.effect );
        result.setText( presentation.result );
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
        } else {
            rightView.setImageResource( R.drawable.ic_add_circle_black_24dp );
            rightView.setColorFilter(ContextCompat.getColor(
                    getActivity(), R.color.text_primary));
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
                populateAttackDirection();
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
            } else if( requestCode == RIGHT_POKEMON_REQUEST_CODE ) {
                presenter.setRightPokemon( (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
                rightPresentation = builder.build( (PokemonConfig)data.getParcelableExtra(
                        POKEMON_CONFIGURATION_EXTRA_KEY ) );
            }
            populateConfiguration( getView() );
        }
    }

    public class PresentationBuilder {
        public CalculatorPokemonPresentation build( PokemonConfig pokemonConfig ) {
            return new CalculatorPokemonPresentation( pokemonConfig.getName(),
                    pokemonConfig.getId() == -1 );
        }

        public MoveDamagePresentation build(  ) {
            return new MoveDamagePresentation(
                    "Flamethrower",
                    "Fire", "Special", "Power 90",
                    "SuperEffective : x4.0",
                    "Guaranteed 1HKO  94.5 112%", false
            );
        }
    }
}
