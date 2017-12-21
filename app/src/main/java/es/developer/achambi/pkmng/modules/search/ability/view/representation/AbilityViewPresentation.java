package es.developer.achambi.pkmng.modules.search.ability.view.representation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public class AbilityViewPresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String description;

    public AbilityViewPresentation( int id, String name, String description ) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getViewType() {
        return R.id.ability_view_id;
    }
}
