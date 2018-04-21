package es.developer.achambi.pkmng.modules.search.configuration.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.Error;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.DataState;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.configuration.screen.presentation.ConfigurationPresentation;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.configuration.screen.ISearchConfigurationScreen;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class SearchConfigurationPresenter extends Presenter implements
        SearchAdapterDecorator.OnItemClickedListener<ConfigurationPresentation> {
    private static final String CONFIGURATION_DATA_SAVED_STATE = "CONFIGURATION_DATA_SAVED_STATE";

    private ArrayList<PokemonConfig> pokemonConfigList;
    private ISearchConfigurationScreen screen;

    public SearchConfigurationPresenter( ISearchConfigurationScreen screen,
                                         MainExecutor executor ) {
        super(screen, executor);
        this.screen = screen;
    }

    public void fetchConfigurationList(
            final ResponseHandler<ArrayList<PokemonConfig>> responseHandler ) {
        setDataState( DataState.NOT_FINISHED );
        ResponseHandler handler = new ResponseHandlerDecorator<ArrayList<PokemonConfig>>(
                responseHandler) {
            @Override
            public void onSuccess(Response<ArrayList<PokemonConfig>> response) {
                setDataState( DataState.SUCCESS );
                pokemonConfigList = response.getData();
                super.onSuccess(response);
            }

            @Override
            public void onError(Error error) {
                setDataState( DataState.ERROR );
                super.onError(error);
            }
        };

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( buildConfigurationData() );
            }
        }, handler );
    }

    public ArrayList<PokemonConfig> getConfigurationList() {
        return pokemonConfigList;
    }

    @Override
    public void onItemClicked(ConfigurationPresentation item) {
        for( BasePokemon baseItem : pokemonConfigList) {
            if( item.id == baseItem.getId() ) {
                screen.showConfigurationDetails( ((PokemonConfig) baseItem) );
            }
        }
    }

    private ArrayList<PokemonConfig> buildConfigurationData() {
        int numberOfPokemon = 1;
        ArrayList<PokemonConfig> pokemonList = new ArrayList<>(numberOfPokemon);

        Pokemon pokemon0 = new Pokemon(1);
        pokemon0.setName("Pikachu");
        pokemon0.setType(Type.ELECTRIC);
        pokemon0.setHP(35);
        pokemon0.setAttack(55);
        pokemon0.setDefense(40);
        pokemon0.setSpAttack(50);
        pokemon0.setSpDefense(55);
        pokemon0.setSpeed(50);
        pokemonList.add(new PokemonConfig(110, pokemon0, new Configuration()));

        for(int i = 0; i < numberOfPokemon; i++) {
            Pokemon pokemon = new Pokemon(10);
            pokemon.setName("Pikachu");
            pokemon.setType(Type.STEEL);
            pokemon.setHP(35);
            pokemon.setAttack(55);
            pokemon.setDefense(40);
            pokemon.setSpAttack(50);
            pokemon.setSpDefense(55);
            pokemon.setSpeed(50);

            Configuration config = new Configuration();
            PokemonConfig pokemonConfig = new PokemonConfig(i,pokemon, config);
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
            pokemonList.add(pokemonConfig);
        }

        return pokemonList;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList( CONFIGURATION_DATA_SAVED_STATE, pokemonConfigList );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        pokemonConfigList = bundle.getParcelableArrayList( CONFIGURATION_DATA_SAVED_STATE );
    }
}
