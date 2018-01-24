package es.developer.achambi.pkmng.modules.calculator.utils;

import org.junit.Before;
import org.junit.Test;

import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class DamageCalculatorTest {
    private Move move;
    @Before
    public void setup() {
        move = new Move();
        move.setPower( 90 );
        move.setCategory( Move.Category.PHYSICAL );
    }

    @Test(expected = IllegalStateException.class)
    public void moveDamageResultEmptyCategory() {
        int statValue = 90;
        move.setCategory( Move.Category.EMPTY );
        DamageCalculator.moveDamageResult( statValue, statValue, statValue, statValue, move );
    }

    @Test(expected = IllegalStateException.class)
    public void moveDamageResultInvalidPower() {
        int statValue = 90;
        move.setPower( 0 );
        DamageCalculator.moveDamageResult( statValue, statValue, statValue, statValue, move );
    }

    @Test(expected = IllegalStateException.class)
    public void moveDamageResultInvalidStats() {
        int statValue = 0;
        DamageCalculator.moveDamageResult( statValue, statValue, statValue, statValue, move );
    }

    @Test
    public void physicalAttackDamage(){

    }

    @Test
    public void specialAttackDamage() {

    }

    @Test
    public void hitsToKO() throws Exception {
        //input : Pair<Float, Float> moveDamage : null Float
        //                                      : 0 value
        //                                      : negative value
        //input : int hp : 0 value
        //               : negative value
    }

    @Test
    public void modifierValue() throws Exception {
        //input : attacker type & move type
    }

    @Test
    public void stabModifier() {
        //input : Pair<Type, Type> attackerType : null / empty values
        //input : Type moveType : null / empty value
        //input : Ability ability : null / not valid value
    }

    @Test
    public void burnModifier() {
        //input : Ability ability : invalid input
        //input : MoveCategory category : invalid input
        //input : boolean isBurned
    }

    @Test
    public void typeModifier() {
        //input : Pair<Type, Type> attackedType : null / empty values
        //input : Type moveType : invalid values
    }

    @Test
    public void weatherModifier() {
        //input : Weather weather : invalid values
        //input : Type moveType : invalid values
    }

    @Test
    public void moveModifier() {
        //Aurora veil
        //Light Screen
        //Reflect
        //input : Move move : invalid values
        //input : Category moveCategory : invalid values
    }
}