package es.developer.achambi.pkmng.modules.search.item.view;

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
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.modules.details.view.ItemDetailsFragment;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.item.presenter.SearchItemsPresenter;
import es.developer.achambi.pkmng.modules.search.item.view.presentation.SearchItemPresentation;

public class SearchItemFragment extends BaseSearchListFragment
        implements ISearchItemScreen {
    private static final String ITEM_DETAILS_DIALOG_TAG = "ITEM_DETAILS_DIALOG_TAG";
    private static final String CURRENT_ITEM_ARGUMENT_KEY = "CURRENT_ITEM_ARGUMENT_KEY";

    private SearchItemsPresenter presenter;
    private ItemListAdapter adapter;
    private SearchItemPresentation item;

    public static final SearchItemFragment newInstance( Bundle args ) {
        SearchItemFragment fragment = new SearchItemFragment();
        fragment.setArguments( args );

        return fragment;
    }

    public static final Bundle getFragmentArgs( Item item ) {
        Bundle bundle = new Bundle();
        bundle.putParcelable( CURRENT_ITEM_ARGUMENT_KEY, item );
        return bundle;
    }

    @Override
    public int getHeaderLayoutResource() {
        return R.layout.item_list_item_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = SearchItemPresentation.Builder.buildPresentation(
                ((Item)getArguments().getParcelable( CURRENT_ITEM_ARGUMENT_KEY )) );
    }

    @Override
    public void onHeaderSetup(View header) {
        if( !item.empty ) {
            header.setVisibility(View.VISIBLE);
            TextView itemName = header.findViewById(R.id.item_name_text);
            TextView itemDescription = header.findViewById(R.id.item_description_text);
            itemName.setText( item.name );
            itemDescription.setText( item.description );
            itemName.setTextColor( ContextCompat.getColor(getActivity(), R.color.text_primary) );
            itemDescription.setTextColor(
                    ContextCompat.getColor(getActivity(), R.color.text_primary) );
        } else {
            header.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewSetup(View view, @Nullable Bundle savedInstanceState) {
        super.onViewSetup(view, savedInstanceState);
        if( savedInstanceState == null) {
            doRequest();
        }
    }

    @Override
    public SearchAdapterDecorator provideAdapter() {
        adapter = new ItemListAdapter();
        adapter.setListener(presenter);
        return adapter;
    }

    @Override
    public Presenter setupPresenter() {
        if( presenter == null ) {
            presenter = new SearchItemsPresenter(this);
        }
        return presenter;
    }

    @Override
    public void doRequest() {
        super.doRequest();
        presenter.fetchItems(new ResponseHandler<ArrayList<Item>>() {
            @Override
            public void onSuccess(Response<ArrayList<Item>> response) {
                adapter.setData(
                        new ItemResultDataBuilder().buildViewRepresentation(
                                response.getData()
                        ) );
                presentAdapterData();
                hideLoading();
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.setData(
                new ItemResultDataBuilder().buildViewRepresentation( presenter.getItems() ) );
        presentAdapterData();
    }

    @Override
    public void showItemDetails(Item item) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ItemDetailsFragment.newInstance( item ).show( transaction, ITEM_DETAILS_DIALOG_TAG );
    }

    public class ItemResultDataBuilder {
        public ArrayList<SearchItemPresentation> buildViewRepresentation(
                ArrayList<Item> items ) {
            ArrayList<SearchItemPresentation> representations = new ArrayList<>();
            for( Item item: items ) {
                representations.add(
                        SearchItemPresentation.Builder.buildPresentation( item )
                );
            }

            return representations;
        }
    }

    public class ItemListAdapter extends SearchAdapterDecorator<
            SearchItemPresentation, ItemListAdapter.ItemViewHolder> {

        public ItemListAdapter() {
            super();
        }

        @Override
        public int getLayoutResource() {
            return R.layout.item_list_item_cardview_layout;
        }

        @Override
        public int getAdapterViewType() {
            return R.id.item_view_id;
        }

        @Override
        public ItemViewHolder createViewHolder( View rootView ) {
            ItemViewHolder viewHolder = new ItemViewHolder(rootView);

            viewHolder.itemName = rootView.findViewById(R.id.item_name_text);
            viewHolder.itemDescription = rootView.findViewById(R.id.item_description_text);

            return viewHolder;
        }

        @Override
        public void bindViewHolder(ItemViewHolder holder, SearchItemPresentation item) {
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
