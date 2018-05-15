package es.developer.achambi.pkmng.modules.search.nature.data;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public interface INatureDataAccess {
    /**
     * Access the list of Natures
     * @return list of Natures
     */
    ArrayList<Nature> accessData();

    /**
     * Access Nature data for the given nature id
     * @param natureId to be requested, id's should be higher than 0
     * @return Nature data that matches the given id, empty Nature if no match is found
     * @throws IllegalIDException on 0 or negative id's provided
     */
    Nature accessNatureData(int natureId) throws IllegalIDException;
}
