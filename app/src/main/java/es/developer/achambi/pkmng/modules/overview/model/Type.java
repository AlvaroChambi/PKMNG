package es.developer.achambi.pkmng.modules.overview.model;

import android.util.Pair;

import java.util.HashMap;

public enum Type {
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
    EMPTY( null, null );

    private HashMap<Type, Float> deals;
    private HashMap<Type, Float> receives;

    Type( HashMap<Type, Float> deals,
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
        if( type.second == EMPTY ) {
            return modifier( type.first );
        } else {
            float fistModifier = modifier( type.first );
            float secondModifier = modifier( type.second );

            return fistModifier * secondModifier;
        }
    }

    public HashMap<Type, Float> getReceives() {
        return receives;
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
        values.put( WATER, 2.0f );
        values.put( GRASS, 0.5f );
        values.put( ICE, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( FLYING, 0.5f );
        values.put( DRAGON, 0.5f );
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
}
