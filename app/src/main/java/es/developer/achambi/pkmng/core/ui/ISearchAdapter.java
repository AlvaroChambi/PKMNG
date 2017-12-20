package es.developer.achambi.pkmng.core.ui;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.developer.achambi.pkmng.modules.overview.view.representation.SearchListData;

public interface ISearchAdapter<D extends SearchListData,VH extends RecyclerView.ViewHolder> {
    interface OnItemClickedListener<D> {
        void onItemClicked( D item );
    }
    int getAdapterViewType();

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType );
    void onBindViewHolder(RecyclerView.ViewHolder holder, final SearchListData item );

    public void setListener( OnItemClickedListener<D> listener );
    void bindViewHolder( VH holder, D item );
    VH createViewHolder( ViewGroup parent );
    ArrayList<D> getData( );
}
