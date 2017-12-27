package es.developer.achambi.pkmng.modules.search.move.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class SearchMovePresenter implements ISearchMovePresenter{
    private ArrayList<Move> data;

    @Override
    public ArrayList<Move> fetchMoves() {
        data = buildMoves();
        return data;
    }

    private ArrayList<Move> buildMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for( int i = 0; i < 802; i++ ) {
            Move move = new Move();
            move.setId( i );
            move.setAccuracy(100);
            move.setPower(90);
            move.setPp(20);
            move.setType(Pokemon.Type.GROUND);
            move.setCategory("Physical");
            moves.add(move);
        }
        return moves;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {

    }
}
