package es.developer.achambi.pkmng.core.ui;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.presentation.SearchListData;

public class BaseSearchAdapter extends RecyclerView.Adapter{
    private SortedList<SearchListData> data;
    private SearchAdapterDecorator adapter;

    public BaseSearchAdapter( SearchAdapterDecorator adapter ) {
        this.adapter = adapter;
        data = new SortedList<>(SearchListData.class,
                new SortedList.Callback<SearchListData>() {
                    @Override
                    public void onInserted(int position, int count) {
                        notifyItemRangeInserted( position, count );
                    }

                    @Override
                    public void onRemoved(int position, int count) {
                        notifyItemRangeRemoved( position, count );
                    }

                    @Override
                    public void onMoved(int fromPosition, int toPosition) {
                        notifyItemMoved( fromPosition, toPosition );
                    }

                    @Override
                    public int compare(SearchListData o1, SearchListData o2) {
                        int compare;
                        if( o1.getViewType() > o2.getViewType() ) {
                            compare = 1;
                        } else if( o1.getViewType() == o2.getViewType() ) {
                            compare = 0;
                        } else {
                            compare = -1;
                        }

                        if( compare != 0 ) {
                            return compare;
                        }

                        if( o1.getId() > o2.getId() ) {
                            compare = 1;
                        } else if( o1.getId() == o2.getId() ) {
                            compare = 0;
                        } else {
                            compare = -1;
                        }
                        return compare;
                    }

                    @Override
                    public void onChanged(int position, int count) {
                        notifyItemRangeChanged( position, count );
                    }

                    @Override
                    public boolean areContentsTheSame(SearchListData oldItem, SearchListData newItem) {
                        return oldItem.equals( newItem );
                    }

                    @Override
                    public boolean areItemsTheSame(SearchListData item1, SearchListData item2) {
                        return ( item1.getViewType() == item2.getViewType()
                            && item1.getId() == item2.getId() );
                    }
                });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapter.onCreateViewHolder( parent, viewType, data );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapter.onBindViewHolder( holder, data.get(position) );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewType();
    }

    public void updateData() {
        ArrayList<SearchListData> dataList = adapter.buildData();
        data.beginBatchedUpdates();
        data.replaceAll( dataList );
        data.endBatchedUpdates();
    }
}