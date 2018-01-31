package es.developer.achambi.pkmng.modules.calculator.presenter;

import android.os.Bundle;
import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.calculator.model.Damage;
import es.developer.achambi.pkmng.modules.calculator.utils.DamageCalculator;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.EmptyPokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class DamageCalculatorPresenter implements ViewPresenter {
    private static final String LEFT_POKEMON_SAVED_DATA = "LEFT_POKEMON_SAVED_DATA";
    private static final String RIGHT_POKEMON_SAVED_DATA = "RIGHT_POKEMON_SAVED_DATA";
    private static final String ATTACK_DIRECTION_SAVED_DATA = "ATTACK_DIRECTION_SAVED_DATA";
    private static final String EDITABLE_CONFIGURATION_SAVED_DATA = "EDITABLE_SAVED_DATA";
    private PokemonConfig leftPokemon;
    private PokemonConfig rightPokemon;
    private boolean leftRightDirection;
    private Configuration editableConfiguration;

    public DamageCalculatorPresenter() {
        leftPokemon = new EmptyPokemonConfig();
        rightPokemon = new EmptyPokemonConfig();
        leftRightDirection = true;
        editableConfiguration = new Configuration();
    }

    public PokemonConfig getLeftPokemon() {
        return leftPokemon;
    }

    public void setLeftPokemon(PokemonConfig leftPokemon) {
        this.leftPokemon = leftPokemon;
        this.editableConfiguration = leftPokemon.getConfiguration();
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

    public Damage getDamageResult( Move move ) {
        PokemonConfig attacker, attacked;
        if( leftRightDirection ) {
            attacker = leftPokemon;
            attacked = rightPokemon;
        } else {
            attacker = rightPokemon;
            attacked = leftPokemon;
        }
        if( attacked.getId() == BasePokemon.EMPTY_ID || move.getId() == BasePokemon.EMPTY_ID ) {
            return new Damage();
        }
        Damage damageResult = damageResult( attacker, attacked, move );
        return damageResult;
    }

    private Damage damageResult( PokemonConfig attacker, PokemonConfig attacked, Move move ) {
        Pair<Float, Float> moveDamage = DamageCalculator.moveDamageResult( attacker.getAttack(),
                attacker.getSpAttack(), attacked.getDefense(), attacked.getSPDefense(), move ) ;
        float modifier = DamageCalculator.modifierValue( attacker, attacked, move );
        int hitsToKO = DamageCalculator.hitsToKO( moveDamage, attacked.getHP() );

        Damage damage = new Damage( move );
        damage.setMoveDamage( moveDamage );
        damage.setModifier( modifier );
        damage.setHitsToKO( hitsToKO );

        return damage;
    }

    public Move getMove0() {
        return editableConfiguration.getMove0();
    }

    public void updateMove0( Move move ) {
        editableConfiguration.setMove0( move );
    }

    public Move getMove1() {
        return editableConfiguration.getMove1();
    }

    public void updateMove1( Move move ) {
        editableConfiguration.setMove1( move );
    }

    public Move getMove2() {
        return editableConfiguration.getMove2();
    }

    public void updateMove2( Move move ) {
        editableConfiguration.setMove2( move );
    }

    public Move getMove3() {
        return editableConfiguration.getMove3();
    }

    public void updateMove3( Move move ) {
        editableConfiguration.setMove3( move );
    }

    public Configuration getEditableConfiguration() {
        return editableConfiguration;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable( LEFT_POKEMON_SAVED_DATA, leftPokemon);
        bundle.putParcelable( RIGHT_POKEMON_SAVED_DATA, rightPokemon);
        bundle.putBoolean( ATTACK_DIRECTION_SAVED_DATA, leftRightDirection );
        bundle.putParcelable( EDITABLE_CONFIGURATION_SAVED_DATA, editableConfiguration );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        leftPokemon = bundle.getParcelable( LEFT_POKEMON_SAVED_DATA );
        rightPokemon = bundle.getParcelable( RIGHT_POKEMON_SAVED_DATA );
        leftRightDirection = bundle.getBoolean( ATTACK_DIRECTION_SAVED_DATA );
        editableConfiguration = bundle.getParcelable( EDITABLE_CONFIGURATION_SAVED_DATA );
    }
}
