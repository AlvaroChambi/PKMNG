package es.developer.achambi.pkmng.modules.search.move.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.core.ui.view.TypeView;
import es.developer.achambi.pkmng.modules.create.view.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.search.move.model.Move;
import es.developer.achambi.pkmng.modules.search.move.presenter.SearchMovePresenter;
import es.developer.achambi.pkmng.modules.search.move.view.presentation.SearchMovePresentation;

public class SearchMoveFragment extends BaseSearchListFragment
    implements ISearchMoveScreen {
    private static final String CURRENT_MOVE_ARGUMENT_KEY = "CURRENT_MOVE_ARGUMENT_KEY";

    private SearchMovePresenter presenter;
    private ArrayList<SearchMovePresentation> moves;
    private SearchMovePresentation move;

    public static final SearchMoveFragment newInstance( Bundle args ) {
        SearchMoveFragment fragment = new SearchMoveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static final Bundle getFragmentParams( Move move ) {
        Bundle bundle = new Bundle();
        bundle.putParcelable( CURRENT_MOVE_ARGUMENT_KEY, move );
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        move = SearchMovePresentation.Builder.buildPresentation( getActivity(),
                ((Move)getArguments().getParcelable( CURRENT_MOVE_ARGUMENT_KEY ))
        );
    }

    @Override
    public int getHeaderLayoutResource() {
        return R.layout.move_list_item_layout;
    }

    @Override
    public void onHeaderSetup(View header) {
        if( !move.empty ) {
            header.setVisibility(View.VISIBLE);
            TextView name = header.findViewById(R.id.move_name_text);
            TextView effect = header.findViewById(R.id.move_effect_text);
            ImageView category = header.findViewById(R.id.move_category_image);
            TypeView type = header.findViewById(R.id.move_type_image);
            TextView power = header.findViewById(R.id.move_power_value_text);
            TextView accuracy = header.findViewById(R.id.move_accuracy_value_text);
            TextView pp = header.findViewById(R.id.move_pp_value_text);

            name.setText( move.name );
            effect.setText( move.effect );
            category.setImageResource( move.categoryImageResource );
            type.setType( move.moveTypePresentation);
            type.setBackgroundTintList(
                    move.moveTypePresentation.typePresentation.backgroundColor );
            power.setText( move.power );
            accuracy.setText( move.accuracy );
            pp.setText( move.pp );

            name.setTextColor( ContextCompat.getColor( getActivity(), R.color.text_primary ) );
            effect.setTextColor( ContextCompat.getColor( getActivity(), R.color.text_primary ) );
            power.setTextColor( ContextCompat.getColor( getActivity(), R.color.text_primary ) );
            accuracy.setTextColor( ContextCompat.getColor( getActivity(), R.color.text_primary ) );
            pp.setTextColor( ContextCompat.getColor( getActivity(), R.color.text_primary ) );
        } else {
            header.setVisibility(View.GONE);
        }
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
        moves = new MovesPresentationBuilder().build( getActivity(),
                presenter.fetchMoves() );
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
        MovesListAdapter adapter = new MovesListAdapter(moves);
        adapter.setListener(presenter);
        return adapter;
    }

    @Override
    public void returnSelectedMove(Move move) {
        Intent dataIntent = getActivity().getIntent();
        dataIntent.putExtra(ConfigurationFragment.MOVE_ACTIVITY_RESULT_DATA_KEY, move);
        getActivity().setResult(Activity.RESULT_OK, dataIntent);
        getActivity().finish();
    }

    public class MovesListAdapter extends
            SearchAdapterDecorator<SearchMovePresentation, MovesListAdapter.MovesViewHolder> {
        public MovesListAdapter(ArrayList<SearchMovePresentation> data) {
            super(data);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.move_list_item_cardview_layout;
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
        public void bindViewHolder(MovesViewHolder holder, SearchMovePresentation item) {
            holder.name.setText( item.name );
            holder.effect.setText( item.effect );
            holder.category.setImageResource( item.categoryImageResource );
            holder.type.setType( item.moveTypePresentation);
            holder.type.setBackgroundTintList(
                    item.moveTypePresentation.typePresentation.backgroundColor );
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
            public TypeView type;
            public ImageView category;

            public MovesViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public class MovesPresentationBuilder {
        ArrayList<SearchMovePresentation> build(Context context, ArrayList<Move> moves ) {
            ArrayList<SearchMovePresentation> movePresentations = new ArrayList<>();
            for( Move move : moves ) {
                movePresentations.add(
                        SearchMovePresentation.Builder.buildPresentation( context, move )
                );
            }
            return movePresentations;
        }
    }
}
