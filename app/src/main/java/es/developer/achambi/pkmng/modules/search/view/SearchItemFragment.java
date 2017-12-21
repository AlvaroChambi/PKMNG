package es.developer.achambi.pkmng.modules.search.view;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.BaseSearchListFragment;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.details.view.ItemDetailsFragment;
import es.developer.achambi.pkmng.modules.search.model.Item;
import es.developer.achambi.pkmng.modules.search.presenter.SearchItemsPresenter;
import es.developer.achambi.pkmng.modules.search.view.representation.ItemResultViewRepresentation;

public class SearchItemFragment extends BaseSearchListFragment
        implements ISearchItemView {
    private static final String ITEM_DETAILS_DIALOG_TAG = "ITEM_DETAILS_DIALOG_TAG";

    private SearchItemsPresenter presenter;
    private ArrayList<ItemResultViewRepresentation> items;

    public static final SearchItemFragment newInstance( Bundle args ) {
        SearchItemFragment fragment = new SearchItemFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
        presenter = (SearchItemsPresenter)getPresenter();
        if( !isViewRecreated() ) {
            doRequest();
        }
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        ItemListAdapter adapter = new ItemListAdapter( items );
        adapter.setListener(presenter);
        return adapter;
    }

    @Override
    public ViewPresenter getPresenter() {
        if( presenter == null ) {
            presenter = new SearchItemsPresenter(this);
        }
        return presenter;
    }

    @Override
    public void doRequest() {
        items = new ItemResultDataBuilder().buildViewRepresentation( presenter.fetchItems() );
        refreshAdapter();
    }

    @Override
    public void showItemDetails(Item item) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ItemDetailsFragment.newInstance( item ).show( transaction, ITEM_DETAILS_DIALOG_TAG );
    }

    public class ItemResultDataBuilder {
        public ArrayList<ItemResultViewRepresentation> buildViewRepresentation(
                ArrayList<Item> items ) {
            ArrayList<ItemResultViewRepresentation> representations = new ArrayList<>();
            for( Item item: items ) {
                representations.add(
                        new ItemResultViewRepresentation(
                                item.getId(),
                                item.getName(),
                                item.getImageUrl(),
                                item.getDescriptionShort()
                        )
                );
            }

            return representations;
        }
    }

    public class ItemListAdapter extends SearchAdapterDecorator<
            ItemResultViewRepresentation, ItemListAdapter.ItemViewHolder> {

        public ItemListAdapter(ArrayList<ItemResultViewRepresentation> data) {
            super(data);
        }

        @Override
        public int getAdapterViewType() {
            return R.id.item_view_id;
        }

        @Override
        public ItemViewHolder createViewHolder(ViewGroup parent) {
            View rootView = LayoutInflater.from( parent.getContext())
                    .inflate(R.layout.item_list_item_layout, parent, false );
            ItemViewHolder viewHolder = new ItemViewHolder(rootView);

            viewHolder.itemName = rootView.findViewById(R.id.item_name_text);
            viewHolder.itemDescription = rootView.findViewById(R.id.item_description_text);

            return viewHolder;
        }

        @Override
        public void bindViewHolder(ItemViewHolder holder, ItemResultViewRepresentation item) {
            holder.itemName.setText( item.name );
            holder.itemDescription.setText( item.description);
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
