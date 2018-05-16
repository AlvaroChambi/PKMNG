package es.developer.achambi.pkmng.modules.calculator.presenter;

import android.os.Bundle;
import android.support.v4.util.Pair;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.Response;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;
import es.developer.achambi.pkmng.core.ui.Presenter;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.calculator.model.Damage;
import es.developer.achambi.pkmng.modules.calculator.utils.DamageCalculator;
import es.developer.achambi.pkmng.modules.create.presenter.ConfigurationAction;
import es.developer.achambi.pkmng.modules.overview.model.BasePokemon;
import es.developer.achambi.pkmng.modules.overview.model.Configuration;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;
import es.developer.achambi.pkmng.modules.overview.model.PokemonConfig;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;
import es.developer.achambi.pkmng.modules.search.move.model.Move;

public class DamageCalculatorPresenter extends Presenter {
    private static final String LEFT_POKEMON_SAVED_DATA = "LEFT_POKEMON_SAVED_DATA";
    private static final String RIGHT_POKEMON_SAVED_DATA = "RIGHT_POKEMON_SAVED_DATA";
    private static final String ATTACK_DIRECTION_SAVED_DATA = "ATTACK_DIRECTION_SAVED_DATA";
    private static final String EDITABLE_LEFT_CONFIGURATION_SAVED_DATA = "EDITABLE_LEFT_SAVED_DATA";
    private static final String EDITABLE_RIGHT_CONFIGURATION_SAVED_DATA = "EDITABLE_RIGHT_SAVED_DATA";
    private PokemonConfig leftPokemon;
    private PokemonConfig rightPokemon;
    private boolean leftRightDirection;
    private Configuration editableLeftConfiguration;
    private Configuration editableRightConfiguration;
    private IConfigurationDataAccess dataAccess;

    public DamageCalculatorPresenter(Screen screen, IConfigurationDataAccess dataAccess,
                                     MainExecutor executor) {
        super(screen, executor);
        leftPokemon = new PokemonConfig();
        rightPokemon = new PokemonConfig();
        leftRightDirection = true;
        editableLeftConfiguration = new Configuration();
        editableRightConfiguration = new Configuration();
        this.dataAccess = dataAccess;
    }

    public PokemonConfig getLeftPokemon() {
        return leftPokemon;
    }

    public void setLeftPokemon(PokemonConfig leftPokemon) {
        this.leftPokemon = leftPokemon;
        this.editableLeftConfiguration = new Configuration( leftPokemon.getConfiguration() );
    }

    public PokemonConfig getRightPokemon() {
        return rightPokemon;
    }

    public void setRightPokemon(PokemonConfig rightPokemon) {
        this.rightPokemon = rightPokemon;
        this.editableRightConfiguration = new Configuration( rightPokemon.getConfiguration() );
    }

    public boolean isLeftRightDirection() {
        return leftRightDirection;
    }

    public void setAttackDirection(boolean leftRightDirection) {
        this.leftRightDirection = leftRightDirection;
    }

    public Damage getDamageResult( Move move ) {
        PokemonConfig attacker, attacked;
        if( leftRightDirection ) {
            attacker = leftPokemon;
            attacked = rightPokemon;
        } else {
            attacker = rightPokemon;
            attacked = leftPokemon;
        }
        if( attacked.getId() == BasePokemon.EMPTY_ID || move.getId() == BasePokemon.EMPTY_ID ) {
            return new Damage();
        }
        return damageResult( attacker, attacked, move );
    }

    private Configuration getAttackerEditableConfiguration() {
        if( leftRightDirection ) {
            return editableLeftConfiguration;
        } else {
            return editableRightConfiguration;
        }
    }

