package es.developer.achambi.pkmng.core.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public abstract class SearchAdapterDecorator<D extends SearchListData,VH extends RecyclerView.ViewHolder>
        implements ISearchAdapter<D,VH> {

    protected ISearchAdapter<D,VH> adapter;
    protected ArrayList<D> data;
    private OnItemClickedListener<D> listener;

    public SearchAdapterDecorator( ArrayList<D> data ) {
        this.data = data;
    }

    public SearchAdapterDecorator( ArrayList<D> data, ISearchAdapter<D,VH> adapter  ) {
        this.adapter = adapter;
        this.data = data;
    }

    @Override
    public void setListener( OnItemClickedListener<D> listener ) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( isValidAdapter( viewType ) ) {
            return createViewHolder(parent);
        }
        return adapter.onCreateViewHolder( parent, viewType );
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, final SearchListData item) {
        if( item.getViewType() == getAdapterViewType() ) {
            bindViewHolder( (VH)holder, (D)item );
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( listener != null ) {
                        listener.onItemClicked( (D)item );
                    }
                }
            });
        } else {
            adapter.onBindViewHolder( holder, item );
        }
    }

    @Override
    public ArrayList<D> getData() {
        if( adapter != null && adapter.getData() != null ) {
            data.addAll( adapter.getData() );
        }
        return data;
    }

    public boolean isValidAdapter(int viewType ){
        if( getAdapterViewType() == viewType ) {
            return true;
        }
        return false;
    }
}
