package es.developer.achambi.pkmng.modules.search.configuration.data;

import android.support.v4.util.Pair;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class MockConfigurationDataAccess implements IConfigurationDataAccess {
    private static int STATIC_ID = 1001;
    @Override
    public ArrayList<PokemonConfig> accessConfigurationData() {
        return buildConfigurationData();
    }

    @Override
    public ArrayList<PokemonConfig> queryConfigurationData(String query) {
        ArrayList<PokemonConfig> list = new ArrayList<>(  );
        list.add( buildDamageConfiguration() );
        return list;
    }

    @Override
    public int insertConfiguration(PokemonConfig configuration) {
        return STATIC_ID++;
    }

    @Override
    public void updateConfiguration(PokemonConfig configuration) {
    }

    private ArrayList<PokemonConfig> buildConfigurationData() {
        int numberOfPokemon = 2;
        ArrayList<PokemonConfig> pokemonList = new ArrayList<>(numberOfPokemon);

        pokemonList.add(buildDamageConfiguration());
        pokemonList.add(buildConfigurationEmpty());
        pokemonList.add(buildFilledConfiguration());

        return pokemonList;
    }

    private PokemonConfig buildDamageConfiguration() {
        Pokemon pokemon = new Pokemon( 120 );
        pokemon.setName( "Venasaur" );
        pokemon.setType(new Pair<>(Type.GRASS, Type.POISON));

        pokemon.setType(Type.GRASS, Type.POISON);
        pokemon.setHP(80);
        pokemon.setAttack(82);
        pokemon.setDefense(83);
        pokemon.setSpAttack(100);
        pokemon.setSpDefense(100);
        pokemon.setSpeed(80);

        Configuration config = new Configuration();
        PokemonConfig pokemonConfig = new PokemonConfig();
        pokemonConfig.setId(120);
        pokemonConfig.setPokemon(pokemon);
        pokemonConfig.setConfiguration(config);
        pokemonConfig.setName("Special");
        Item item = new Item();
        item.setId(101);
        item.setName("eviolite");
        item.setDescriptionShort("Holder has 1.5× Defense and Special Defense, as long as it\'s not fully evolved.");
        config.setItem( item );
        Ability ability = new Ability();
        ability.setId( 10 );
        ability.setDescriptionShort("Strengthens grass moves to inflict 1.5× damage at 1/3 max HP or less.");
        ability.setName("magic guard");
        config.setAbility(ability);
        Nature nature = new Nature();
        nature.setId(101);
        nature.setIncreasedStat(Stat.SP_DEFENSE);
        nature.setDecreasedStat(Stat.ATTACK);
        nature.setName("calm");
        config.setNature(nature);

        Move move0 = new Move(1);
        move0.setName("Sludge bomb");
        move0.setType(Type.POISON);
        move0.setCategory(Move.Category.SPECIAL);
        move0.setPower(90);
        move0.setAccuracy(100);
        move0.setEffect("Inflicts regular damage with no additional effect.");
        config.setMove0(move0);

        StatsSet evs = new StatsSet();
        evs.setHP(248);
        evs.setDefense(88);
        evs.setSpDefense(156);
        evs.setSpeed(16);

        pokemonConfig.getConfiguration().setEvsSet(evs);

        return pokemonConfig;
    }

    private PokemonConfig buildFilledConfiguration() {
        Pokemon pokemon = new Pokemon(1);
        pokemon.setName("Pikachu");
        pokemon.setType(Type.STEEL);
        pokemon.setHP(35);
        pokemon.setAttack(55);
        pokemon.setDefense(40);
        pokemon.setSpAttack(50);
        pokemon.setSpDefense(55);
        pokemon.setSpeed(50);

        Configuration config = new Configuration();
        PokemonConfig pokemonConfig = new PokemonConfig();
        pokemonConfig.setId(1);
        pokemonConfig.setPokemon(pokemon);
        pokemonConfig.setConfiguration(config);
        pokemonConfig.setName("Filled");
        Item item = new Item();
        item.setId(101);
        item.setName("eviolite");
        item.setDescriptionShort("Holder has 1.5× Defense and Special Defense, as long as it\'s not fully evolved.");
        config.setItem( item );
        Ability ability = new Ability();
        ability.setId( 10 );
        ability.setDescriptionShort("Strengthens grass moves to inflict 1.5× damage at 1/3 max HP or less.");
        ability.setName("magic guard");
        config.setAbility(ability);
        Nature nature = new Nature();
        nature.setId(101);
        nature.setIncreasedStat(Stat.ATTACK);
        nature.setDecreasedStat(Stat.DEFENSE);
        nature.setName("modest");
        config.setNature(nature);

        Move move0 = new Move(1);
        move0.setName("Thunderbolt");
        move0.setType(Type.ELECTRIC);
        move0.setCategory(Move.Category.SPECIAL);
        move0.setPower(90);
        move0.setAccuracy(100);
        move0.setEffect("Inflicts regular damage with no additional effect.");
        config.setMove0(move0);
        Move move1 = new Move(2);
        move1.setName("Grass knot");
        move1.setType(Type.GRASS);
        move1.setCategory(Move.Category.SPECIAL);
        move1.setPower(90);
        move1.setAccuracy(100);
        move1.setEffect("Inflicts regular damage with no additional effect.");
        config.setMove1(move1);
        Move move2 = new Move(3);
        move2.setName("Signal Beam");
        move2.setType(Type.BUG);
        move2.setCategory(Move.Category.SPECIAL);
        move2.setPower(90);
        move2.setAccuracy(100);
        move2.setEffect("Inflicts regular damage with no additional effect.");
        config.setMove2(move2);
        Move move3 = new Move(4);
        move3.setName("Hidden power");
        move3.setType(Type.NORMAL);
        move3.setCategory(Move.Category.SPECIAL);
        move3.setPower(90);
        move3.setAccuracy(100);
        move3.setEffect("Inflicts regular damage with no additional effect.");
        config.setMove3(move3);

        StatsSet evs = new StatsSet();
        evs.setHP(50);
        evs.setAttack(50);
        pokemonConfig.getConfiguration().setEvsSet(evs);

        return pokemonConfig;
    }

    private PokemonConfig buildConfigurationEmpty() {
        Pokemon pokemon0 = new Pokemon(4);
        pokemon0.setName("Pikachu");
        pokemon0.setType(Type.ELECTRIC);
        pokemon0.setHP(35);
        pokemon0.setAttack(55);
        pokemon0.setDefense(40);
        pokemon0.setSpAttack(50);
        pokemon0.setSpDefense(55);
        pokemon0.setSpeed(50);
        PokemonConfig pokemonConfiguration = new PokemonConfig();
        pokemonConfiguration.setId(110);
        pokemonConfiguration.setPokemon(pokemon0);
        pokemonConfiguration.setConfiguration(new Configuration());
        return pokemonConfiguration;
    }
}
