package es.developer.achambi.pkmng.modules.calculator.utils;

import android.support.v4.util.Pair;

import org.junit.Before;
import org.junit.Test;

import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

import static junit.framework.Assert.assertEquals;

public class DamageCalculatorTest {
    private Move move;
    private int level;

    @Before
    public void setup() {
        move = new Move();
        move.setPower( 90 );
        move.setCategory( Move.Category.PHYSICAL );
        level = 50;
    }

    @Test(expected = IllegalStateException.class)
    public void moveDamageResultEmptyCategory() {
        int statValue = 90;
        move.setCategory( Move.Category.EMPTY );
        DamageCalculator.moveDamageResult( statValue, statValue, statValue, statValue,
                level, move );
    }

    @Test(expected = IllegalStateException.class)
    public void moveDamageResultInvalidPower() {
        int statValue = 90;
        move.setPower( 0 );
        DamageCalculator.moveDamageResult( statValue, statValue, statValue, statValue,
                level, move );
    }

    @Test(expected = IllegalStateException.class)
    public void moveDamageResultInvalidStats() {
        int statValue = 0;
        DamageCalculator.moveDamageResult( statValue, statValue, statValue, statValue, level,
                move );
    }

    @Test(expected = IllegalStateException.class)
    public void moveDamageResultNonDamagingMove() {
        int statValue = 90;
        move.setCategory( Move.Category.NON_DAMAGING );
        DamageCalculator.moveDamageResult( statValue, statValue, statValue, statValue, level,
                move );
    }

    @Test
    public void specialAttackResult() {
        move.setCategory( Move.Category.SPECIAL );
        int specialAttack = 100;
        int specialDefense = 50;
        int emptyStat = 10;
        Pair<Float, Float> damage = DamageCalculator.moveDamageResult( emptyStat, specialAttack,
                emptyStat, specialDefense, level, move );
        assertEquals( damage.first, 68.85f );
        assertEquals( damage.second, 81.0f );
    }

    @Test
    public void physicalAttackResult() {
        move.setCategory( Move.Category.PHYSICAL );
        int attack = 100;
        int defense = 50;
        int emptyStat = 10;
        Pair<Float, Float> damage = DamageCalculator.moveDamageResult( attack, emptyStat,
                defense, emptyStat, level, move );
        assertEquals( damage.first, 68.85f );
        assertEquals( damage.second, 81.0f );
    }

    @Test( expected = IllegalStateException.class )
    public void hitsToKONegativeDamage() {
        Pair<Float, Float> damage = new Pair<>( -10f, -10f );
        int hp = 100;
        DamageCalculator.hitsToKO( damage, hp );
    }

    @Test
    public void hitsToKOZeroDamage() {
        Pair<Float, Float> damage = new Pair<>( 0f, 0f );
        int hp = 100;
        int result = DamageCalculator.hitsToKO( damage, hp );
        assertEquals( 0, result );
    }

    @Test( expected = IllegalStateException.class )
    public void hitsToKOZeroHP() {
        Pair<Float, Float> damage = new Pair<>( 10f, 10f );
        int hp = 0;
        DamageCalculator.hitsToKO( damage, hp );
    }

    @Test( expected = IllegalStateException.class )
    public void hitsToKONegativeHP() {
        Pair<Float, Float> damage = new Pair<>( 10f, 10f );
        int hp = -10;
        DamageCalculator.hitsToKO( damage, hp );
    }

    @Test
    public void hitsToKOValidValue() {
        Pair<Float, Float> damage = new Pair<>( 30f, 30f );
        int hp = 100;
        int result = DamageCalculator.hitsToKO( damage, hp );
        assertEquals( 4, result );
    }

    @Test( expected = IllegalStateException.class )
    public void stabModifierEmptyAttackerType() {
        Pair<Type, Type> empty = new Pair<>( Type.EMPTY, Type.EMPTY );
        DamageCalculator.stabModifier( empty, Type.BUG, new Ability() );
    }

    @Test( expected = IllegalStateException.class )
    public void stabModifierEmptyMoveType() {
        Pair<Type, Type> empty = new Pair<>( Type.ELECTRIC, Type.EMPTY );
        DamageCalculator.stabModifier( empty, Type.EMPTY, new Ability() );
    }

