package es.developer.achambi.pkmng.modules.utils;

public class PokemonUtils {
    private static final int FIXED_IV = 31;

    public static int getHpStatValue(int baseHP, int evValue, int level ) {
        float stat = (((2 * baseHP + FIXED_IV + (evValue/4) ) * level ) /
                100 ) + level + 10;
        return (int)stat;
    }

    public static int getStatValue(int baseStatValue, int evValue,
                                   float natureMultiplier, int level ) {
        float stat =  ((( ( 2 * baseStatValue + FIXED_IV + (evValue/4) ) * level ) /
                100 ) + 5 ) * natureMultiplier;
        return (int)stat;
    }
}
