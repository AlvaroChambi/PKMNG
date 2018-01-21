package es.developer.achambi.pkmng.modules.calculator;

import android.os.Bundle;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.model.EmptyPokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class DamageCalculatorPresenter implements ViewPresenter{
    private static final String LEFT_POKEMON_SAVED_DATA = "LEFT_POKEMON_SAVED_DATA";
    private static final String RIGHT_POKEMON_SAVED_DATA = "RIGHT_POKEMON_SAVED_DATA";
    private static final String ATTACK_DIRECTION_SAVED_DATA = "ATTACK_DIRECTION_SAVED_DATA";
    private PokemonConfig leftPokemon;
    private PokemonConfig rightPokemon;
    private boolean leftRightDirection;

    public DamageCalculatorPresenter() {
        leftPokemon = new EmptyPokemonConfig();
        rightPokemon = new EmptyPokemonConfig();
        leftRightDirection = true;
    }

    public PokemonConfig getLeftPokemon() {
        return leftPokemon;
    }

    public void setLeftPokemon(PokemonConfig leftPokemon) {
        this.leftPokemon = leftPokemon;
    }

    public PokemonConfig getRightPokemon() {
        return rightPokemon;
    }

    public void setRightPokemon(PokemonConfig rightPokemon) {
        this.rightPokemon = rightPokemon;
    }

    public boolean isLeftRightDirection() {
        return leftRightDirection;
    }

    public void setAttackDirection(boolean leftRightDirection) {
        this.leftRightDirection = leftRightDirection;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable( LEFT_POKEMON_SAVED_DATA, leftPokemon);
        bundle.putParcelable( RIGHT_POKEMON_SAVED_DATA, rightPokemon);
        bundle.putBoolean( ATTACK_DIRECTION_SAVED_DATA, leftRightDirection );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        leftPokemon = bundle.getParcelable( LEFT_POKEMON_SAVED_DATA );
        rightPokemon = bundle.getParcelable( RIGHT_POKEMON_SAVED_DATA );
        leftRightDirection = bundle.getBoolean( ATTACK_DIRECTION_SAVED_DATA );
    }
}
