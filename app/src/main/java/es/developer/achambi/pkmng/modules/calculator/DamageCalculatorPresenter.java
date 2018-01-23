package es.developer.achambi.pkmng.modules.calculator;

import android.os.Bundle;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.model.EmptyPokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class DamageCalculatorPresenter implements ViewPresenter{
    private static final String LEFT_POKEMON_SAVED_DATA = "LEFT_POKEMON_SAVED_DATA";
    private static final String RIGHT_POKEMON_SAVED_DATA = "RIGHT_POKEMON_SAVED_DATA";
    private static final String ATTACK_DIRECTION_SAVED_DATA = "ATTACK_DIRECTION_SAVED_DATA";
    private PokemonConfig leftPokemon;
    private PokemonConfig rightPokemon;
    private boolean leftRightDirection;

    public class Damage {
        private Pair<Float, Float> moveDamage;
        private int hitsToKO;

        public Damage( Pair<Float, Float> moveDamage, int hitsToKO ) {
            this.moveDamage = moveDamage;
            this.hitsToKO = hitsToKO;
        }

        public Pair<Float, Float> getMoveDamage() {
            return moveDamage;
        }

        public void setMoveDamage(Pair<Float, Float> moveDamage) {
            this.moveDamage = moveDamage;
        }

        public int getHitsToKO() {
            return hitsToKO;
        }

        public void setHitsToKO(int hitsToKO) {
            this.hitsToKO = hitsToKO;
        }
    }

    public DamageCalculatorPresenter() {
        leftPokemon = new EmptyPokemonConfig();
        rightPokemon = new EmptyPokemonConfig();
        leftRightDirection = true;
    }

    public PokemonConfig getLeftPokemon() {
        return leftPokemon;
    }

    public void setLeftPokemon(PokemonConfig leftPokemon) {
        this.leftPokemon = leftPokemon;
    }

    public PokemonConfig getRightPokemon() {
        return rightPokemon;
    }

    public void setRightPokemon(PokemonConfig rightPokemon) {
        this.rightPokemon = rightPokemon;
    }

    public boolean isLeftRightDirection() {
        return leftRightDirection;
    }

    public void setAttackDirection(boolean leftRightDirection) {
        this.leftRightDirection = leftRightDirection;
    }

    public ArrayList<Damage> getDamageResults() {
        PokemonConfig attacker, attacked;
        if( leftRightDirection ) {
            attacker = leftPokemon;
            attacked = rightPokemon;
        } else {
            attacker = rightPokemon;
            attacked = leftPokemon;
        }
        ArrayList<Damage> damageResult = damagePerMove( attacker, attacked );
        return damageResult;
    }

    private ArrayList<Damage> damagePerMove( PokemonConfig attacker, PokemonConfig attacked ) {
        ArrayList<Damage> damageResult = new ArrayList<>();
        Pair<Float, Float> moveDamage0 = moveDamageResult( attacker, attacked,
                attacker.getConfiguration().getMove0() );
        Pair<Float, Float> moveDamage1 = moveDamageResult( attacker, attacked,
                attacker.getConfiguration().getMove0() );
        Pair<Float, Float> moveDamage2 = moveDamageResult( attacker, attacked,
                attacker.getConfiguration().getMove0() );
        Pair<Float, Float> moveDamage3 = moveDamageResult( attacker, attacked,
                attacker.getConfiguration().getMove0() );

        int hitsToKOMove0 = hitsToKO( moveDamage0, attacked.getHP() );
        int hitsToKOMove1 = hitsToKO( moveDamage1, attacked.getHP() );
        int hitsToKOMove2 = hitsToKO( moveDamage2, attacked.getHP() );
        int hitsToKOMove3 = hitsToKO( moveDamage3, attacked.getHP() );

        damageResult.add( new Damage( moveDamage0, hitsToKOMove0 ) );
        damageResult.add( new Damage( moveDamage1, hitsToKOMove1 ) );
        damageResult.add( new Damage( moveDamage2, hitsToKOMove2 ) );
        damageResult.add( new Damage( moveDamage3, hitsToKOMove3 ) );
        return damageResult;
    }

    private int hitsToKO( Pair<Float, Float> moveDamage,
                           int attackedHP ) {
        return (int)(attackedHP / moveDamage.first);
    }

    private Pair<Float, Float> moveDamageResult( PokemonConfig attacker,
                                                 PokemonConfig attacked, Move move ) {
        Random random = new Random();
        int attack = 0;
        int defense = 0;
        float modifiers = 0.85f + random.nextFloat() * ( 1.0f - 0.85f );
        if( move.getCategory().equals("Physical") ) {
            attack = attacker.getPokemon().getAttack();
            defense = attacked.getPokemon().getDefense();
        } else if( move.getCategory().equals("Special") ) {
            attack = attacker.getPokemon().getSpAttack();
            defense = attacker.getPokemon().getSPDefense();
        }
        float min = ( ( ( ( ( (2 * Pokemon.FIXED_LEVEL) / 5 ) + 2 ) * move.getPower() * ( attack / defense ) ) / 50 )
                + 2 ) * 0.85f;
        float max = ( ( ( ( ( (2 * Pokemon.FIXED_LEVEL) / 5 ) + 2 ) * move.getPower() * ( attack / defense ) ) / 50 )
                + 2 ) * 1.0f;
        return new Pair<>( min, max );
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable( LEFT_POKEMON_SAVED_DATA, leftPokemon);
        bundle.putParcelable( RIGHT_POKEMON_SAVED_DATA, rightPokemon);
        bundle.putBoolean( ATTACK_DIRECTION_SAVED_DATA, leftRightDirection );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        leftPokemon = bundle.getParcelable( LEFT_POKEMON_SAVED_DATA );
        rightPokemon = bundle.getParcelable( RIGHT_POKEMON_SAVED_DATA );
        leftRightDirection = bundle.getBoolean( ATTACK_DIRECTION_SAVED_DATA );
    }
}
