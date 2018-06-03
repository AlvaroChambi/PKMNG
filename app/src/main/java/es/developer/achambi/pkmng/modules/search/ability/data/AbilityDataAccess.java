package es.developer.achambi.pkmng.modules.search.ability.data;

import java.util.ArrayList;
import java.util.List;

import es.developer.achambi.pkmng.core.db.dao.AbilitiesDAO;
import es.developer.achambi.pkmng.core.db.model.ability_value;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.data.utils.DataFormatUtil;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;

public class AbilityDataAccess implements IAbilityDataAccess{
    private AbilitiesDAO abilitiesDAO;
    private DataFormatUtil formatter;

    public AbilityDataAccess(AbilitiesDAO abilitiesDAO,
                             DataFormatUtil formatter) {
        this.abilitiesDAO = abilitiesDAO;
        this.formatter = formatter;
    }

    @Override
    public ArrayList<Ability> accessAbilities( int pokemonId ) {
        if( pokemonId < 1 ) {
            throw new IllegalIDException( pokemonId );
        }
        List<ability_value> rawAbilities = abilitiesDAO.getPokemonAbilities(pokemonId);
       return buildAbilities( rawAbilities );
    }

    @Override
    public ArrayList<Ability> accessAbilitiesQuery(int pokemonId, String query) throws IllegalIDException {
        if( pokemonId < 1 ) {
            throw new IllegalIDException( pokemonId );
        }
        if( query == null ) {
            return new ArrayList<>();
        }
        List<ability_value> rawAbilities = abilitiesDAO.getAbilitiesQuery(pokemonId, query+"%");
        return buildAbilities( rawAbilities );
    }

    @Override
    public Ability accessAbilityData( int abilityId ) throws IllegalIDException {
        if( abilityId == -1 ) {
            return new Ability();
        }
        if( abilityId < 1 ) {
            throw new IllegalIDException( abilityId );
        }
        ability_value rawAbility = abilitiesDAO.getAbility(abilityId);
        return buildAbility( rawAbility );
    }

    private ArrayList<Ability> buildAbilities( List<ability_value> rawAbilities ) {
        ArrayList<Ability> abilities = new ArrayList<>(rawAbilities.size());
        for (ability_value currentAbility : rawAbilities) {
            abilities.add( buildAbility(currentAbility) );
        }
        return abilities;
    }

    private Ability buildAbility( ability_value rawAbility ) {
        Ability ability = new Ability();
        if( rawAbility == null ) {
            return ability;
        }
        ability.setId( rawAbility.id );
        ability.setName( rawAbility.name );
        ability.setDescription( formatter.formatDescriptionMessage(rawAbility.effect) );
        ability.setDescriptionShort( formatter.formatDescriptionMessage(rawAbility.shortEffect) );

        return ability;
    }
}
