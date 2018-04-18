package es.developer.achambi.pkmng.modules.search.item.data;

import es.developer.achambi.pkmng.core.db.AppDatabase;

public class ItemDataAccessFactory implements IItemDataAccessFactory{
    private AppDatabase database;

    public ItemDataAccessFactory( AppDatabase database ) {
        this.database = database;
    }

    @Override
    public ItemDataAccess buildDataAccess() {
        return new ItemDataAccess(database);
    }
}
