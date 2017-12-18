package es.developer.achambi.pkmng.modules.search.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseRequestFragment;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.search.model.Item;
import es.developer.achambi.pkmng.modules.search.presenter.SearchItemsPresenter;
import es.developer.achambi.pkmng.modules.search.view.representation.ItemResultViewRepresentation;

public class SearchItemFragment extends BaseRequestFragment{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SearchItemsPresenter presenter;
    private SearchListAdapter adapter;

    public static final SearchItemFragment newInstance( Bundle args ) {
        SearchItemFragment fragment = new SearchItemFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.search_item_fragment_layout;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        presenter = (SearchItemsPresenter)getPresenter();
        recyclerView = view.findViewById(R.id.search_result_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());

        if( !isViewRecreated() ) {
            doRequest();
        }
    }

    @Override
    public ViewPresenter getPresenter() {
        if( presenter == null ) {
            presenter = new SearchItemsPresenter();
        }
        return presenter;
    }

    @Override
    public void doRequest() {
        ArrayList<Item> itemsList = presenter.fetchItems();
        ItemResultDataBuilder dataBuilder = new ItemResultDataBuilder();

        adapter = new SearchListAdapter( dataBuilder.buildViewRepresentation(
                getResources(), itemsList ) );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public class ItemResultDataBuilder {
        public ArrayList<ItemResultViewRepresentation> buildViewRepresentation(
                Resources resources, ArrayList<Item> items ) {
            ArrayList<ItemResultViewRepresentation> representations = new ArrayList<>();
            for( Item item: items ) {
                representations.add(
                        new ItemResultViewRepresentation(
                                item.getName(),
                                item.getImageUrl(),
                                item.getDescriptionShort()
                        )
                );
            }

            return representations;
        }
    }

    public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ItemViewHolder> {
        private ArrayList<ItemResultViewRepresentation> itemsList;

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from( parent.getContext())
                    .inflate(R.layout.item_list_item_layout, parent, false );
            ItemViewHolder viewHolder = new ItemViewHolder(rootView);

            viewHolder.itemName = rootView.findViewById(R.id.item_name_text);
            viewHolder.itemDescription = rootView.findViewById(R.id.item_description_text);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            ItemResultViewRepresentation itemResultViewRepresentation = itemsList.get( position );
            holder.itemName.setText( itemResultViewRepresentation.name );
            holder.itemDescription.setText( itemResultViewRepresentation.shortDescription );
        }

        @Override
        public int getItemCount() {
            return itemsList.size();
        }

        public SearchListAdapter( ArrayList<ItemResultViewRepresentation> itemsList ) {
            this.itemsList = itemsList;
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            public TextView itemName;
            public TextView itemDescription;

            public ItemViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
