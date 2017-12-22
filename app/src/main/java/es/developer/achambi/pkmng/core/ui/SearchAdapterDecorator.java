package es.developer.achambi.pkmng.core.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public abstract class SearchAdapterDecorator<D extends SearchListData,VH extends RecyclerView.ViewHolder> {
    public interface OnItemClickedListener<D> {
        void onItemClicked( D item );
    }
    protected SearchAdapterDecorator<D,VH> adapter;
    protected ArrayList<D> data;
    private OnItemClickedListener<D> listener;

    public SearchAdapterDecorator( ArrayList<D> data ) {
        this.data = data;
    }

    public SearchAdapterDecorator( ArrayList<D> data, SearchAdapterDecorator<D,VH> adapter  ) {
        this.adapter = adapter;
        this.data = data;
    }

    public void setListener( OnItemClickedListener<D> listener ) {
        this.listener = listener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( isValidAdapter( viewType ) ) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(getLayoutResource(), parent, false);
            return createViewHolder( rootView );
        }
        return adapter.onCreateViewHolder( parent, viewType );
    }

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

    public abstract int getLayoutResource();
    public abstract VH createViewHolder( View rootView );
    public abstract void bindViewHolder( VH holder, D item );
    public abstract int getAdapterViewType();
}
