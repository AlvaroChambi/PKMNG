package es.developer.achambi.pkmng.modules.overview.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public enum Type {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY,
    EMPTY;

    public enum Value{
        NORMAL( normalDeals(), normalReceives() ),
        FIRE( fireDeals(), fireReceives() ),
        WATER( waterDeals(), waterReceives() ),
        ELECTRIC( electricDeals(), electricReceives() ),
        GRASS( grassDeals(), grassReceives() ),
        ICE( iceDeals(), iceReceives() ),
        FIGHTING( fightingDeals(), fightingReceives() ),
        POISON( poisonDeals(), poisonReceives() ),
        GROUND( groundDeals(), groundReceives() ),
        FLYING( flyingDeals(), flyingReceives() ),
        PSYCHIC( psychicDeals(), psychicReceives() ),
        BUG( bugDeals(), bugReceives() ),
        ROCK( rockDeals(), rockReceives() ),
        GHOST( ghostDeals(), ghostReceives() ),
        DRAGON( dragonDeals(), dragonReceives() ),
        DARK( darkDeals(), darkReceives() ),
        STEEL( steelDeals(), steelReceives() ),
        FAIRY( fairyDeals(), fairyReceives() ),
        EMPTY( new HashMap<Type, Float>(), new HashMap<Type, Float>() );

        private HashMap<Type, Float> deals;
        private HashMap<Type, Float> receives;

        Value( HashMap<Type, Float> deals,
              HashMap<Type, Float> receives ) {
            this.deals = deals;
            this.receives = receives;
        }

        public float modifier( Type type ) {
            if( deals.containsKey( type ) ) {
                return deals.get( type );
            } else {
                return 1.0f;
            }
        }

        public float modifier( Pair<Type, Type> type ) {
            if( type.second == Type.EMPTY ) {
                return modifier( type.first );
            } else {
                float fistModifier = modifier( type.first );
                float secondModifier = modifier( type.second );

                return fistModifier * secondModifier;
            }
        }

        public static HashMap<Type, Float> resistantTo( Pair<Type, Type> type ) {
            if( type.second == Type.EMPTY ) {
                HashMap<Type, Float> singleTypeResult = new HashMap<>();
                for( Type currentType : cast( type.first ).receives.keySet() ) {
                    if( cast( type.first ).receives.get(currentType) <= 0.5f ) {
                        singleTypeResult.put( currentType, cast( type.first )
                                .receives.get(currentType) );
                    }
                }
                return singleTypeResult;
            } else {
                HashMap<Type, Float> dualTypeResult = new HashMap<>();
                for( Type currentType : Type.values() ) {
                    if( currentType != Type.EMPTY ) {
                        float modifier = currentType.modifier( type );
                        if( modifier <= 0.5 ) {
                            dualTypeResult.put( currentType, modifier );
                        }
                    }
                }
                return dualTypeResult;
            }
        }

        public static HashMap<Type, Float> weakAgainst( Pair<Type, Type> type ) {

            if( type.second == Type.EMPTY ) {
                HashMap<Type, Float> singleTypeResult = new HashMap<>();
                for( Type currentType : cast( type.first ).receives.keySet() ) {
                    if( cast( type.first ).receives.get(currentType) >= 2 ) {
                        singleTypeResult.put( currentType, cast( type.first )
                                .receives.get(currentType) );
                    }
                }
                return singleTypeResult;
            } else {
                HashMap<Type, Float> dualTypeResult = new HashMap<>();
                for( Type currentType : Type.values() ) {
                    if( currentType != Type.EMPTY ) {
                        float modifier = currentType.modifier( type );
                        if( modifier >= 2 ) {
                            dualTypeResult.put( currentType, modifier );
                        }
                    }
                }
                return dualTypeResult;
            }
        }

        public HashMap<Type, Float> effectiveAgainst( ) {
            HashMap<Type, Float> result = new HashMap<>();
            for( Type currentType : deals.keySet() ) {
                float modifier = deals.get( currentType );
                if( modifier >= 2.0 ) {
                    result.put( currentType, modifier );
                }
            }
            return result;
        }
    }

    public float modifier( Pair<Type, Type> type ) {
        return cast(this).modifier( type );
    }

    public static HashMap<Type, Float> weakAgainst( Pair<Type, Type> type ) {
        return Value.weakAgainst( type );
    }

    public static HashMap<Type, Float> resistantAgainst( Pair<Type, Type> type ) {
        return Value.resistantTo( type );
    }

    public HashMap<Type, Float> effectiveAgainst() {
        return cast(this).effectiveAgainst();
    }

    private static HashMap<Type, Float> normalDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( ROCK, 0.5f );
        values.put( GHOST, 0.0f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> normalReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIGHTING, 2.0f );
        values.put( GHOST, 0.0f );
        return values;
    }

    private static HashMap<Type, Float> fireDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 0.5f );
        values.put( GRASS, 2.0f );
        values.put( ICE, 2.0f );
        values.put( BUG, 2.0f );
        values.put( ROCK, 0.5f );
        values.put( DRAGON, 0.5f );
        values.put( STEEL, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> fireReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 2.0f );
        values.put( GRASS, 0.5f );
        values.put( ICE, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( BUG, 0.5f );
        values.put( ROCK, 0.5f );
        values.put( STEEL, 0.5f );
        values.put( FAIRY, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> waterDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 2.0f );
        values.put( WATER, 0.5f );
        values.put( GRASS, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( ROCK, 2.0f );
        values.put( DRAGON, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> waterReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 0.5f );
        values.put( ELECTRIC, 2.0f );
        values.put( GRASS, 2.0f );
        values.put( ICE, 0.5f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> electricDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( WATER, 2.0f );
        values.put( ELECTRIC, 0.5f );
        values.put( GRASS, 0.5f );
        values.put( GROUND, 0.0f );
        values.put( FLYING, 2.0f );
        values.put( DRAGON, 0.5f );
        return values;
    }

    private static  HashMap<Type, Float> electricReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( ELECTRIC, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( FLYING, 0.5f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> grassDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 2.0f );
        values.put( GRASS, 0.5f );
        values.put( POISON, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( FLYING, 0.5f );
        values.put( BUG, 0.5f );
        values.put( ROCK, 2.0f );
        values.put( DRAGON, 0.5f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> grassReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 2.0f );
        values.put( WATER, 0.5f );
        values.put( ELECTRIC, 0.5f );
        values.put( GRASS, 0.5f );
        values.put( ICE, 2.0f );
        values.put( POISON, 2.0f );
        values.put( GROUND, 0.5f );
        values.put( FLYING, 2.0f );
        values.put( BUG, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> iceDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 0.5f );
        values.put( GRASS, 2.0f );
        values.put( ICE, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( FLYING, 2.0f );
        values.put( DRAGON, 2.0f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> iceReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 2.0f );
        values.put( ICE, 0.5f );
        values.put( FIGHTING, 2.0f );
        values.put( ROCK, 2.0f );
        values.put( STEEL, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> fightingDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( NORMAL, 2.0f );
        values.put( ICE, 2.0f );
        values.put( POISON, 0.5f );
        values.put( FLYING, 0.5f );
        values.put( PSYCHIC, 0.5f );
        values.put( BUG, 0.5f );
        values.put( ROCK, 2.0f );
        values.put( GHOST, 0.0f );
        values.put( DARK, 2.0f );
        values.put( STEEL, 2.0f );
        values.put( FAIRY, 0.5f );

        return values;
    }

    private static HashMap<Type, Float> fightingReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FLYING, 2.0f );
        values.put( PSYCHIC, 2.0f );
        values.put( BUG, 0.5f );
        values.put( ROCK, 0.5f );
        values.put( DARK, 0.5f );
        values.put( FAIRY, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> poisonDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( GRASS, 2.0f );
        values.put( POISON, 0.5f );
        values.put( GROUND, 0.5f );
        values.put( ROCK, 0.5f );
        values.put( GHOST, 0.5f );
        values.put( STEEL, 0.0f );
        values.put( FAIRY, 2.0f );

        return values;
    }

    private static HashMap<Type, Float> poisonReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( GRASS, 0.5f );
        values.put( FIGHTING, 0.5f );
        values.put( POISON, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( PSYCHIC, 2.0f );
        values.put( BUG, 0.5f );
        values.put( FAIRY, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> groundDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 2.0f );
        values.put( ELECTRIC, 2.0f );
        values.put( GRASS, 0.5f );
        values.put( POISON, 2.0f );
        values.put( FLYING, 0.0f );
        values.put( BUG, 0.5f );
        values.put( ROCK, 2.0f );
        values.put( STEEL, 0.5f );

        return values;
    }

    private static HashMap<Type, Float> groundReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( WATER, 2.0f );
        values.put( ELECTRIC, 0.0f );
        values.put( GRASS, 2.0f );
        values.put( ICE, 2.0f );
        values.put( POISON, 0.5f );
        values.put( ROCK, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> flyingDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( ELECTRIC, 0.5f );
        values.put( GRASS, 2.0f );
        values.put( FIGHTING, 2.0f );
        values.put( BUG, 2.0f );
        values.put( ROCK, 0.5f );
        values.put( STEEL, 0.5f );

        return values;
    }

    private static HashMap<Type, Float> flyingReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( ELECTRIC, 2.0f );
        values.put( GRASS, 0.5f );
        values.put( ICE, 2.0f );
        values.put( FIGHTING, 0.5f );
        values.put( ROCK, 2.0f );
        values.put( GROUND, 0.0f );
        values.put( BUG, 0.5f );
        values.put( ROCK, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> psychicDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIGHTING, 2.0f );
        values.put( POISON, 2.0f );
        values.put( PSYCHIC, 0.5f );
        values.put( DARK, 0.0f );
        values.put( STEEL, 0.5f );

        return values;
    }

    private static HashMap<Type, Float> psychicReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIGHTING, 0.5f );
        values.put( PSYCHIC, 0.5f );
        values.put( BUG, 2.0f );
        values.put( GHOST, 2.0f );
        values.put( DARK, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> bugDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( GRASS, 2.0f );
        values.put( FIGHTING, 0.5f );
        values.put( POISON, 0.5f );
        values.put( FLYING, 0.5f );
        values.put( PSYCHIC, 2.0f );
        values.put( GHOST, 0.5f );
        values.put( DARK, 2.0f );
        values.put( STEEL, 0.5f );
        values.put( FAIRY, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> bugReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 2.0f );
        values.put( GRASS, 0.5f );
        values.put( FIGHTING, 0.5f );
        values.put( GROUND, 0.5f );
        values.put( FLYING, 2.0f );
        values.put( ROCK, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> rockDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 2.0f );
        values.put( ICE, 2.0f );
        values.put( FIGHTING, 0.5f );
        values.put( GROUND, 0.5f );
        values.put( FLYING, 2.0f );
        values.put( BUG, 2.0f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> rockReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( NORMAL, 0.5f );
        values.put( FIRE, 0.5f );
        values.put( WATER, 2.0f );
        values.put( GRASS, 2.0f );
        values.put( FIGHTING, 2.0f );
        values.put( POISON, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( FLYING, 0.5f );
        values.put( STEEL, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> ghostDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( NORMAL, 0.0f );
        values.put( PSYCHIC, 2.0f );
        values.put( GHOST, 2.0f );
        values.put( DARK, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> ghostReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( NORMAL, 0.0f );
        values.put( FIGHTING, 0.0f );
        values.put( POISON, 0.5f );
        values.put( BUG, 0.5f );
        values.put( GHOST, 2.0f );
        values.put( DARK, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> dragonDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( DRAGON, 2.0f );
        values.put( STEEL, 0.5f );
        values.put( FAIRY, 0.0f );
        return values;
    }

    private static HashMap<Type, Float> dragonReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 0.5f );
        values.put( ELECTRIC, 0.5f );
        values.put( GRASS, 0.5f );
        values.put( ICE, 2.0f );
        values.put( DRAGON, 2.0f );
        values.put( FAIRY, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> darkDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIGHTING, 0.5f );
        values.put( PSYCHIC, 2.0f );
        values.put( GHOST, 2.0f );
        values.put( DARK, 0.5f );
        values.put( FAIRY, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> darkReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIGHTING, 2.0f );
        values.put( PSYCHIC, 0.0f );
        values.put( BUG, 2.0f );
        values.put( GHOST, 0.5f );
        values.put( DARK, 0.5f );
        values.put( FAIRY, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> steelDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 0.5f );
        values.put( ELECTRIC, 0.5f );
        values.put( ICE, 2.0f );
        values.put( ROCK, 2.0f );
        values.put( STEEL, 0.5f );
        values.put( FAIRY, 2.0f );
        return values;
    }

    private static HashMap<Type, Float> steelReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( NORMAL, 0.5f );
        values.put( FIRE, 2.0f );
        values.put( GRASS, 0.5f );
        values.put( ICE, 0.5f );
        values.put( FIGHTING, 2.0f );
        values.put( POISON, 0.0f );
        values.put( GROUND, 2.0f );
        values.put( FLYING, 0.5f );
        values.put( PSYCHIC, 0.5f );
        values.put( BUG, 0.5f );
        values.put( ROCK, 0.5f );
        values.put( DRAGON, 0.5f );
        values.put( STEEL, 0.5f );
        values.put( FAIRY, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> fairyDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( FIGHTING, 2.0f );
        values.put( POISON, 0.5f );
        values.put( GHOST, 2.0f );
        values.put( DARK, 2.0f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static HashMap<Type, Float> fairyReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIGHTING, 0.5f );
        values.put( POISON, 2.0f );
        values.put( BUG, 0.5f );
        values.put( DRAGON, 0.0f );
        values.put( DARK, 0.5f );
        values.put( STEEL, 2.0f );
        return values;
    }

    private static Type.Value cast( Type type ) {
        switch (type) {
            case NORMAL:
                return Value.NORMAL;
            case FIRE:
                return Value.FIRE;
            case WATER:
                return Value.WATER;
            case ELECTRIC:
                return Value.ELECTRIC;
            case GRASS:
                return Value.GRASS;
            case ICE:
                return Value.ICE;
            case FIGHTING:
                return Value.FIGHTING;
            case POISON:
                return Value.POISON;
            case GROUND:
                return Value.GROUND;
            case FLYING:
                return Value.FLYING;
            case PSYCHIC:
                return Value.PSYCHIC;
            case BUG:
                return Value.BUG;
            case ROCK:
                return Value.ROCK;
            case GHOST:
                return Value.GHOST;
            case DRAGON:
                return Value.DRAGON;
            case DARK:
                return Value.DARK;
            case STEEL:
                return Value.STEEL;
            case FAIRY:
                return Value.FAIRY;
            default:
                return Value.EMPTY;
        }
    }
}
