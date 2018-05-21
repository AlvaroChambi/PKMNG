package es.developer.achambi.pkmng.modules.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class PokemonUtilsTest {
    private static final int LEVEL = 50;
    private static final float INCREASED_STAT_MODIFIER = 1.1f;
    private static final float DECREASED_STAT_MODIFIER = 0.9f;
    private static final float NEUTRAL_STAT_MODIFIER = 1.0f;

    @Test
    public void getHpStatValue() {
        int baseHP = 120;
        int evValue = 240;
        int hpTotal = PokemonUtils.getHpStatValue( baseHP, evValue, LEVEL );

        assertEquals( 225, hpTotal );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHpStatInvalidBaseHP() {
        int baseHP = 0;
        int evValue = 240;
        PokemonUtils.getHpStatValue( baseHP, evValue, LEVEL );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHpStatInvalidEvValue() {
        int baseHP = 120;
        int evValue = -1;
        PokemonUtils.getHpStatValue( baseHP, evValue, LEVEL );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getHpStatInvalidLevel() {
        int baseHP = 120;
        int evValue = 240;
        PokemonUtils.getHpStatValue( baseHP, evValue, 0 );
    }

    @Test
    public void getStatValueNeutralNature() {
        int baseStat = 120;
        int evValue = 240;
        float natureMultiplier = NEUTRAL_STAT_MODIFIER;

        int statTotal = PokemonUtils.getStatValue( baseStat, evValue, natureMultiplier, LEVEL );

        assertEquals( 170, statTotal );
    }

    @Test
    public void getStatValueIncreasedNature() {
        int baseStat = 120;
        int evValue = 240;
        float natureMultiplier = INCREASED_STAT_MODIFIER;

        int statTotal = PokemonUtils.getStatValue( baseStat, evValue, natureMultiplier, LEVEL );

        assertEquals( 187, statTotal );
    }

    @Test
    public void getStatValueDecreasedNature() {
        int baseStat = 120;
        int evValue = 240;
        float natureMultiplier = DECREASED_STAT_MODIFIER;

        int statTotal = PokemonUtils.getStatValue( baseStat, evValue, natureMultiplier, LEVEL );

        assertEquals( 153, statTotal );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStatValueInvalidBaseStat() {
        PokemonUtils.getStatValue( 0, 100, NEUTRAL_STAT_MODIFIER, LEVEL );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStatValueInvalidEvValue() {
        PokemonUtils.getStatValue( 100, -1, NEUTRAL_STAT_MODIFIER, LEVEL );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStatValueInvalidLevel() {
        PokemonUtils.getStatValue( 100, 100,NEUTRAL_STAT_MODIFIER, 110  );
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStatValueInvalidNatureMultiplier() {
        PokemonUtils.getStatValue( 100, 100, 2, LEVEL );
    }
}