    @Test
    public void stabModifierValidWithAdaptability() {
        Pair<Type, Type> type = new Pair<>( Type.ELECTRIC, Type.EMPTY );
        Ability adaptability = new Ability();
        adaptability.setName(Ability.ADAPTABILITY);
        float result = DamageCalculator.stabModifier( type, Type.ELECTRIC, adaptability );
        assertEquals( 2.0f, result );
    }

    @Test
    public void stabModifierValid() {
        Pair<Type, Type> type = new Pair<>( Type.GRASS, Type.ELECTRIC );
        Ability ability = new Ability();
        float result = DamageCalculator.stabModifier( type, Type.ELECTRIC, ability );
        assertEquals( 1.5f, result );
    }

    @Test( expected = IllegalStateException.class )
    public void burnModifierEmptyCategory() {
        DamageCalculator.burnModifier( Move.Category.EMPTY, new Ability(), false );
    }

    @Test
    public void burnModifierSpecialCategory() {
        float result = DamageCalculator.burnModifier( Move.Category.SPECIAL, new Ability(), true );
        assertEquals( 1.0f, result );
    }

    @Test
    public void burnModifierNotBurnedState() {
        float result = DamageCalculator.burnModifier( Move.Category.PHYSICAL,
                new Ability(), false );
        assertEquals( 1.0f, result );
    }

    @Test
    public void burnModifierBurnedState() {
        float result = DamageCalculator.burnModifier( Move.Category.PHYSICAL,
                new Ability(), true );
        assertEquals( 0.5f, result );
    }

    @Test
    public void burnModifierBurnedWithGuts() {
        Ability guts = new Ability();
        guts.setName(Ability.GUTS);
        float result = DamageCalculator.burnModifier( Move.Category.PHYSICAL,
                guts, true );
        assertEquals( 1.0f, result );
    }

    @Test
    public void abilityModifierSolidRockSuperEffective() {
        Ability ability = new Ability();
        ability.setName("Solid Rock");
        float superEffective = 2.0f;
        float result = DamageCalculator.abilityModifier( ability, superEffective );

        assertEquals( 0.75f, result );
    }

    @Test
    public void abilityModifierSolidRock() {
        Ability ability = new Ability();
        ability.setName("Solid Rock");
        float effective = 1.0f;
        float result = DamageCalculator.abilityModifier( ability, effective );

        assertEquals( 1.0f, result );
    }

    @Test
    public void abilityModifierTintedLensEffective() {
        Ability ability = new Ability();
        ability.setName("Tinted Lens");
        float effective = 1.0f;
        float result = DamageCalculator.abilityModifier( ability, effective );

        assertEquals( 1.0f, result );
    }

    @Test
    public void abilityModifierTintedLensNotEffective() {
        Ability ability = new Ability();
        ability.setName("Tinted Lens");
        float notEffective = 0.5f;
        float result = DamageCalculator.abilityModifier( ability, notEffective );

        assertEquals( 2.0f, result );
    }

    @Test
    public void moveModifierFluffyFireContact() {
        Ability ability = new Ability();
        ability.setName( "Fluffy" );
        Move move = new Move();
        move.setContact( true );
        move.setType( Type.FIRE );

        float result = DamageCalculator.moveModifier( move, ability );

        assertEquals( 1.0f, result );
    }

    @Test
    public void moveModifierFluffyFireNotContact() {
        Ability ability = new Ability();
        ability.setName( "Fluffy" );
        Move move = new Move();
        move.setContact( false );
        move.setType( Type.FIRE );

        float result = DamageCalculator.moveModifier( move, ability );

        assertEquals( 2.0f, result );
    }

    @Test
    public void moveModifierFluffyNotFireContact() {
        Ability ability = new Ability();
        ability.setName( "Fluffy" );
        Move move = new Move();
        move.setContact( true );
        move.setType( Type.WATER );

        float result = DamageCalculator.moveModifier( move, ability );

        assertEquals( 0.5f, result );
    }
}