package es.developer.achambi.pkmng.modules.calculator.utils;

import android.util.Pair;

import org.jetbrains.annotations.NotNull;

import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class DamageCalculator {
    public static int hitsToKO( Pair<Float, Float> moveDamage,
                          int attackedHP ) {
        return (int)(attackedHP / moveDamage.first);
    }

    public static Pair<Float, Float> moveDamageResult( int attackerAttack, int attackerSpAttack,
                                                       int attackedDefense, int attackedSpDefense,
                                                       @NotNull Move move ) {
        int attack = 0;
        int defense = 0;
        if( move.getPower() <= 0 || move.getCategory().equals(Move.Category.EMPTY ) ) {
            throw new IllegalStateException();
        }
        if( attackerAttack <= 0 && attackedDefense <= 0
               || attackerSpAttack <= 0 || attackedSpDefense <= 0 ) {
            throw  new IllegalStateException();
        }
        if( move.getCategory().equals(Move.Category.PHYSICAL) ) {
            attack = attackerAttack;
            defense = attackedDefense;
        } else if( move.getCategory().equals(Move.Category.SPECIAL) ) {
            attack = attackerSpAttack;
            defense = attackedSpDefense;
        }
        float min = ( ( ( ( ( (2 * Pokemon.FIXED_LEVEL) / 5 ) + 2 ) * move.getPower() *
                ( attack / defense ) ) / 50 )
                + 2 ) * 0.85f;
        float max = ( ( ( ( ( (2 * Pokemon.FIXED_LEVEL) / 5 ) + 2 ) * move.getPower() *
                ( attack / defense ) ) / 50 )
                + 2 ) * 1.0f;
        return new Pair<>( min, max );
    }

    public static float modifierValue( PokemonConfig attacker,
                                        PokemonConfig attacked, Move move ) {
        float weatherModifier = 1.0f;
        float stab = 1.0f;
        if( isSTAB( attacker.getPokemon().getType(), move.getType() ) ) {
            if( attacker.getAbility().equals("Adaptability") ) {
                stab = 2.0f;
            } else  {
                stab = 1.5f;
            }
        }
        float typeModifier = move.getType().modifier( attacked.getPokemon().getType() );
        float burnModifier = burnModifier( attacker.getConfiguration(),
                move, false );
        float moveModifier = moveModifier();
        float itemModifier = itemModifier();
        return weatherModifier * stab * typeModifier * burnModifier * moveModifier *
                itemModifier;
    }

    private static boolean isSTAB( Pair<Type, Type> attackerType,
                            Type moveType ) {
        if( attackerType.first == moveType || attackerType.second == moveType ) {
            return true;
        }
        return false;
    }

    private static float burnModifier( Configuration configuration, Move move,
                                        boolean isBurned ) {
        if( isBurned && !configuration.getAbility().equals("Guts") &&
                move.getCategory().equals("Physical") ) {
            return 0.5f;
        } else {
            return 1.0f;
        }
    }

    private static float moveModifier() {
        //move modifier can be affected by the equipped item
        return 1.0f;
    }

    private static float itemModifier() {
        //can be affected by moves, and several modifiers...
        return 1.0f;
    }
}
