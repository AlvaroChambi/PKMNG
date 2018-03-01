package es.developer.achambi.pkmng.modules.search.ability.presenter;

import android.os.Bundle;

import java.util.ArrayList;

import es.developer.achambi.pkmng.core.ui.SearchAdapterDecorator;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.ability.view.ISearchAbilityView;
import es.developer.achambi.pkmng.modules.search.ability.view.representation.SearchAbilityPresentation;

public class SearchAbilityPresenter implements ISearchAbilityPresenter,
        SearchAdapterDecorator.OnItemClickedListener<SearchAbilityPresentation>{
    private static final String DATA_SAVED_STATE = "DATA_SAVED_STATE";
    private ArrayList<Ability> data;
    private ISearchAbilityView searchAbilityView;

    public SearchAbilityPresenter( ISearchAbilityView searchAbilityView ) {
        this.searchAbilityView = searchAbilityView;
    }

    @Override
    public ArrayList<Ability> fetchAbilities() {
        data = buildAbilityData();
        return data;
    }

    ArrayList<Ability> buildAbilityData() {
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
