package es.developer.achambi.pkmng.modules.search.ability.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public interface ISearchAbilityPresenter extends ViewPresenter{
    void fetchAbilities( ResponseHandler<ArrayList<Ability>> responseHandler );
}
