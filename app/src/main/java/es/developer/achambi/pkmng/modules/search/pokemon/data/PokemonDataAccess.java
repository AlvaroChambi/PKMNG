package es.developer.achambi.pkmng.modules.search.pokemon.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.pokemon_species;
import es.developer.achambi.pkmng.core.db.stat_value;
import es.developer.achambi.pkmng.core.db.type_value;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public class PokemonDataAccess {
    private static final String SP_ATTACK = "special-attack";
    private static final String SP_DEFENSE = "special-defense";
    private AppDatabase database;

    public PokemonDataAccess( AppDatabase database ) {
        this.database = database;
    }

    public ArrayList<Pokemon> accessData( ) {
        long start = System.currentTimeMillis();
        List<pokemon_species> pokemonArray = AppDatabase.buildDatabase().pokemonModel().getPokemon();
        ArrayList<Pokemon> pokemonList = new ArrayList<>( pokemonArray.size() );
        for( pokemon_species currentPokemon : pokemonArray ) {
            Pokemon pokemon = new Pokemon(currentPokemon.id);
            pokemon.setName(currentPokemon.identifier);
            List<type_value> type =
                    AppDatabase.buildDatabase().typeModel().getType(currentPokemon.id);
            Type secondType = Type.EMPTY;
            if( type.size() > 1 ) {
                secondType = parseType( type.get(1).name );
            }
            pokemon.setType( parseType(type.get(0).name), secondType );
            List<stat_value> stats =
                    AppDatabase.buildDatabase().statsModel().getStats(currentPokemon.id);
            populateStats( pokemon, stats );

            pokemonList.add( pokemon );
        }
        Log.i("TRACE", "time spent: " + (System.currentTimeMillis() - start));
        return pokemonList;
    }

    private void populateStats( Pokemon pokemon, List<stat_value> stats ) {
        for( stat_value current : stats ) {
            switch ( parseStat(current.name) ) {
                case HP:
                    pokemon.setHP( current.value );
                    break;
                case ATTACK:
                    pokemon.setAttack( current.value );
                    break;
                case DEFENSE:
                    pokemon.setDefense( current.value );
                    break;
                case SP_ATTACK:
                    pokemon.setSpAttack( current.value );
                    break;
                case SP_DEFENSE:
                    pokemon.setSpDefense( current.value );
                    break;
                case SPEED:
                    pokemon.setSpeed( current.value );
                    break;
                case NONE:
                    break;
            }
        }
    }

    private Stat parseStat(String identifier ) {
        if( identifier.equalsIgnoreCase(Stat.HP.toString()) ) {
            return Stat.HP;
        } else if( identifier.equalsIgnoreCase(Stat.ATTACK.toString()) ) {
            return Stat.ATTACK;
        } else if( identifier.equalsIgnoreCase(Stat.DEFENSE.toString()) ) {
            return Stat.DEFENSE;
        } else if( identifier.equalsIgnoreCase(SP_ATTACK) ) {
            return Stat.SP_ATTACK;
        } else if( identifier.equalsIgnoreCase(SP_DEFENSE) ) {
            return Stat.SP_DEFENSE;
        } else if( identifier.equalsIgnoreCase(Stat.SPEED.toString()) ) {
            return Stat.SPEED;
        } else {
            return Stat.NONE;
        }
    }

    private Type parseType( String identifier ) {
        if( identifier.equalsIgnoreCase(Type.NORMAL.toString()) ) {
            return Type.NORMAL;
        } else if( identifier.equalsIgnoreCase(Type.FIRE.toString()) ) {
            return Type.FIRE;
        } else if( identifier.equalsIgnoreCase(Type.WATER.toString()) ) {
            return Type.WATER;
        } else if( identifier.equalsIgnoreCase(Type.ELECTRIC.toString()) ) {
            return Type.ELECTRIC;
        } else if( identifier.equalsIgnoreCase(Type.GRASS.toString()) ) {
            return Type.GRASS;
        } else if( identifier.equalsIgnoreCase(Type.ICE.toString()) ) {
            return Type.ICE;
        } else if( identifier.equalsIgnoreCase(Type.FIGHTING.toString()) ) {
            return Type.FIGHTING;
        } else if( identifier.equalsIgnoreCase(Type.POISON.toString()) ) {
            return Type.POISON;
        } else if( identifier.equalsIgnoreCase(Type.GROUND.toString()) ) {
            return Type.GROUND;
        } else if( identifier.equalsIgnoreCase(Type.FLYING.toString()) ) {
            return Type.FLYING;
        } else if( identifier.equalsIgnoreCase(Type.PSYCHIC.toString()) ) {
            return Type.PSYCHIC;
        } else if( identifier.equalsIgnoreCase(Type.BUG.toString()) ) {
            return Type.BUG;
        } else if( identifier.equalsIgnoreCase(Type.ROCK.toString()) ) {
            return Type.ROCK;
        } else if( identifier.equalsIgnoreCase(Type.GHOST.toString()) ) {
            return Type.GHOST;
        } else if( identifier.equalsIgnoreCase(Type.DRAGON.toString()) ) {
            return Type.DRAGON;
        } else if( identifier.equalsIgnoreCase(Type.DARK.toString()) ) {
            return Type.DARK;
        } else if( identifier.equalsIgnoreCase(Type.STEEL.toString()) ) {
            return Type.STEEL;
        } else if( identifier.equalsIgnoreCase(Type.FAIRY.toString()) ) {
            return Type.FAIRY;
        } else {
            return Type.EMPTY;
        }
    }
}
