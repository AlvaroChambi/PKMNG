package es.developer.achambi.pkmng.core.data;

public class OverviewAssembler {
   private IOverviewPresenterFactory presenterFactory;
   private IPokemonDataAccessFactory dataAccessFactory;

    public IOverviewPresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(IOverviewPresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }

    public IPokemonDataAccessFactory getDataAccessFactory() {
        return dataAccessFactory;
    }

    public void setDataAccessFactory(IPokemonDataAccessFactory dataAccessFactory) {
        this.dataAccessFactory = dataAccessFactory;
    }
}
