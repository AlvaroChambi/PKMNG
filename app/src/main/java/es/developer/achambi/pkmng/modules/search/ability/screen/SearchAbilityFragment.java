package es.developer.achambi.pkmng.modules.search.ability.screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.details.view.AbilityDetailsFragment;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.ability.presenter.SearchAbilityPresenter;
import es.developer.achambi.pkmng.modules.search.ability.screen.presentation.SearchAbilityPresentation;

public class SearchAbilityFragment extends BaseSearchListFragment implements ISearchAbilityScreen {
    private static final String CURRENT_ABILITY_ARGUMENT_KEY = "CURRENT_ABILITY_ARGUMENT_KEY";
    private static final String ABILITY_DETAILS_DIALOG_TAG = "ABILITY_DETAILS_DIALOG_TAG";

    private SearchAbilityPresenter presenter;
    private AbilitiesListAdapter adapter;
    private SearchAbilityPresentation ability;

    public static final SearchAbilityFragment newInstance( Bundle args ) {
        SearchAbilityFragment fragment = new SearchAbilityFragment();
        fragment.setArguments( args );
        return fragment;
    }

    public static final Bundle getFragmentParams( Ability ability ) {
        Bundle bundle = new Bundle();
        bundle.putParcelable( CURRENT_ABILITY_ARGUMENT_KEY, ability );
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ability = SearchAbilityPresentation.Builder.buildPresentation(
                ((Ability) getArguments().getParcelable( CURRENT_ABILITY_ARGUMENT_KEY ))
        );
    }

    @Override
    public int getHeaderLayoutResource() {
        return R.layout.ability_list_item_layout;
    }

    @Override
    public void onHeaderSetup(View header) {
        super.onHeaderSetup(header);
        if( !ability.empty ) {
            header.setVisibility(View.VISIBLE);
            TextView name = header.findViewById(R.id.ability_name_text);
            TextView description = header.findViewById(R.id.ability_description_text);

            name.setText(ability.name);
            description.setText(ability.description);
            name.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_primary));
            description.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_primary));
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
    public Presenter setupPresenter() {
        if( presenter == null ) {
            presenter = new SearchAbilityPresenter(this);
        }
        return presenter;
    }

    @Override
    public void doRequest() {
        super.doRequest();
        presenter.fetchAbilities(new ResponseHandler<ArrayList<Ability>>() {
            @Override
            public void onSuccess(Response<ArrayList<Ability>> response) {
                adapter.setData(
                        new AbilityPresentationDataBuilder().build( response.getData() ) );
                presentAdapterData();
                hideLoading();
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.setData(
                new AbilityPresentationDataBuilder().build( presenter.getAbilityList() ) );
        presentAdapterData();
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        adapter = new AbilitiesListAdapter();
        adapter.setListener(presenter);
        return adapter;
    }

    @Override
    public void showAbilityDetails(Ability ability) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        AbilityDetailsFragment.newInstance( ability ).show( transaction, ABILITY_DETAILS_DIALOG_TAG );
    }

    public class AbilitiesListAdapter extends
            SearchAdapterDecorator<SearchAbilityPresentation,AbilitiesListAdapter.AbilityViewHolder> {

        public AbilitiesListAdapter() {
            super();
        }

        @Override
        public int getLayoutResource() {
            return R.layout.ability_list_item_cardview_layout;
        }

        @Override
        public AbilityViewHolder createViewHolder(View rootView) {
            AbilityViewHolder viewHolder = new AbilityViewHolder( rootView );
            viewHolder.name = rootView.findViewById(R.id.ability_name_text);
            viewHolder.description = rootView.findViewById(R.id.ability_description_text);
            return viewHolder;
        }

        @Override
        public void bindViewHolder(AbilityViewHolder holder, SearchAbilityPresentation item) {
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
        public ArrayList<SearchAbilityPresentation> build(ArrayList<Ability> abilities ) {
            ArrayList<SearchAbilityPresentation> presentations = new ArrayList<>();
            for( Ability ability: abilities ) {
                presentations.add(SearchAbilityPresentation.Builder.buildPresentation(ability));
            }
            return presentations;
        }
    }
}
