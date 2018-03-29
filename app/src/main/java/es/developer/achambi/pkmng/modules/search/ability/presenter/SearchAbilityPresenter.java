package es.developer.achambi.pkmng.modules.search.ability.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.ability.view.ISearchAbilityScreen;
import es.developer.achambi.pkmng.modules.search.ability.view.presentation.SearchAbilityPresentation;

public class SearchAbilityPresenter implements ISearchAbilityPresenter,
        SearchAdapterDecorator.OnItemClickedListener<SearchAbilityPresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";
    private ArrayList<Ability> data;
    private ISearchAbilityScreen searchAbilityView;

    public SearchAbilityPresenter( ISearchAbilityScreen searchAbilityView ) {
        this.searchAbilityView = searchAbilityView;
    }

    @Override
    public void fetchAbilities(final ResponseHandler<ArrayList<Ability>> responseHandler ) {
        ResponseHandler<ArrayList<Ability>> handler = new ResponseHandler<ArrayList<Ability>>() {
            @Override
            public void onSuccess(Response<ArrayList<Ability>> response) {
                data = response.getData();
                responseHandler.onSuccess( response );
            }
        };

        MainExecutor.executor().executeRequest(new Request() {
            @Override
            public Response perform() {
                return new Response( buildAbilityData() );
            }
        }, handler);
    }

    public ArrayList<Ability> buildAbilityData() {
        ArrayList<Ability> abilities = new ArrayList<>();
        for( int i = 0; i < 5; i++ ) {
            Ability ability = new Ability();
            ability.setId(i);
            ability.setName("overgrow");
            ability.setDescription("When this Pokémon has 1/3 or less of its HP remaining, its grass-type moves inflict 1.5× as much regular damage.");
            ability.setDescriptionShort("Strengthens grass moves to inflict 1.5× damage at 1/3 max HP or less.");
            abilities.add(ability);
        }
        return abilities;
    }

    public ArrayList<Ability> getAbilityList() {
        return data;
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
    public void onItemClicked(SearchAbilityPresentation item) {
        for( Ability ability : data ) {
            if( item.id == ability.getId() ) {
                searchAbilityView.showAbilityDetails( ability );
                return;
            }
        }
    }
}
