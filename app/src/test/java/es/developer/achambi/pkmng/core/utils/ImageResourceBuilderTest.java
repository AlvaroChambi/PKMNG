package es.developer.achambi.pkmng.core.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageResourceBuilderTest {
    private ImageResourceBuilder imageResourceBuilder;

    @Before
    public void setup() {
        imageResourceBuilder = new ImageResourceBuilder();
    }

    @Test
    public void buildPokemonImageSimpleIdentifier() {
        String identifier = "pokemon";
        int speciesId = 1;

        String result = imageResourceBuilder.buildPokemonImageIdentifier( speciesId, identifier );

        assertEquals( "1", result );
    }

    @Test
    public void buildPokemonImageMultipleTokens() {
        String identifier = "pokemon-multiple-tokens";
        int speciesId = 2;

        String result = imageResourceBuilder.buildPokemonImageIdentifier( speciesId, identifier );

        assertEquals( "2-multiple-tokens", result );
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildPokemonImageInvalidIdentifier() {
        String identifier = null;
        int speciesId = 1;
        imageResourceBuilder.buildPokemonImageIdentifier( speciesId, identifier );
    }
}