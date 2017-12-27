package es.developer.achambi.pkmng.modules.search.move.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.presenter.SearchMovePresenter;

public class SearchMoveFragment extends BaseSearchListFragment {

    private SearchMovePresenter presenter;
    private ArrayList<MoveItemPresentation> movesPresentation;

    public static final SearchMoveFragment newInstance( Bundle args ) {
        SearchMoveFragment fragment = new SearchMoveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
        if( !isViewRecreated() ) {
            doRequest();
        }
    }

    @Override
    public void doRequest() {
        movesPresentation = new MovesPresentationBuilder().build( presenter.fetchMoves() );
        refreshAdapter();
    }

    @Override
    public ViewPresenter setupPresenter() {
        if( presenter == null ) {
            presenter = new SearchMovePresenter();
        }
        return presenter;
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        MovesListAdapter adapter = new MovesListAdapter(movesPresentation);
        return adapter;
    }

    public class MovesListAdapter extends
            SearchAdapterDecorator<MoveItemPresentation, MovesListAdapter.MovesViewHolder> {
        public MovesListAdapter(ArrayList<MoveItemPresentation> data) {
            super(data);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.move_list_item_layout;
        }

        @Override
        public MovesViewHolder createViewHolder(View rootView) {
            MovesViewHolder viewHolder = new MovesViewHolder(rootView);
            return viewHolder;
        }

        @Override
        public void bindViewHolder(MovesViewHolder holder, MoveItemPresentation item) {

        }

        @Override
        public int getAdapterViewType() {
            return R.id.move_view_id;
        }

        public class MovesViewHolder extends RecyclerView.ViewHolder{
            public TextView power;
            public TextView accuracy;
            public TextView pp;
            public ImageView type;
            public ImageView category;

            public MovesViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public class MovesPresentationBuilder {
        ArrayList<MoveItemPresentation> build( ArrayList<Move> moves ) {
            ArrayList<MoveItemPresentation> movePresentations = new ArrayList<>();
            for( Move move : moves ) {
                MoveItemPresentation presentation = new MoveItemPresentation(
                        move.getId(),
                        buildCategory(move.getCategory()),
                        buildType(move.getType()),
                        "Power: " + move.getPower(),
                        "PP: " + move.getPp(),
                        "Accuracy: " + move.getAccuracy()

                );
                movePresentations.add(presentation);
            }
            return movePresentations;
        }

        private int buildType( Pokemon.Type type ) {
            switch (type) {
                case ELECTRIC:
                    break;
                case GROUND:
                    return R.drawable.type_ground_icon;
                case EMPTY:
                    break;
            }
            return 0;
        }

        private int buildCategory( String category ) {
            if( category.equals("Physical") ) {
                return R.drawable.move_category_physical;
            }
            return 0;
        }
    }
}
