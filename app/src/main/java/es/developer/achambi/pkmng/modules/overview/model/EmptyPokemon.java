package es.developer.achambi.pkmng.modules.overview.model;

import android.util.Pair;

public class EmptyPokemon extends Pokemon {
    public EmptyPokemon() {
        super(-1);
        setType( new Pair( Type.EMPTY, Type.EMPTY ) );
        setName("");
    }
}
