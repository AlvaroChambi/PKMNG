package es.developer.achambi.pkmng.core.ui;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public class BaseSearchAdapter extends RecyclerView.Adapter{
    private ArrayList<SearchListData> data;
    private SearchAdapterDecorator adapter;

    public BaseSearchAdapter( SearchAdapterDecorator adapter ) {
        this.adapter = adapter;
        data = this.adapter.getData();
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


}