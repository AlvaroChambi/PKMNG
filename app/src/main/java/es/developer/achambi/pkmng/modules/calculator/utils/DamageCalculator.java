package es.developer.achambi.pkmng.modules.calculator.utils;

import android.support.v4.util.Pair;

import org.jetbrains.annotations.NotNull;

import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Type;
import es.developer.achambi.pkmng.modules.search.ability.model.Ability;
import es.developer.achambi.pkmng.modules.search.item.model.Item;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class DamageCalculator {
    private static final float BASE_CRITICAL_HIT_MODIFIER = 2.0f;

    public static Pair<Float, Float> moveDamageResult( int attackerAttack, int attackerSpAttack,
                                                       int attackedDefense, int attackedSpDefense,
                                                       int level,
                                                       @NotNull Move move ) {
        int attack = 0;
        int defense = 0;
        if( move.getPower() <= 0 ) {
            throw new IllegalStateException();
        }

        if( move.getCategory().equals( Move.Category.EMPTY ) ||
                move.getCategory().equals( Move.Category.NON_DAMAGING ) ) {
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
        float min = ( ( ( ( ( (2 * level) / 5 ) + 2 ) * move.getPower() *
                ( attack / defense ) ) / 50 )
                + 2 ) * 0.85f;
        float max = ( ( ( ( ( (2 * level) / 5 ) + 2 ) * move.getPower() *
                ( attack / defense ) ) / 50 )
                + 2 ) * 1.0f;
        return new Pair<>( min, max );
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
        float abilityModifier = abilityModifier( attacker.getConfiguration().getAbility(),
                typeModifier );
        float moveModifier = moveModifier( move, attacked.getAbility() );
        float berryModifier = berryModifier( attacked.getItem(), move.getType(), typeModifier );
        float itemModifier = attackerItemModifier( attacker.getItem(), typeModifier );
        return weatherModifier * stab * typeModifier * burnModifier * moveModifier *
                itemModifier * berryModifier * abilityModifier;
    }

    public static float stabModifier( @NotNull Pair<Type, Type> attackerType,
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
        return ( attackerType.first == moveType || attackerType.second == moveType );
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

    public static float criticalHitModifier( Ability ability ) {
        if( ability.getName().equals( Ability.SNIPER ) ) {
            return BASE_CRITICAL_HIT_MODIFIER * 1.5f;
        }
        return BASE_CRITICAL_HIT_MODIFIER;
    }

    public static float abilityModifier( Ability ability, float typeModifier ) {
        if( ability.getName().equals( Ability.SOLID_ROCK )||
                   ability.getName().equals( Ability.FILTER )||
                   ability.getName().equals( Ability.PRISM_ARMOR ) ){
            if( typeModifier > 1 ) {
                return 0.75f;
            }
        } else if( ability.getName().equals( Ability.TINTED_LENS ) ) {
            if( typeModifier < 1 ) {
                return 2.0f;
            }
        }

        return 1.0f;
    }

    public static float moveModifier( Move move, Ability targetAbility ) {
        if( targetAbility.getName().equals( Ability.FLUFFY ) ) {
            if( move.isContact() && !move.getType().equals( Type.FIRE ) ) {
                return 0.5f;
            } else if( !move.isContact() && move.getType().equals( Type.FIRE ) ) {
                return 2.0f;
            }
        }
        return 1.0f;
    }

    public static float berryModifier( Item targetItem, Type moveType, float typeModifier ) {
        if( typeModifier > 1 ) {
            if( targetItem.getName().equals( Item.OCCA_BERRY )
                    && moveType.equals( Type.FIRE ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.PASSHO_BERRY )
                    && moveType.equals( Type.WATER ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.WACAN_BERRY )
                    && moveType.equals( Type.ELECTRIC ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.RINDO_BERRY )
                    && moveType.equals( Type.GRASS ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.YACHE_BERRY )
                    && moveType.equals( Type.ICE ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.CHOPLE_BERRY )
                    && moveType.equals( Type.FIGHTING ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.KEBIA_BERRY )
                    && moveType.equals( Type.POISON ) ) {
            } else if( targetItem.getName().equals( Item.SHUCA_BERRY )
                    && moveType.equals( Type.GROUND ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.COBA_BERRY )
                    && moveType.equals( Type.FLYING ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.PAYAPA_BERRY )
                    && moveType.equals( Type.PSYCHIC ) ){
                return 0.5f;
            } else if( targetItem.getName().equals( Item.TANGA_BERRY )
                    && moveType.equals( Type.BUG ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.CHARTI_BERRY )
                    && moveType.equals( Type.ROCK ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.KASIB_BERRY )
                    && moveType.equals( Type.GHOST ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.HABAN_BERRY )
                    && moveType.equals( Type.DRAGON ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.COLBUR_BERRY )
                    && moveType.equals( Type.DARK ) ) {
                return 0.5f;
            } else if( targetItem.getName().equals( Item.BABIRI_BERRY )
                    && moveType.equals( Type.STEEL ) ) {
                return 0.5f;
            }
        }

        if( targetItem.getName().equals( Item.CHILAN_BERRY )
                && moveType.equals( Type.NORMAL ) ) {
            return 0.5f;
        }
        return 1.0f;
    }

    public static float attackerItemModifier( Item item, float typeModifier ) {
        if( item.getName().equals( Item.EXPERT_BELT ) && typeModifier > 1 ) {
            return 1.2f;
        } else if( item.getName().equals( Item.LIFE_ORB ) ) {
            return 1.3f;
        }
        return 1.0f;
    }
}
