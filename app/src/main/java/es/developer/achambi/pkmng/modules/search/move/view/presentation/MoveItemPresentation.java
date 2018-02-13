package es.developer.achambi.pkmng.modules.search.move.view.presentation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.MoveTypePresentation;
import es.developer.achambi.pkmng.core.ui.presentation.TypePresentation;
import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public class MoveItemPresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String effect;
    public final int categoryImageResource;
    public final MoveTypePresentation typePresentation;
    public final String power;
    public final String pp;
    public final String accuracy;

    public MoveItemPresentation(int id, String name, String effect,
                                int categoryImageResource, MoveTypePresentation typePresentation,
                                String power, String pp, String accuracy) {
        this.id = id;
        this.name = name;
        this.effect = effect;
        this.categoryImageResource = categoryImageResource;
        this.typePresentation = typePresentation;
        this.power = power;
        this.pp = pp;
        this.accuracy = accuracy;
    }

    @Override
    public int getViewType() {
        return R.id.move_view_id;
    }
}
