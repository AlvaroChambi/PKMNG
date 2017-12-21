package es.developer.achambi.pkmng.modules.search.nature;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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
import es.developer.achambi.pkmng.modules.create.CreateConfigurationFragment;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
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
        presenter = (SearchNaturePresenter)getPresenter();
        if( !isViewRecreated() ) {
            doRequest();
        }
    }

    @Override
    public ViewPresenter getPresenter() {
        if( presenter == null ) {
            presenter = new SearchNaturePresenter( this );
        }
        return presenter;
    }

    @Override
    public void doRequest() {
        natureList = new NaturePresentationDataBuilder().build(
                getResources(), presenter.fetchNatureList()
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
    public void onNatureClicked(Nature nature) {
        Intent dataIntent = getActivity().getIntent();
        dataIntent.putExtra(CreateConfigurationFragment.NATURE_ACTIVITY_RESULT_DATA_KEY, nature);
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
            return R.layout.nature_list_item_layout;
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
            holder.increasedStat.setText(item.increasedStat);
            holder.decreasedStat.setText(item.decreasedStat);
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
        public ArrayList<NatureViewPresentation> build( Resources resources,
                                                        ArrayList<Nature> natureList ) {
            ArrayList<NatureViewPresentation> presentations = new ArrayList<>();
            for( Nature nature : natureList ) {
                presentations.add( new NatureViewPresentation(
                        nature.getId(),
                        nature.getName(),
                        natureStat(resources, nature.getIncreasedStat(), true),
                        natureStat(resources, nature.getDecreasedStat(), false)
                ) );
            }
            return presentations;
        }

        private String natureStat( Resources resources, Stat stat, boolean increased ) {
            if( increased ) {
                String statText = statFormat(resources, stat);
                return resources.getString( R.string.nature_increased_stat_text, statText );
            } else {
                String statText = statFormat(resources, stat);
                return resources.getString( R.string.nature_decreased_stat_text, statText );
            }
        }

        private String statFormat(Resources resources, Stat stat) {
            switch (stat) {
                case HP:
                    return resources.getString(R.string.stat_hp_text);
                case DEFENSE:
                    return resources.getString(R.string.stat_defense_text);
                case ATTACK:
                    return resources.getString(R.string.stat_attack_text);
                case SP_ATTACK:
                    return resources.getString(R.string.stat_sp_attack_text);
                case SP_DEFENSE:
                    return resources.getString(R.string.stat_sp_defense_text);
                case SPEED:
                    return resources.getString(R.string.stat_speed_text);
                default:
                    return "";
            }
        }
    }
}
