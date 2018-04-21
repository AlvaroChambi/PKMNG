package es.developer.achambi.pkmng.modules.search.ability.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;

public class AbilityDataAccessFactory implements IAbilityDataAccessFactory {
    private AppDatabase database;

    public AbilityDataAccessFactory(AppDatabase database) {
        this.database = database;
    }

    @Override
    public AbilityDataAccess buildDataAccess() {
        return new AbilityDataAccess( database );
    }
}
