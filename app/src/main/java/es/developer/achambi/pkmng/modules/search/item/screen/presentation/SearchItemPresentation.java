package es.developer.achambi.pkmng.modules.search.item.screen.presentation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.SearchListData;
import es.developer.achambi.pkmng.modules.search.item.model.Item;

public class SearchItemPresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String imageUrl;
    public final String description;
    public final boolean empty;

    public SearchItemPresentation(
            int id,
            String name, String imageUrl, String description, boolean empty) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.empty = empty;
    }

    @Override
    public int getViewType() {
        return R.id.item_view_id;
    }

    @Override
    public int getId() {
        return id;
    }

    public static class Builder {
        public static SearchItemPresentation buildPresentation( Item item ) {
            return new SearchItemPresentation(
                    item.getId(),
                    item.getName(),
                    item.getImageUrl(),
                    item.getDescriptionShort(),
                    item.getId() == -1
            );
        }
    }
}
