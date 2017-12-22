package es.developer.achambi.pkmng.modules.search.nature.view;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public class NatureViewPresentation implements SearchListData{
    public int id;
    public String name;
    public String increasedStat;
    public String decreasedStat;

    @Override
    public int getViewType() {
        return R.id.nature_view_id;
    }

    public NatureViewPresentation( int id, String name,
                                   String increasedStat, String decreasedStat ) {
        this.id = id;
        this.name = name;
        this.increasedStat = increasedStat;
        this.decreasedStat = decreasedStat;
    }
}
