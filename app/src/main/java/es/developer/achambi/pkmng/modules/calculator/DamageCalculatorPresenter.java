package es.developer.achambi.pkmng.modules.calculator;

import android.os.Bundle;

import es.developer.achambi.pkmng.core.ui.ViewPresenter;
import es.developer.achambi.pkmng.modules.overview.model.EmptyPokemonConfig;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;

public class DamageCalculatorPresenter implements ViewPresenter{
    private static final String LEFT_POKEMON_SAVED_DATA = "LEFT_POKEMON_SAVED_DATA";
    private static final String RIGHT_POKEMON_SAVED_DATA = "RIGHT_POKEMON_SAVED_DATA";
    private PokemonConfig attackerPokemon;
    private PokemonConfig attackedPokemon;

    public DamageCalculatorPresenter() {
        attackerPokemon = new EmptyPokemonConfig();
        attackedPokemon = new EmptyPokemonConfig();
    }

    public PokemonConfig getAttackerPokemon() {
        return attackerPokemon;
    }

    public void setAttackerPokemon(PokemonConfig attackerPokemon) {
        this.attackerPokemon = attackerPokemon;
    }

    public PokemonConfig getAttackedPokemon() {
        return attackedPokemon;
    }

    public void setAttackedPokemon(PokemonConfig attackedPokemon) {
        this.attackedPokemon = attackedPokemon;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable( LEFT_POKEMON_SAVED_DATA, attackerPokemon );
        bundle.putParcelable( RIGHT_POKEMON_SAVED_DATA, attackedPokemon );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        attackerPokemon = bundle.getParcelable( LEFT_POKEMON_SAVED_DATA );
        attackedPokemon = bundle.getParcelable( RIGHT_POKEMON_SAVED_DATA );
    }
}
