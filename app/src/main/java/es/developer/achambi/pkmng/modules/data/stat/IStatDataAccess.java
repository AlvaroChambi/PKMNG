package es.developer.achambi.pkmng.modules.data.stat;

import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public interface IStatDataAccess {
    /**
     * Access pokemon data with the given id
     * @param pokemonId valid id's are higher than 0
     * @return Pokemon data that matches the given id, null if no pokemon is found
     * @throws IllegalIDException when an invalid id is requested
     */
    StatsSet accessPokemonStatsData( int pokemonId ) throws IllegalIDException;

    /**
     * Access EV set data for the given configuration id
     * @param configurationId valid id's are higher than 0
     * @return Ev set defined for the given configuration
     * @throws IllegalIDException when an invalid id is requested
     */
    StatsSet accessEvsSetData( int configurationId ) throws IllegalIDException;

    /**
     *Access stat data for the given id
     * @param statId valid id's are higher than 0
     * @return stat values for the given stat id
     * @throws IllegalIDException when an invalid id is requested
     */
    Stat accessStatData(int statId ) throws IllegalIDException;

    /**
     * Creates a new stat set for the given configuration id
     * @param configurationId Configuration that holds the stat set
     * @param statsSet Stat set values to be created
     * @throws IllegalArgumentException on negative configuration id's and null StatSet's
     */
    void insertStatsSet( int configurationId, StatsSet statsSet ) throws IllegalArgumentException;

    /**
     * Updates the stat set that relates to the given configuration id
     * @param configurationId Configuration that holds the stat set
     * @param statsSet new Stat set values for the configuration
     * @throws IllegalArgumentException on negative configuration id's and null StatSet's
     */
    void updateStatsSet( int configurationId, StatsSet statsSet ) throws IllegalArgumentException;
}
