package es.developer.achambi.pkmng.modules.search.ability.view.presentation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.SearchListData;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public class SearchAbilityPresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String description;
    public final boolean empty;

    public SearchAbilityPresentation(int id, String name, String description, boolean empty ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.empty = empty;
    }

    @Override
    public int getViewType() {
        return R.id.ability_view_id;
    }

    public static class Builder {
        public static SearchAbilityPresentation buildPresentation(Ability ability ) {
            return new SearchAbilityPresentation(
                    ability.getId(),
                    ability.getName(),
                    ability.getDescriptionShort(),
                    ability.getId() == -1
            );
        }
    }
}
