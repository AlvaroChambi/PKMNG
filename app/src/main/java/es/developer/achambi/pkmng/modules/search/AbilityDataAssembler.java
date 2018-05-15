package es.developer.achambi.pkmng.modules.search;

import es.developer.achambi.pkmng.core.db.dao.AbilitiesDAO;
import es.developer.achambi.pkmng.modules.search.ability.data.AbilityDataAccessFactory;
import es.developer.achambi.pkmng.modules.search.ability.data.IAbilityDataAccess;

public class AbilityDataAssembler {
    private AbilitiesDAO abilitiesDAO;
    private AbilityDataAccessFactory dataAccessFactory;

    public AbilityDataAssembler setAbilitiesDAO(AbilitiesDAO abilitiesDAO) {
        this.abilitiesDAO = abilitiesDAO;
        return this;
    }

    public AbilityDataAssembler setDataAccessFactory(AbilityDataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
        return this;
    }

    public IAbilityDataAccess getAbilityDataAccess() {
        return dataAccessFactory.buildDataAccess( abilitiesDAO );
    }
}
