package es.developer.achambi.pkmng.modules.search.move.view;

import android.app.Activity;
import android.content.Intent;
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
import es.developer.achambi.pkmng.modules.create.CreateConfigurationFragment;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.presenter.SearchMovePresenter;
import es.developer.achambi.pkmng.modules.search.move.view.presentation.MoveItemPresentation;

public class SearchMoveFragment extends BaseSearchListFragment
    implements ISearchMoveView {

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
            presenter = new SearchMovePresenter(this);
        }
        return presenter;
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        MovesListAdapter adapter = new MovesListAdapter(movesPresentation);
        adapter.setListener(presenter);
        return adapter;
    }

    @Override
    public void returnSelectedMove(Move move) {
        Intent dataIntent = getActivity().getIntent();
        dataIntent.putExtra(CreateConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY, move);
        getActivity().setResult(Activity.RESULT_OK, dataIntent);
        getActivity().finish();
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
            viewHolder.name = rootView.findViewById(R.id.move_name_text);
            viewHolder.effect = rootView.findViewById(R.id.move_effect_text);
            viewHolder.category = rootView.findViewById(R.id.move_category_image);
            viewHolder.type = rootView.findViewById(R.id.move_type_image);
            viewHolder.power = rootView.findViewById(R.id.move_power_value_text);
            viewHolder.accuracy = rootView.findViewById(R.id.move_accuracy_value_text);
            viewHolder.pp = rootView.findViewById(R.id.move_pp_value_text);
            return viewHolder;
        }

        @Override
        public void bindViewHolder(MovesViewHolder holder, MoveItemPresentation item) {
            holder.name.setText( item.name );
            holder.effect.setText( item.effect );
            holder.category.setImageResource( item.categoryImageResource );
            holder.type.setImageResource( item.typeImageResource );
            holder.power.setText( item.power );
            holder.accuracy.setText( item.accuracy );
            holder.pp.setText( item.pp );
        }

        @Override
        public int getAdapterViewType() {
            return R.id.move_view_id;
        }

        public class MovesViewHolder extends RecyclerView.ViewHolder{
            public TextView name;
            public TextView effect;
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
                        move.getName(),
                        move.getEffect(),
                        buildCategory(move.getCategory()),
                        buildType(move.getType()),
                        "Pow. " + move.getPower(),
                        "PP " + move.getPp(),
                        "Acc. " + move.getAccuracy()

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
