package es.developer.achambi.pkmng.modules.data;

import java.util.List;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.model.stat_value;
import es.developer.achambi.pkmng.modules.overview.model.EvSet;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public class StatDataAccess {
    private static final String SP_ATTACK = "special-attack";
    private static final String SP_DEFENSE = "special-defense";

    private AppDatabase database;

    public StatDataAccess(AppDatabase database) {
        this.database = database;
    }

    public StatsSet accessPokemonStatsData( int pokemonId ) {
        List<stat_value> rawStats =
                database.statsModel().getStats(pokemonId);
        StatsSet statsSet = new StatsSet();
        populateStats( statsSet, rawStats );
        return statsSet;
    }

    public EvSet accessEvsSetData( int evsId ) {
        List<stat_value> rawStats =
                database.statsModel().getEvsSet(evsId);
        EvSet statsSet = new EvSet();
        populateStats( statsSet, rawStats );
        return statsSet;
    }

    public Stat accessStatData(int statId ) {
        return parseStat( database.statsModel().getStat( statId ).identifier );
    }

    private void populateStats( StatsSet statsSet, List<stat_value> stats ) {
        for( stat_value current : stats ) {
            switch ( parseStat(current.name) ) {
                case HP:
                    statsSet.setHP( current.value );
                    break;
                case ATTACK:
                    statsSet.setAttack( current.value );
                    break;
                case DEFENSE:
                    statsSet.setDefense( current.value );
                    break;
                case SP_ATTACK:
                    statsSet.setSpAttack( current.value );
                    break;
                case SP_DEFENSE:
                    statsSet.setSpDefense( current.value );
                    break;
                case SPEED:
                    statsSet.setSpeed( current.value );
                    break;
                case NONE:
                    break;
            }
        }
    }

    private Stat parseStat( String identifier ) {
        if( identifier.equalsIgnoreCase(Stat.HP.toString()) ) {
            return Stat.HP;
        } else if( identifier.equalsIgnoreCase(Stat.ATTACK.toString()) ) {
            return Stat.ATTACK;
        } else if( identifier.equalsIgnoreCase(Stat.DEFENSE.toString()) ) {
            return Stat.DEFENSE;
        } else if( identifier.equalsIgnoreCase(SP_ATTACK) ) {
            return Stat.SP_ATTACK;
        } else if( identifier.equalsIgnoreCase(SP_DEFENSE) ) {
            return Stat.SP_DEFENSE;
        } else if( identifier.equalsIgnoreCase(Stat.SPEED.toString()) ) {
            return Stat.SPEED;
        } else {
            return Stat.NONE;
        }
    }
}
