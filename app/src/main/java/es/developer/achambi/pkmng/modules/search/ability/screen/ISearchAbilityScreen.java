package es.developer.achambi.pkmng.modules.search.ability.screen;

import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public interface ISearchAbilityScreen extends Screen {
    void showAbilityDetails( Ability ability );
}