package es.developer.achambi.pkmng.modules.search.ability.presenter;

import java.util.ArrayList;

import es.developer.achambi.coreframework.threading.MainExecutor;
import es.developer.achambi.coreframework.threading.ResponseHandler;
import es.developer.achambi.coreframework.ui.Presenter;
import es.developer.achambi.coreframework.ui.Screen;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public abstract class ISearchAbilityPresenter extends Presenter {
    public ISearchAbilityPresenter(Screen screen, MainExecutor executor) {
        super(screen, executor);
    }
    public abstract void fetchAbilities( int pokemonId,
                                         ResponseHandler<ArrayList<Ability>> responseHandler );
    public abstract void fetchAbilitiesQuery( int pokemonId, String query,
                                              ResponseHandler<ArrayList<Ability>> responseHandler );
}
