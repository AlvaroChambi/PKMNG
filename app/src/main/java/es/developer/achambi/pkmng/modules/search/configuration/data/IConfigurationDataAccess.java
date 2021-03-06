package es.developer.achambi.pkmng.modules.search.configuration.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public interface IConfigurationDataAccess {
    /**
     * Access list of pokemon configurations
     * @return list of pokemon configurations
     */
    ArrayList<PokemonConfig> accessConfigurationData();

    /**
     * Access configuration data of the instances whose identifiers contains the given query string
     * @param query text to perform the query, on a null query an empty result will be returned
     * @return available data that stats with the given query
     */
    ArrayList<PokemonConfig> queryConfigurationData( String query );

    /**
     * Creates a new configuration for the given PokemonConfig
     * @param configuration to be created
     * @return if successful, id of the created configuration.
     * @throws RuntimeException on not allowed pokemon config values, or id different than -1(NO_ID)
     *
     */
    int insertConfiguration(final PokemonConfig configuration) throws RuntimeException;

    /**
     * Updates the configuration that matches the given configuration id
     * If the configuration does not exist this method will do nothing
     * @param configuration to be updated
     * @throws RuntimeException on not allowed config values, or id not found
     */
    void updateConfiguration(final PokemonConfig configuration) throws RuntimeException;
}
