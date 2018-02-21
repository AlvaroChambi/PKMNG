package es.developer.achambi.pkmng.modules.search.ability.view;

import android.app.FragmentTransaction;
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
import es.developer.achambi.pkmng.modules.details.view.AbilityDetailsFragment;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.ability.presenter.SearchAbilityPresenter;
import es.developer.achambi.pkmng.modules.search.ability.view.representation.AbilityViewPresentation;

public class SearchAbilityFragment extends BaseSearchListFragment implements ISearchAbilityView{
    private static final String ABILITY_DETAILS_DIALOG_TAG = "ABILITY_DETAILS_DIALOG_TAG";

    private SearchAbilityPresenter presenter;
    private ArrayList<AbilityViewPresentation> abilities;

    public static final SearchAbilityFragment newInstance( Bundle args ) {
        SearchAbilityFragment fragment = new SearchAbilityFragment();
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
            presenter = new SearchAbilityPresenter(this);
        }
        return presenter;
    }

    @Override
    public void doRequest() {
        abilities = new AbilityPresentationDataBuilder().build( presenter.fetchAbilities() );
        refreshAdapter();
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        AbilitiesListAdapter adapter = new AbilitiesListAdapter( abilities );
        adapter.setListener(presenter);
        return adapter;
    }

    @Override
    public void showAbilityDetails(Ability ability) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        AbilityDetailsFragment.newInstance( ability ).show( transaction, ABILITY_DETAILS_DIALOG_TAG );
    }

    public class AbilitiesListAdapter extends
            SearchAdapterDecorator<AbilityViewPresentation,AbilitiesListAdapter.AbilityViewHolder> {

        public AbilitiesListAdapter(ArrayList<AbilityViewPresentation> data) {
            super(data);
        }

        @Override
        public int getLayoutResource() {
            return R.layout.ability_list_item_layout;
        }

        @Override
        public AbilityViewHolder createViewHolder(View rootView) {
            AbilityViewHolder viewHolder = new AbilityViewHolder( rootView );
            viewHolder.name = rootView.findViewById(R.id.configuration_value_text);
            viewHolder.description = rootView.findViewById(R.id.ability_description_text);
            return viewHolder;
        }

        @Override
        public void bindViewHolder(AbilityViewHolder holder, AbilityViewPresentation item) {
            holder.name.setText(item.name);
            holder.description.setText(item.description);
        }

        @Override
        public int getAdapterViewType() {
            return R.id.ability_view_id;
        }

        public class AbilityViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public TextView description;


            public AbilityViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public class AbilityPresentationDataBuilder {
        public ArrayList<AbilityViewPresentation> build( ArrayList<Ability> abilities ) {
            ArrayList<AbilityViewPresentation> presentations = new ArrayList<>();
            for( Ability ability: abilities ) {
                presentations.add( new AbilityViewPresentation(
                        ability.getId(),
                        ability.getName(),
                        ability.getDescriptionShort()
                ) );
            }
            return presentations;
        }
    }
}
