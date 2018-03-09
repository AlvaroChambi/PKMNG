package es.developer.achambi.pkmng.modules.search.move.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.view.ISearchMoveScreen;
import es.developer.achambi.pkmng.modules.search.move.view.presentation.SearchMovePresentation;

public class SearchMovePresenter implements ISearchMovePresenter,
        SearchAdapterDecorator.OnItemClickedListener<SearchMovePresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";

    private ArrayList<Move> data;
    private ISearchMoveScreen view;

    public SearchMovePresenter( ISearchMoveScreen view ) {
        this.view = view;
    }

    @Override
    public ArrayList<Move> fetchMoves() {
        data = buildMoves();
        return data;
    }

    public ArrayList<Move> getMoves() {
        return data;
    }

    private ArrayList<Move> buildMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for( int i = 0; i < 802; i++ ) {
            Move move = new Move();
            move.setId( i );
            move.setName("Earthquake");
            move.setEffect("Inflicts regular damage with no additional effect.");
            move.setAccuracy(100);
            move.setPower(90);
            move.setPp(20);
            move.setType(Type.GROUND);
            move.setCategory(Move.Category.PHYSICAL);
            moves.add(move);
        }
        return moves;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList( DATA_SAVED_STATE, data );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        data = bundle.getParcelableArrayList( DATA_SAVED_STATE );
    }

    @Override
    public void onItemClicked(SearchMovePresentation item) {
        for( Move move : data ) {
            if( item.id == move.getId() ) {
                view.returnSelectedMove( move );
            }
        }
    }
}
