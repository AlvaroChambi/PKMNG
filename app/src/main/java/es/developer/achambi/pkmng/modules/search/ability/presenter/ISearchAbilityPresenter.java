package es.developer.achambi.pkmng.modules.search.ability.presenter;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public interface ISearchAbilityPresenter extends ViewPresenter{
    ArrayList<Ability> fetchAbilities();
}
