package es.developer.achambi.pkmng.modules.overview.model;

import java.util.HashMap;

public enum Type {
    NORMAL( normalDeals(), normalReceives() ),
    FIRE( fireDeals(), fireReceives() ),
    WATER( waterDeals(), waterReceives() ),
    ELECTRIC( electricDeals(), electricReceives() ),
    GRASS( grassDeals(), grassReceives() ),
    ICE( iceDeals(), iceReceives() ),
    FIGHTING( fightingDeals(), fireReceives() ),
    POISON(  ),
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

    private static final HashMap<Type, Float> normalDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( ROCK, 0.5f );
        values.put( GHOST, 0.0f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static final HashMap<Type, Float> normalReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIGHTING, 2.0f );
        values.put( GHOST, 0.0f );
        return values;
    }

    private static final HashMap<Type, Float> fireDeals() {
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

    private static final HashMap<Type, Float> fireReceives() {
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

    private static final HashMap<Type, Float> waterDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 2.0f );
        values.put( WATER, 0.5f );
        values.put( GRASS, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( ROCK, 2.0f );
        values.put( DRAGON, 0.5f );
        return values;
    }

    private static final HashMap<Type, Float> waterReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 0.5f );
        values.put( ELECTRIC, 2.0f );
        values.put( GRASS, 2.0f );
        values.put( ICE, 0.5f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static final HashMap<Type, Float> electricDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( WATER, 2.0f );
        values.put( ELECTRIC, 0.5f );
        values.put( GRASS, 0.5f );
        values.put( GROUND, 0.0f );
        values.put( FLYING, 2.0f );
        values.put( DRAGON, 0.5f );
        return values;
    }

    private static final HashMap<Type, Float> electricReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( ELECTRIC, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( FLYING, 0.5f );
        values.put( STEEL, 0.5f );
        return values;
    }

    private static final HashMap<Type, Float> grassDeals() {
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

    private static final HashMap<Type, Float> grassReceives() {
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

    private static final HashMap<Type, Float> iceDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 0.5f );
        values.put( WATER, 2.0f );
        values.put( GRASS, 0.5f );
        values.put( ICE, 0.5f );
        values.put( GROUND, 2.0f );
        values.put( FLYING, 0.5f );;
        values.put( DRAGON, 0.5f );
        return values;
    }

    private static final HashMap<Type, Float> iceReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FIRE, 2.0f );
        values.put( ICE, 0.5f );
        values.put( FIGHTING, 2.0f );
        values.put( ROCK, 2.0f );
        values.put( STEEL, 2.0f );
        return values;
    }

    private static final HashMap<Type, Float> fightingDeals() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( NORMAL, 2.0f );
        values.put( ICE, 2.0f );
        values.put( POISON, 0.5f );
        values.put( FLYING, 0.5f );
        values.put( PSYCHIC, 0.5f );
        values.put( BUG, 0.5f );;
        values.put( ROCK, 2.0f );
        values.put( GHOST, 0.0f );
        values.put( DARK, 2.0f );
        values.put( STEEL, 2.0f );
        values.put( FAIRY, 0.5f );

        return values;
    }

    private static final HashMap<Type, Float> fightingReceives() {
        HashMap<Type,Float> values = new HashMap<>();
        values.put( FLYING, 2.0f );
        values.put( PSYCHIC, 2.0f );
        values.put( BUG, 0.5f );
        values.put( ROCK, 0.5f );
        values.put( DARK, 0.5f );
        values.put( FAIRY, 2.0f );
        return values;
    }
}
