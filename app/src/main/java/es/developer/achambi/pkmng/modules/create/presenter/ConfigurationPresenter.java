package es.developer.achambi.pkmng.modules.create.presenter;

import android.os.Bundle;

import es.developer.achambi.pkmng.core.threading.Error;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.create.screen.StatEVView;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class ConfigurationPresenter extends Presenter
        implements StatEVView.ProgressUpdateProvider {

    private static final String ACTUAL_CONFIGURATION_SAVED_DATA_TAG = "ACTUAL_CONFIGURATION_SAVED_DATA_TAG";
    private static final String EDITABLE_CONFIGURATION_SAVED_DATA_TAG = "EDITABLE_CONFIGURATION_SAVED_DATA_TAG";

    private PokemonConfig editablePokemonConfig;
    private PokemonConfig pokemonConfiguration;
    private IConfigurationDataAccess dataAccess;

    public ConfigurationPresenter(Screen screen,
                                  IConfigurationDataAccess configurationDataAccess,
                                  MainExecutor executor) {
        super(screen, executor);
        editablePokemonConfig = new PokemonConfig();
        pokemonConfiguration = new PokemonConfig();
        this.dataAccess = configurationDataAccess;
    }

    @Override
    public int requestValueIncrement(Stat stat, int progress) {
        StatsSet evData = editablePokemonConfig.getConfiguration().getEvsSet();
        int totalStatsPreview = evData.getTotalStatsPreview( stat, progress );
        if( totalStatsPreview <= StatsSet.MAX_TOTAL_EVS ) {
            evData.getStats().put(stat, progress);
            return progress;
        } else if( totalStatsPreview > StatsSet.MAX_TOTAL_EVS ){
            return progress + ( StatsSet.MAX_TOTAL_EVS - totalStatsPreview );
        }
        return progress;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable( EDITABLE_CONFIGURATION_SAVED_DATA_TAG, editablePokemonConfig );
        bundle.putParcelable( ACTUAL_CONFIGURATION_SAVED_DATA_TAG, pokemonConfiguration );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        editablePokemonConfig = bundle.getParcelable( EDITABLE_CONFIGURATION_SAVED_DATA_TAG );
        pokemonConfiguration = bundle.getParcelable( ACTUAL_CONFIGURATION_SAVED_DATA_TAG );
    }

    public StatsSet getStatsSet() {
        return editablePokemonConfig.getConfiguration().getEvsSet();
    }

    public void saveConfigurationRequest(String name,
                                         ResponseHandler<ConfigurationAction> responseHandler) {
        Request<Integer> request;
        ConfigurationResponseHandler presenterHandler;
        if( pokemonConfiguration.getId() == BasePokemon.EMPTY_ID ) {
            pokemonConfiguration.setName( name );
            pokemonConfiguration.setPokemon( editablePokemonConfig.getPokemon() );
            pokemonConfiguration.setConfiguration( editablePokemonConfig.getConfiguration() );
            request = new Request<Integer>() {
                @Override
                public Response<Integer> perform() throws Exception{
                    return new Response<>(dataAccess.insertConfiguration( pokemonConfiguration ));
                }
            };
            presenterHandler = new ConfigurationResponseHandler( ConfigurationAction.CREATE,
                    responseHandler );
            request( request, presenterHandler );
        } else if( pokemonConfiguration.getName().equals( name ) &&
               pokemonConfiguration.getPokemon().getName().equals(
                       editablePokemonConfig.getPokemon().getName()) &&
               pokemonConfiguration.getConfiguration()
                       .equals(editablePokemonConfig.getConfiguration())) {
            responseHandler.onSuccess( new Response<>(ConfigurationAction.NONE) );
        } else {
            pokemonConfiguration.setName( name );
            pokemonConfiguration.setPokemon( editablePokemonConfig.getPokemon() );
            pokemonConfiguration.setConfiguration( editablePokemonConfig.getConfiguration() );
            request = new Request<Integer>() {
                @Override
                public Response<Integer> perform() throws Exception {
                    dataAccess.updateConfiguration( pokemonConfiguration );
                    return new Response<>( pokemonConfiguration.getId() );
                }
            };
            presenterHandler = new ConfigurationResponseHandler( ConfigurationAction.UPDATE,
                    responseHandler );
            request( request, presenterHandler );
        }
    }

    private class ConfigurationResponseHandler extends ResponseHandler<Integer> {
        private ConfigurationAction action;
        private ResponseHandler<ConfigurationAction> handler;
        private ConfigurationResponseHandler( ConfigurationAction action,
                                              ResponseHandler<ConfigurationAction> handler ) {
            this.action = action;
            this.handler = handler;
        }
        @Override
        public void onSuccess(Response<Integer> response) {
            pokemonConfiguration.setId( response.getData() );
            handler.onSuccess( new Response<>(action) );
        }

        @Override
        public void onError(Error error) {
            super.onError(error);
            handler.onError(error);
        }
    }

    public void setPokemonConfiguration(PokemonConfig pokemonConfiguration) {
        this.pokemonConfiguration = pokemonConfiguration;
        this.editablePokemonConfig = pokemonConfiguration;
    }

    public PokemonConfig getPokemonConfiguration() {
        return pokemonConfiguration;
    }

    public PokemonConfig getEditablePokemonConfig() {
        return editablePokemonConfig;
    }

    /**
     *
     * @return configuration name if there are a previously created configuration, empty string
     * otherwise
     */
    public String getConfigurationName() {
        if(pokemonConfiguration == null) {
            return "";
        } else {
            return pokemonConfiguration.getName();
        }
    }

    public Configuration getConfiguration() {
        return editablePokemonConfig.getConfiguration();
    }

    /**
     * On a Pokemon change all editable values will be set to it's default empty value
     * @param pokemon
     */
    public void setPokemon(Pokemon pokemon) {
        if( !this.editablePokemonConfig.getPokemon().equals( pokemon ) ) {
            this.editablePokemonConfig.setPokemon( pokemon );
            this.editablePokemonConfig.setConfiguration( new Configuration() );
        }
    }

    public Pokemon getPokemon() {
        return editablePokemonConfig.getPokemon();
    }

    public Item getItem() {
        return editablePokemonConfig.getConfiguration().getItem();
    }

    public void setItem(Item item) {
        this.editablePokemonConfig.getConfiguration().setItem( item );
    }

    public Ability getAbility() {
        return editablePokemonConfig.getConfiguration().getAbility();
    }

    public void setAbility(Ability ability) {
        this.editablePokemonConfig.getConfiguration().setAbility( ability );
    }

    public Nature getNature() {
        return editablePokemonConfig.getConfiguration().getNature();
    }

    public void setNature(Nature nature) {
        editablePokemonConfig.getConfiguration().setNature( nature );
    }

    public void setMove0(Move move0) {
        editablePokemonConfig.getConfiguration().setMove0( move0 );
    }

    public void setMove1(Move move1) {
        editablePokemonConfig.getConfiguration().setMove1( move1 );
    }

    public void setMove2(Move move2) {
        editablePokemonConfig.getConfiguration().setMove2( move2 );
    }

    public void setMove3(Move move3) {
        editablePokemonConfig.getConfiguration().setMove3( move3 );
    }
}
