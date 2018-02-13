package es.developer.achambi.pkmng.modules.overview.view.representation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.TypesPresentation;

public class OverviewPokemonRepresentation implements SearchListData {
    public final int id;
    public final String name;
    public final String image;
    public final TypesPresentation type;
    public final String totalStats;
    public final String hp;
    public final String defense;
    public final String attack;
    public final String spAttack;
    public final String spDefense;
    public final String speed;

    public OverviewPokemonRepresentation(
            int id,
           String name,
           String image,
           TypesPresentation type,
           String totalStats,
           String hp,
           String defense,
           String attack,
           String spAttack,
           String spDefense,
           String speed) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.totalStats = totalStats;
        this.hp = hp;
        this.defense = defense;
        this.attack = attack;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_view_id;
    }
}
