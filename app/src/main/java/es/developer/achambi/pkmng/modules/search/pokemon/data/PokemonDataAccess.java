package es.developer.achambi.pkmng.modules.search.pokemon.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.model.pokemon_species;
import es.developer.achambi.pkmng.core.db.model.type_value;
import es.developer.achambi.pkmng.modules.data.StatDataAccess;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public class PokemonDataAccess {
    private AppDatabase database;
    private StatDataAccess statDataAccess;
    

    public PokemonDataAccess( AppDatabase database,
                              StatDataAccess statDataAccess ) {
        this.database = database;
        this.statDataAccess = statDataAccess;
    }

    public ArrayList<Pokemon> accessData() {
        List<pokemon_species> pokemonArray = database.pokemonModel().getPokemon();
        ArrayList<Pokemon> pokemonList = new ArrayList<>( pokemonArray.size() );
        for( pokemon_species currentPokemon : pokemonArray ) {
            Pokemon pokemon = new Pokemon(currentPokemon.id);
            pokemon.setName(currentPokemon.identifier);
            List<type_value> type =
                    database.typeModel().getPokemonType(currentPokemon.id);
            Type secondType = Type.EMPTY;
            if( type.size() > 1 ) {
                secondType = parseType( type.get(1).name );
            }
            pokemon.setType( parseType(type.get(0).name), secondType );
            pokemon.setStats( statDataAccess.accessPokemonStatsData( pokemon.getId() ) );

            pokemonList.add( pokemon );
        }
        return pokemonList;
    }
}
