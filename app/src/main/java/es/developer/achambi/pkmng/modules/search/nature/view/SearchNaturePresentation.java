package es.developer.achambi.pkmng.modules.search.nature.view;

import android.content.Context;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.NatureDetailPresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class SearchNaturePresentation implements SearchListData{
    public final int id;
    public final String name;
    public final NatureDetailPresentation detail;
    public final boolean empty;

    @Override
    public int getViewType() {
        return R.id.nature_view_id;
    }

    public SearchNaturePresentation(int id, String name,
                                    NatureDetailPresentation detail, boolean empty ) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.empty = empty;
    }

    public static class Builder {
        public static SearchNaturePresentation buildPresentation(Context context, Nature nature ) {
            return new SearchNaturePresentation(
                    nature.getId(),
                    nature.getName(),
                    NatureDetailPresentation.Builder.buildPresentation( context,
                            nature.getIncreasedStat(), nature.getDecreasedStat() ),
                    nature.getId() == -1
            );
        }
    }
}
