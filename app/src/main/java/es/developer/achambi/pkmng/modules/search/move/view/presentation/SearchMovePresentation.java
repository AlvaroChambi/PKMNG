package es.developer.achambi.pkmng.modules.search.move.view.presentation;

import android.content.Context;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.MoveTypePresentation;
import es.developer.achambi.pkmng.core.ui.presentation.SearchListData;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class SearchMovePresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String effect;
    public final int categoryImageResource;
    public final MoveTypePresentation moveTypePresentation;
    public final String power;
    public final String pp;
    public final String accuracy;
    public final boolean empty;

    public SearchMovePresentation(int id, String name, String effect,
                                  int categoryImageResource, MoveTypePresentation moveTypePresentation,
                                  String power, String pp, String accuracy, boolean empty) {
        this.id = id;
        this.name = name;
        this.effect = effect;
        this.categoryImageResource = categoryImageResource;
        this.moveTypePresentation = moveTypePresentation;
        this.power = power;
        this.pp = pp;
        this.accuracy = accuracy;
        this.empty = empty;
    }

    @Override
    public int getViewType() {
        return R.id.move_view_id;
    }

    public static class Builder {
        public static SearchMovePresentation buildPresentation(Context context, Move move ) {
            return new SearchMovePresentation(
                    move.getId(),
                    move.getName(),
                    move.getEffect(),
                    buildCategory(move.getCategory()),
                    MoveTypePresentation.Builder.buildPresentation( context, move.getType()),
                    "Pow. " + move.getPower(),
                    "PP " + move.getPp(),
                    "Acc. " + move.getAccuracy(),
                    move.getId() == -1

            );
        }

        private static int buildCategory( Move.Category category ) {
            switch (category) {
                case PHYSICAL:
                    return R.drawable.move_category_physical;
                case SPECIAL:
                    return R.drawable.move_category_special;
                case EMPTY:
                    break;
            }
            return 0;
        }
    }
}
