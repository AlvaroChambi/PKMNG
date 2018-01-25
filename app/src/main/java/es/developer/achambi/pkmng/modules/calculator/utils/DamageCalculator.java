package es.developer.achambi.pkmng.modules.calculator.utils;

import android.support.v4.util.Pair;

import org.jetbrains.annotations.NotNull;

import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class DamageCalculator {

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
        Pair<Float, Float> result = new Pair<>( min, max );
        return result;
    }

    public static int hitsToKO( @NotNull Pair<Float, Float> moveDamage,
                                int attackedHP ) {
        if( moveDamage.first < 0 ) {
            throw new IllegalStateException();
        }

        if( attackedHP <= 0 ) {
            throw new IllegalStateException();
        }

        if( moveDamage.first == 0 ) {
            return 0;
        }
        return (int)Math.ceil(attackedHP / moveDamage.first);
    }

    public static float modifierValue( PokemonConfig attacker,
                                        PokemonConfig attacked, Move move ) {
        float weatherModifier = 1.0f;
        float stab = stabModifier( attacker.getPokemon().getType(), move.getType(),
                                   attacker.getAbility() );
        float typeModifier = move.getType().modifier( attacked.getPokemon().getType() );
        float burnModifier = burnModifier( move.getCategory(),
                attacker.getConfiguration().getAbility(),
                false );
        float moveModifier = moveModifier();
        float itemModifier = itemModifier();
        return weatherModifier * stab * typeModifier * burnModifier * moveModifier *
                itemModifier;
    }

    public static final float stabModifier( @NotNull Pair<Type, Type> attackerType,
                                            @NotNull Type moveType,
                                            @NotNull Ability ability ) {
        if( attackerType.first.equals( Type.EMPTY ) ) {
            throw new IllegalStateException();
        }
        if( moveType.equals( Type.EMPTY ) ){
            throw new IllegalStateException();
        }
        if( isSTAB( attackerType, moveType ) ) {
            if( ability.getName().equals( Ability.ADAPTABILITY ) ) {
                return 2.0f;
            } else {
                return 1.5f;
            }
        }
        return 1.0f;
    }

    private static boolean isSTAB( Pair<Type, Type> attackerType,
                            Type moveType ) {
        if( attackerType.first == moveType || attackerType.second == moveType ) {
            return true;
        }
        return false;
    }

    public static float burnModifier( @NotNull Move.Category category,
                                      @NotNull Ability ability,
                                      boolean isBurned ) {
        if( category.equals( Move.Category.EMPTY ) ) {
            throw new IllegalStateException();
        }
        if( isBurned && !ability.getName().equals(Ability.GUTS) &&
                category.equals(Move.Category.PHYSICAL) ) {
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