    private Damage damageResult( PokemonConfig attacker, PokemonConfig attacked, Move move ) {
        Damage damage = new Damage( move );
        if( move.getCategory().equals( Move.Category.NON_DAMAGING ) ) {
            return damage;
        }
        Pair<Float, Float> moveDamage = DamageCalculator.moveDamageResult( attacker.getAttack(),
                attacker.getSpAttack(), attacked.getDefense(), attacked.getSPDefense(), move ) ;
        float modifier = DamageCalculator.modifierValue( attacker, attacked, move );
        int hitsToKO = DamageCalculator.hitsToKO( moveDamage, attacked.getHP() );

        damage.setMoveDamage( moveDamage );
        damage.setModifier( modifier );
        damage.setHitsToKO( hitsToKO );
        damage.setEffectivenessModifier(
                move.getType().modifier(attacked.getPokemon().getType()) );

        return damage;
    }

    /**
     * @return the current editable pokemon
     */
    public Pokemon getPokemon() {
        if( leftRightDirection ) {
            return leftPokemon.getPokemon();
        } else {
            return rightPokemon.getPokemon();
        }
    }

    public void saveLeftConfiguration( ResponseHandler<ConfigurationAction> responseHandler ) {
        if( !leftPokemon.getConfiguration().equals( editableLeftConfiguration ) ) {
            request(new Request<ConfigurationAction>() {
                @Override
                public Response<ConfigurationAction> perform() throws Exception{
                    leftPokemon.setConfiguration( editableLeftConfiguration );
                    dataAccess.updateConfiguration( leftPokemon );
                    return new Response<>(ConfigurationAction.UPDATE);
                }
            }, responseHandler);
        } else {
            responseHandler.onSuccess( new Response<>(ConfigurationAction.NONE) );
        }
    }

    public void saveRightConfiguration( ResponseHandler<ConfigurationAction> responseHandler ) {
        if( !rightPokemon.getConfiguration().equals( editableRightConfiguration ) ) {
            request(new Request<ConfigurationAction>() {
                @Override
                public Response<ConfigurationAction> perform() throws Exception {
                    rightPokemon.setConfiguration( editableRightConfiguration );
                    dataAccess.updateConfiguration( rightPokemon );
                    return new Response<>(ConfigurationAction.UPDATE);
                }
            }, responseHandler);
        } else {
            responseHandler.onSuccess( new Response<>(ConfigurationAction.NONE) );
        }
    }

    public Move getMove0() {
        return getAttackerEditableConfiguration().getMove0();
    }

    public void updateMove0( Move move ) {
        getAttackerEditableConfiguration().setMove0( move );
    }

    public Move getMove1() {
        return getAttackerEditableConfiguration().getMove1();
    }

    public void updateMove1( Move move ) {
        getAttackerEditableConfiguration().setMove1( move );
    }

    public Move getMove2() {
        return getAttackerEditableConfiguration().getMove2();
    }

    public void updateMove2( Move move ) {
        getAttackerEditableConfiguration().setMove2( move );
    }

    public Move getMove3() {
        return getAttackerEditableConfiguration().getMove3();
    }

    public void updateMove3( Move move ) {
        getAttackerEditableConfiguration().setMove3( move );
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable( LEFT_POKEMON_SAVED_DATA, leftPokemon);
        bundle.putParcelable( RIGHT_POKEMON_SAVED_DATA, rightPokemon);
        bundle.putBoolean( ATTACK_DIRECTION_SAVED_DATA, leftRightDirection );
        bundle.putParcelable( EDITABLE_LEFT_CONFIGURATION_SAVED_DATA, editableLeftConfiguration );
        bundle.putParcelable( EDITABLE_RIGHT_CONFIGURATION_SAVED_DATA, editableRightConfiguration );
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        leftPokemon = bundle.getParcelable( LEFT_POKEMON_SAVED_DATA );
        rightPokemon = bundle.getParcelable( RIGHT_POKEMON_SAVED_DATA );
        leftRightDirection = bundle.getBoolean( ATTACK_DIRECTION_SAVED_DATA );
        editableLeftConfiguration = bundle.getParcelable(EDITABLE_LEFT_CONFIGURATION_SAVED_DATA);
        editableRightConfiguration = bundle.getParcelable(EDITABLE_RIGHT_CONFIGURATION_SAVED_DATA);
    }
}
