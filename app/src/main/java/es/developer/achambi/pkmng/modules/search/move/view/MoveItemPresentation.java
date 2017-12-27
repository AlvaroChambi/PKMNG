package es.developer.achambi.pkmng.modules.search.move.view;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public class MoveItemPresentation implements SearchListData{
    public final int id;
    public final int categoryImageResource;
    public final int typeImageResource;
    public final String power;
    public final String pp;
    public final String accuracy;

    public MoveItemPresentation(int id, int categoryImageResource, int typeImageResource,
                                String power, String pp, String accuracy) {
        this.id = id;
        this.categoryImageResource = categoryImageResource;
        this.typeImageResource = typeImageResource;
        this.power = power;
        this.pp = pp;
        this.accuracy = accuracy;
    }

    @Override
    public int getViewType() {
        return R.id.move_view_id;
    }
}
