package es.developer.achambi.pkmng.modules.search.pokemon.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.model.pokemon_species;
import es.developer.achambi.pkmng.core.db.model.type_value;
import es.developer.achambi.pkmng.modules.data.StatDataAccess;
import es.developer.achambi.pkmng.modules.data.TypeDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public class PokemonDataAccess {
    private AppDatabase database;
    private StatDataAccess statDataAccess;
    private TypeDataAccess typeDataAccess;

    public PokemonDataAccess( AppDatabase database,
                              StatDataAccess statDataAccess,
                              TypeDataAccess typeDataAccess ) {
        this.database = database;
        this.statDataAccess = statDataAccess;
        this.typeDataAccess = typeDataAccess;
    }

    public ArrayList<Pokemon> accessData() {
        List<pokemon_species> pokemonArray = database.pokemonModel().getPokemon();
        ArrayList<Pokemon> pokemonList = new ArrayList<>( pokemonArray.size() );
        for( pokemon_species currentPokemon : pokemonArray ) {
            Pokemon pokemon = new Pokemon(currentPokemon.id);
            pokemon.setName(currentPokemon.identifier);
            pokemon.setType( typeDataAccess.accessPokemonTypeData( currentPokemon.id ) );
            pokemon.setStats( statDataAccess.accessPokemonStatsData( pokemon.getId() ) );

            pokemonList.add( pokemon );
        }
        return pokemonList;
    }
}
