package es.developer.achambi.pkmng.modules.search.ability.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public abstract class ISearchAbilityPresenter extends Presenter {
    public abstract void fetchAbilities( ResponseHandler<ArrayList<Ability>> responseHandler );
}
