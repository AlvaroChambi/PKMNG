package es.developer.achambi.pkmng.modules.data.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataFormatUtilTest {
    private DataFormatUtil dataFormatUtil;

    private String typeTokenMessage;
    private String pokemonTokenMessage;
    private String mechanicTokenMessage;
    private String effectChanceTokenMessage;

    @Before
    public void setup() {
        dataFormatUtil = new DataFormatUtil();
        typeTokenMessage = "Held: When the holder uses a damaging []{type:fire}-type move, the move has 1.5× power and this item is consumed.";
        mechanicTokenMessage = "Has a chance to [poison]{mechanic:poison} the target";
        pokemonTokenMessage = "Can be revived into a []{pokemon:archen}.";
        effectChanceTokenMessage = "Has a $effect_chance%chance to burn";
    }

    @Test
    public void overrideTypeToken() {
        String expected = "Held: When the holder uses a damaging fire-type move, the move has 1.5× power and this item is consumed.";
        String result = dataFormatUtil.formatDescriptionMessage( typeTokenMessage );

        assertEquals(expected, result);
    }

    @Test
    public void overrideMechanicToken() {
        String expected = "Has a chance to poison the target";
        String result = dataFormatUtil.formatDescriptionMessage( mechanicTokenMessage );

        assertEquals( expected, result );
    }

    @Test
    public void overridePokemonToken() {
        String expected = "Can be revived into a archen.";
        String result = dataFormatUtil.formatDescriptionMessage( pokemonTokenMessage );

        assertEquals( expected, result );
    }

    @Test
    public void overridePokemonEffectChance() {
        String expected = "Has a chance to burn";
        String result = dataFormatUtil.formatDescriptionMessage( effectChanceTokenMessage );

        assertEquals( expected, result );
    }

    @Test
    public void overrideMultipleToken() {
        String multipleTokens = "First token []{type:fire}, second token [poison]{mechanic:poison}, third token []{pokemon:archen}, last token$effect_chance%";
        String expected = "First token fire, second token poison, third token archen, last token";

        String result = dataFormatUtil.formatDescriptionMessage( multipleTokens );

        assertEquals( expected, result );
    }
}