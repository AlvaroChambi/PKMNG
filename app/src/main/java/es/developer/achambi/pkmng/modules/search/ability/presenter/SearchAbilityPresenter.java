package es.developer.achambi.pkmng.modules.search.ability.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.threading.ResponseHandlerDecorator;
import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.search.ability.data.IAbilityDataAccess;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.ability.screen.ISearchAbilityScreen;
import es.developer.achambi.pkmng.modules.search.ability.screen.presentation.SearchAbilityPresentation;

public class SearchAbilityPresenter extends ISearchAbilityPresenter
        implements SearchAdapterDecorator.OnItemClickedListener<SearchAbilityPresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";
    private ArrayList<Ability> data;
    private ISearchAbilityScreen searchAbilityScreen;
    private IAbilityDataAccess dataAccess;

    public SearchAbilityPresenter( ISearchAbilityScreen searchAbilityScreen,
                                   IAbilityDataAccess abilityDataAccess,
                                   MainExecutor executor ) {
        super( searchAbilityScreen, executor );
        this.searchAbilityScreen = searchAbilityScreen;
        data = new ArrayList<>();
        this.dataAccess = abilityDataAccess;
    }

    @Override
    public void fetchAbilities(final int pokemonId,
                               final ResponseHandler<ArrayList<Ability>> responseHandler ) {
        ResponseHandler<ArrayList<Ability>> handler =
                new ResponseHandlerDecorator<ArrayList<Ability>>( responseHandler ) {
            @Override
            public void onSuccess(Response<ArrayList<Ability>> response) {
                data = response.getData();
                responseHandler.onSuccess( response );
            }
        };

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.accessAbilities( pokemonId ) );
            }
        }, handler);
    }

    @Override
    public void fetchAbilitiesQuery(final int pokemonId, final String query,
                                    final ResponseHandler<ArrayList<Ability>> responseHandler) {
        ResponseHandler<ArrayList<Ability>> handler =
                new ResponseHandlerDecorator<ArrayList<Ability>>( responseHandler ) {
                    @Override
                    public void onSuccess(Response<ArrayList<Ability>> response) {
                        data = response.getData();
                        responseHandler.onSuccess( response );
                    }
                };

        request(new Request() {
            @Override
            public Response perform() {
                return new Response<>( dataAccess.accessAbilitiesQuery( pokemonId, query ) );
            }
        }, handler);
    }

    public ArrayList<Ability> getAbilityList() {
        return data;
    }

    @Override
    public void onItemClicked(SearchAbilityPresentation item) {
        for( Ability ability : data ) {
            if( item.id == ability.getId() ) {
                searchAbilityScreen.showAbilityDetails( ability );
                return;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList( DATA_SAVED_STATE, data );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        data = bundle.getParcelableArrayList( DATA_SAVED_STATE );
    }
}
