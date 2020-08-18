package es.developer.achambi.pkmng.modules.utils;

import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class PokemonUtils {
    private static final int FIXED_IV = 31;
    private static final int MAX_LEVEL = 100;

    public static int getHpStatValue(int baseHP, int evValue, int level )
            throws IllegalArgumentException{
        if(baseHP < 1) {
            throw new IllegalArgumentException();
        }
        if(evValue < 0) {
            throw new IllegalArgumentException();
        }
        if(level < 1 || level > MAX_LEVEL) {
            throw new IllegalArgumentException();
        }
        float stat = (((2 * baseHP + FIXED_IV + (evValue/4) ) * level ) /
                100 ) + level + 10;
        return (int)stat;
    }

    public static int getStatValue(int baseStatValue, int evValue,
                                   float natureMultiplier, int level )
            throws IllegalArgumentException{
        if(baseStatValue < 1) {
            throw new IllegalArgumentException();
        }
        if(evValue < 0) {
            throw new IllegalArgumentException();
        }
        if(level < 1 || level > MAX_LEVEL) {
            throw new IllegalArgumentException();
        }
        if( natureMultiplier == Nature.INCREASED_STAT_MODIFIER
                || natureMultiplier == Nature.DECREASED_STAT_MODIFIER
                || natureMultiplier == Nature.NEUTRAL_STAT_MODIFIER ) {
            float stat =  ((( ( 2 * baseStatValue + FIXED_IV + (evValue/4) ) * level ) /
                    100 ) + 5 ) * natureMultiplier;
            return (int)stat;
        }
        throw new IllegalArgumentException();
    }

    public static int getStatValue(int baseStatValue, int evValue,
                                   float natureMultiplier, int level, int iv )
            throws IllegalArgumentException{
        if(baseStatValue < 1) {
            throw new IllegalArgumentException();
        }
        if(evValue < 0) {
            throw new IllegalArgumentException();
        }
        if(level < 1 || level > MAX_LEVEL) {
            throw new IllegalArgumentException();
        }
        if( natureMultiplier == Nature.INCREASED_STAT_MODIFIER
                || natureMultiplier == Nature.DECREASED_STAT_MODIFIER
                || natureMultiplier == Nature.NEUTRAL_STAT_MODIFIER ) {
            float stat =  ((( ( 2 * baseStatValue + iv + (evValue/4) ) * level ) /
                    100 ) + 5 ) * natureMultiplier;
            return (int)stat;
        }
        throw new IllegalArgumentException();
    }
}
