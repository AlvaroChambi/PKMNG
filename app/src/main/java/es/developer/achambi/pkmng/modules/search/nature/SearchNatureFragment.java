package es.developer.achambi.pkmng.modules.search.nature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.create.view.ConfigurationFragment;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;
import es.developer.achambi.pkmng.modules.search.nature.presenter.SearchNaturePresenter;
import es.developer.achambi.pkmng.modules.search.nature.view.ISearchNatureView;
import es.developer.achambi.pkmng.modules.search.nature.view.NatureViewPresentation;

public class SearchNatureFragment extends BaseSearchListFragment implements ISearchNatureView {
    private SearchNaturePresenter presenter;
    private ArrayList<NatureViewPresentation> natureList;

    public static final SearchNatureFragment newInstance( Bundle args ) {
        SearchNatureFragment fragment = new SearchNatureFragment();
        fragment.setArguments( args );
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
    public ViewPresenter setupPresenter() {
        if( presenter == null ) {
            presenter = new SearchNaturePresenter( this );
        }
        return presenter;
    }

    @Override
    public void doRequest() {
        natureList = new NaturePresentationDataBuilder().build(
                getActivity(), presenter.fetchNatureList()
        );

        refreshAdapter();
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        NatureListAdapter adapter = new NatureListAdapter(natureList);
        adapter.setListener(presenter);
        return adapter;
    }

    @Override
    public void returnSelectedNature(Nature nature) {
        Intent dataIntent = getActivity().getIntent();
        dataIntent.putExtra(ConfigurationFragment.NATURE_ACTIVITY_RESULT_DATA_KEY, nature);
        getActivity().setResult(Activity.RESULT_OK, dataIntent);
        getActivity().finish();
    }


    public class NatureListAdapter extends
            SearchAdapterDecorator<NatureViewPresentation, NatureListAdapter.NatureViewHolder> {
        public NatureListAdapter(ArrayList<NatureViewPresentation> data) {
            super(data);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.nature_list_item_cardview_layout;
        }

        @Override
        public NatureViewHolder createViewHolder(View rootView) {
            NatureViewHolder viewHolder = new NatureViewHolder(rootView);
            viewHolder.name = rootView.findViewById(R.id.nature_name_text);
            viewHolder.increasedStat = rootView.findViewById(R.id.nature_increased_stat_text);
            viewHolder.decreasedStat = rootView.findViewById(R.id.nature_decreased_stat_text);
            return viewHolder;
        }

        @Override
        public void bindViewHolder(NatureViewHolder holder, NatureViewPresentation item) {
            holder.name.setText(item.name);
            holder.increasedStat.setText(item.detail.increased);
            holder.decreasedStat.setText(item.detail.decreased);
        }

        @Override
        public int getAdapterViewType() {
            return R.id.nature_view_id;
        }

        public class NatureViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public TextView increasedStat;
            public TextView decreasedStat;

            public NatureViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public class NaturePresentationDataBuilder {
        public ArrayList<NatureViewPresentation> build( Context context,
                                                        ArrayList<Nature> natureList ) {
            ArrayList<NatureViewPresentation> presentations = new ArrayList<>();
            for( Nature nature : natureList ) {
                presentations.add(
                        NatureViewPresentation.Builder.buildPresentation( context, nature ) );
            }
            return presentations;
        }

    }
}
