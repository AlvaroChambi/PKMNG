package es.developer.achambi.pkmng.modules.create.screen;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.overview.model.StatsSet;
import es.developer.achambi.pkmng.modules.search.nature.model.Nature;

public class ConfigurationStatSetView extends ConstraintLayout {
    private StatEVView hp;
    private StatEVView attack;
    private StatEVView defense;
    private StatEVView spAttack;
    private StatEVView spDefense;
    private StatEVView speed;

    public ConfigurationStatSetView(Context context) {
        super(context);
        init(context);
    }

    public ConfigurationStatSetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init( Context context ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.configuration_ev_set_custom_view_layout, this);
        hp = findViewById(R.id.configuration_hp_ev_stat_bar);
        attack = findViewById(R.id.configuration_attack_ev_stat_bar);
        defense = findViewById(R.id.configuration_defense_ev_stat_bar);
        spAttack = findViewById(R.id.configuration_sp_attack_ev_stat_bar);
        spDefense = findViewById(R.id.configuration_sp_defense_ev_stat_bar);
        speed = findViewById(R.id.configuration_speed_ev_stat_bar);
    }

    public void displayStatSet( StatsSet statsSet ) {
        hp.setValue( statsSet.getHP() );
        attack.setValue( statsSet.getAttack() );
        defense.setValue( statsSet.getDefense() );
        spAttack.setValue( statsSet.getSpAttack() );
        spDefense.setValue( statsSet.getSPDefense() );
        speed.setValue( statsSet.getSpeed() );
    }

    public void setProgressUpdateProvider( StatEVView.ProgressUpdateProvider progressProvider ) {
        hp.setProgressUpdateProvider(progressProvider);
        attack.setProgressUpdateProvider(progressProvider);
        defense.setProgressUpdateProvider(progressProvider);
        spAttack.setProgressUpdateProvider(progressProvider);
        spDefense.setProgressUpdateProvider(progressProvider);
        speed.setProgressUpdateProvider(progressProvider);
    }

    public void displayNatureModifier( Nature nature ) {
        hp.setNatureModifier( nature );
        attack.setNatureModifier( nature );
        defense.setNatureModifier( nature );
        spAttack.setNatureModifier( nature );
        spDefense.setNatureModifier( nature );
        speed.setNatureModifier( nature );
    }
}
