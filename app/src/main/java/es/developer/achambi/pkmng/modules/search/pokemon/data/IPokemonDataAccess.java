package es.developer.achambi.pkmng.modules.search.pokemon.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public interface IPokemonDataAccess {
    ArrayList<Pokemon> accessData();
    Pokemon accessPokemonData(int pokemonId);
}
