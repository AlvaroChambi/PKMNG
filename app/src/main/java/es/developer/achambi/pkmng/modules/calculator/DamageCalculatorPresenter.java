package es.developer.achambi.pkmng.modules.calculator;

import android.os.Bundle;
import android.support.v4.util.Pair;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.calculator.model.Damage;
import es.developer.achambi.pkmng.modules.calculator.utils.DamageCalculator;
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

    public ArrayList<Damage> getDamageResults() {
        PokemonConfig attacker, attacked;
        if( leftRightDirection ) {
            attacker = leftPokemon;
            attacked = rightPokemon;
        } else {
            attacker = rightPokemon;
            attacked = leftPokemon;
        }
        ArrayList<Damage> damageResult = damagePerMove( attacker, attacked );
        return damageResult;
    }

    private ArrayList<Damage> damagePerMove( PokemonConfig attacker, PokemonConfig attacked ) {
        ArrayList<Damage> damageResult = new ArrayList<>();
        Pair<Float, Float> moveDamage0 = DamageCalculator.moveDamageResult( attacker.getAttack(),
                attacker.getSpAttack(), attacked.getDefense(), attacked.getSPDefense(),
                attacker.getConfiguration().getMove0() ) ;
        Pair<Float, Float> moveDamage1 = DamageCalculator.moveDamageResult( attacker.getAttack(),
                attacker.getSpAttack(), attacked.getDefense(), attacked.getSPDefense(),
                attacker.getConfiguration().getMove0() );
        Pair<Float, Float> moveDamage2 = DamageCalculator.moveDamageResult( attacker.getAttack(),
                attacker.getSpAttack(), attacked.getDefense(), attacked.getSPDefense(),
                attacker.getConfiguration().getMove0() );
        Pair<Float, Float> moveDamage3 = DamageCalculator.moveDamageResult( attacker.getAttack(),
                attacker.getSpAttack(), attacked.getDefense(), attacked.getSPDefense(),
                attacker.getConfiguration().getMove0() );

        float modifierMove0 = DamageCalculator.modifierValue( attacker, attacked,
                attacker.getConfiguration().getMove0() );
        float modifierMove1 = DamageCalculator.modifierValue( attacker, attacked,
                attacker.getConfiguration().getMove1() );
        float modifierMove2 = DamageCalculator.modifierValue( attacker, attacked,
                attacker.getConfiguration().getMove2() );
        float modifierMove3 = DamageCalculator.modifierValue( attacker, attacked,
                attacker.getConfiguration().getMove3() );

        int hitsToKOMove0 = DamageCalculator.hitsToKO( moveDamage0, attacked.getHP() );
        int hitsToKOMove1 = DamageCalculator.hitsToKO( moveDamage1, attacked.getHP() );
        int hitsToKOMove2 = DamageCalculator.hitsToKO( moveDamage2, attacked.getHP() );
        int hitsToKOMove3 = DamageCalculator.hitsToKO( moveDamage3, attacked.getHP() );

        damageResult.add( new Damage( moveDamage0, hitsToKOMove0, modifierMove0 ) );
        damageResult.add( new Damage( moveDamage1, hitsToKOMove1, modifierMove1 ) );
        damageResult.add( new Damage( moveDamage2, hitsToKOMove2, modifierMove2 ) );
        damageResult.add( new Damage( moveDamage3, hitsToKOMove3, modifierMove3 ) );
        return damageResult;
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
