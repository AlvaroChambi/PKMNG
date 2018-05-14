package es.developer.achambi.pkmng.modules.data.stat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.developer.achambi.pkmng.core.db.dao.StatsDAO;
import es.developer.achambi.pkmng.core.db.model.configuration_stats;
import es.developer.achambi.pkmng.core.db.model.stat_value;
import es.developer.achambi.pkmng.core.db.model.stats;
import es.developer.achambi.pkmng.core.exception.IllegalIDException;
import es.developer.achambi.pkmng.modules.overview.model.Stat;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;

public class StatDataAccess implements IStatDataAccess{
    private static final String SP_ATTACK = "special-attack";
    private static final String SP_DEFENSE = "special-defense";
    private static final int HP_STAT_ID = 1;
    private static final int ATTACK_STAT_ID = 2;
    private static final int DEFENSE_STAT_ID = 3;
    private static final int SPECIAL_ATTACK_STAT_ID = 4;
    private static final int SPECIAL_DEFENSE_STAT_ID = 5;
    private static final int SPEED_STAT_ID = 6;

    private StatsDAO statsDAO;

    public StatDataAccess(StatsDAO statsDAO) {
        this.statsDAO = statsDAO;
    }

    @Override
    public StatsSet accessPokemonStatsData( int pokemonId ) throws IllegalIDException {
        if( pokemonId < 1 ) {
            throw new IllegalIDException( pokemonId );
        }
        List<stat_value> rawStats = statsDAO.getStats(pokemonId);
        StatsSet statsSet = new StatsSet();
        populateStats( statsSet, rawStats );
        return statsSet;
    }

    @Override
    public StatsSet accessEvsSetData( int configurationId ) throws IllegalIDException {
        if( configurationId < 1 ) {
            throw new IllegalIDException( configurationId );
        }
        List<stat_value> rawStats = statsDAO.getEvsSet(configurationId);
        StatsSet statsSet = new StatsSet();
        populateStats( statsSet, rawStats );
        return statsSet;
    }

    @Override
    public Stat accessStatData( int statId ) throws IllegalIDException {
        if( statId < 1 ) {
            throw new IllegalIDException( statId );
        }
        stats value = statsDAO.getStat( statId );
        if( value != null ) {
            return parseStat( statsDAO.getStat( statId ).identifier );
        } else {
            return Stat.NONE;
        }
    }

    @Override
    public void insertStatsSet( int configurationId,
                                StatsSet statsSet ) throws IllegalArgumentException {
        if(configurationId < 0) {
            throw new IllegalArgumentException("invalid id : " + configurationId);
        }

        if(statsSet == null) {
            throw new IllegalArgumentException( "invalid StatSet: " + statsSet );
        }
        ArrayList<configuration_stats> statsToInsert = new ArrayList<>();
        Iterator<Stat> iterator = statsSet.getKeysIterator();
        while (iterator.hasNext()) {
            statsToInsert.add( cast(configurationId, iterator.next(), statsSet) );
        }
        statsDAO.insertStatsSet(statsToInsert);
    }

    @Override
    public void updateStatsSet( int configurationId, StatsSet statsSet ) {
        if(configurationId < 0) {
            throw new IllegalArgumentException("invalid id : " + configurationId);
        }

        if(statsSet == null) {
            throw new IllegalArgumentException( "invalid StatSet: " + statsSet );
        }
        ArrayList<configuration_stats> statsToUpdate = new ArrayList<>();
        Iterator<Stat> iterator = statsSet.getKeysIterator();
        while (iterator.hasNext()) {
            statsToUpdate.add( cast(configurationId, iterator.next(), statsSet) );
        }
        statsDAO.updateStatsSet(statsToUpdate);
    }

    private configuration_stats cast(int setId, Stat stat, StatsSet StatsSet) {
        configuration_stats rawStat = new configuration_stats();
        rawStat.configuration_id = setId;
        switch (stat) {
            case HP:
                rawStat.stat_id = HP_STAT_ID;
                rawStat.ev_value = StatsSet.getHP();
                break;
            case ATTACK:
                rawStat.stat_id = ATTACK_STAT_ID;
                rawStat.ev_value = StatsSet.getAttack();
                break;
            case DEFENSE:
                rawStat.stat_id = DEFENSE_STAT_ID;
                rawStat.ev_value = StatsSet.getDefense();
                break;
            case SP_ATTACK:
                rawStat.stat_id = SPECIAL_ATTACK_STAT_ID;
                rawStat.ev_value = StatsSet.getSpAttack();
                break;
            case SP_DEFENSE:
                rawStat.stat_id = SPECIAL_DEFENSE_STAT_ID;
                rawStat.ev_value = StatsSet.getSPDefense();
                break;
            case SPEED:
                rawStat.stat_id = SPEED_STAT_ID;
                rawStat.ev_value = StatsSet.getSpeed();
                break;
            case NONE:
                break;
        }
        return rawStat;
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
