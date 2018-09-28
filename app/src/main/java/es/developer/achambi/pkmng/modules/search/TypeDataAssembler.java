package es.developer.achambi.pkmng.modules.search;

import es.developer.achambi.coreframework.db.dao.TypeDAO;
import es.developer.achambi.pkmng.modules.data.type.ITypeDataAccess;
import es.developer.achambi.pkmng.modules.data.type.TypeDataAccessFactory;

public class TypeDataAssembler {
    private TypeDAO typeDAO;
    private TypeDataAccessFactory dataAccessFactory;

    public TypeDataAssembler setTypeDAO(TypeDAO typeDAO) {
        this.typeDAO = typeDAO;
        return this;
    }

    public TypeDataAssembler setDataAccessFactory(TypeDataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
        return this;
    }

    public ITypeDataAccess getDataAccess() {
        return dataAccessFactory.buildDataAccess( typeDAO );
    }
}
