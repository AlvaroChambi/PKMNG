package es.developer.achambi.pkmng.modules.search.item.view.representation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public class ItemResultViewRepresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String imageUrl;
    public final String description;

    public ItemResultViewRepresentation (
            int id,
            String name, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    @Override
    public int getViewType() {
        return R.id.item_view_id;
    }
}
