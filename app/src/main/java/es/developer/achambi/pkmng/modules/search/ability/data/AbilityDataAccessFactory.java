package es.developer.achambi.pkmng.modules.search.ability.data;

import es.developer.achambi.pkmng.core.db.dao.AbilitiesDAO;

public class AbilityDataAccessFactory {
    public AbilityDataAccess buildDataAccess(AbilitiesDAO abilitiesDAO) {
        return new AbilityDataAccess( abilitiesDAO );
    }
}
