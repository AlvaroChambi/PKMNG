package es.developer.achambi.pkmng.modules.search.nature.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;
import es.developer.achambi.pkmng.modules.search.nature.view.ISearchNatureView;
import es.developer.achambi.pkmng.modules.search.nature.view.SearchNaturePresentation;

public class SearchNaturePresenter implements ISearchNaturePresenter,
        SearchAdapterDecorator.OnItemClickedListener<SearchNaturePresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";

    private ISearchNatureView searchNatureView;
    private ArrayList<Nature> data;

    public SearchNaturePresenter( ISearchNatureView searchNatureView ) {
        this.searchNatureView = searchNatureView;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList( DATA_SAVED_STATE, data );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        data = bundle.getParcelableArrayList( DATA_SAVED_STATE );
    }

    @Override
    public ArrayList<Nature> fetchNatureList() {
        data = buildNatureList();
        return data;
    }

    @Override
    public void onItemClicked(SearchNaturePresentation item) {
        for( Nature nature : data ) {
            if( item.id == nature.getId() ) {
                searchNatureView.returnSelectedNature( nature );
            }
        }
    }

    private ArrayList<Nature> buildNatureList() {
        ArrayList<Nature> natureList = new ArrayList<>();
        for( int i = 0; i < 25 ; i++ ) {
            Nature nature = new Nature();
            nature.setId(i);
            nature.setName("modest");
            nature.setIncreasedStat(Stat.ATTACK);
            nature.setDecreasedStat(Stat.DEFENSE);
            natureList.add( nature );
        }
        return natureList;
    }
}
