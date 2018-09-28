package es.developer.achambi.pkmng.modules.search.ability.data;

import es.developer.achambi.coreframework.db.dao.AbilitiesDAO;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;

public class AbilityDataAccessFactory {
    public AbilityDataAccess buildDataAccess(AbilitiesDAO abilitiesDAO, DataFormatUtil formatter) {
        return new AbilityDataAccess( abilitiesDAO, formatter );
    }
}
