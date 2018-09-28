package es.developer.achambi.coreframework.ui;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.developer.achambi.coreframework.ui.presentation.SearchListData;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public abstract class SearchAdapterDecorator<D extends SearchListData,VH extends RecyclerView.ViewHolder> {
    public interface OnItemClickedListener<D> {
        void onItemClicked( D item );
    }
    protected SearchAdapterDecorator<D,VH> adapter;
    protected ArrayList<D> data;
    private OnItemClickedListener<D> listener;

    public SearchAdapterDecorator( SearchAdapterDecorator<D,VH> adapter ) {
        this.adapter = adapter;
        data = new ArrayList<>();
    }

    public SearchAdapterDecorator( ArrayList<D> data ) {
        this.data = data;
    }

    public SearchAdapterDecorator() {
        data = new ArrayList<>();
    }

    public SearchAdapterDecorator( ArrayList<D> data, SearchAdapterDecorator<D,VH> adapter  ) {
        this.adapter = adapter;
        this.data = data;
    }

    public void setListener( OnItemClickedListener<D> listener ) {
        this.listener = listener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType,
                                                      final SortedList rootData ) {
        if( isValidAdapter( viewType ) ) {
            View rootView = LayoutInflater.from(parent.getContext())
                    .inflate(getLayoutResource(), parent, false);
            final RecyclerView.ViewHolder viewHolder = createViewHolder( rootView );
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    if( position != NO_POSITION && listener != null ) {
                        listener.onItemClicked( (D)rootData.get( position ) );
                    }
                }
            });
            return viewHolder;
        }
        return adapter.onCreateViewHolder( parent, viewType, rootData );
    }

    public void onBindViewHolder( RecyclerView.ViewHolder holder, final SearchListData item) {
        if( item.getViewType() == getAdapterViewType() ) {
            bindViewHolder( (VH)holder, (D)item );
        } else {
            adapter.onBindViewHolder( holder, item );
        }
    }

    /**
     * Build the data list adding any nested decorator's data if present
     * @return data list with all available decorators information, empty list if no data
     * is available for any decorator
     */
    public ArrayList<D> buildData() {
        ArrayList<D> tempData = new ArrayList<>();
        tempData.addAll( data );
        if( adapter != null ) {
            tempData.addAll( adapter.buildData() );

        }
        return tempData;
    }

    public void setData(ArrayList<D> dataList ) {
        this.data = dataList;
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
