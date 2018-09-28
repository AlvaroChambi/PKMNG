package es.developer.achambi.pkmng.modules.search.nature.data;

import java.util.ArrayList;

import es.developer.achambi.coreframework.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public interface INatureDataAccess {
    /**
     * Access the list of Natures
     * @return list of Natures
     */
    ArrayList<Nature> accessData();

    /**
     * Access the items data that matches the given query
     * @param query text to perform the query, on a null query an empty result will be returned
     * @return available data that starts with the given query
     */
    ArrayList<Nature> accessNatureQueryData( String query );

    /**
     * Access Nature data for the given nature id
     * @param natureId to be requested, id's should be higher than 0
     * @return Nature data that matches the given id, empty Nature if no match is found or
     * the id is equals to -1
     * @throws IllegalIDException on 0 or negative id's provided
     */
    Nature accessNatureData(int natureId) throws IllegalIDException;
}
