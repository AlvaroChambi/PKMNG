package es.developer.achambi.pkmng.modules.data.type;

import es.developer.achambi.coreframework.db.dao.TypeDAO;

public class TypeDataAccessFactory {
    public ITypeDataAccess buildDataAccess( TypeDAO typeDAO ) {
        return new TypeDataAccess( typeDAO );
    }
}
