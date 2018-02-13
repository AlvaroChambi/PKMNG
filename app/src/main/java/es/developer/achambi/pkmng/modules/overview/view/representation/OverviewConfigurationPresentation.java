package es.developer.achambi.pkmng.modules.overview.view.representation;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.core.ui.presentation.PokemonTypePresentation;

public class OverviewConfigurationPresentation implements SearchListData{
    public final int id;
    public final String name;
    public final String image;
    public final String pokemonName;
    public final PokemonTypePresentation type;
    public final String totalStats;
    public final String ability;
    public final String item;
    public final String nature;

    public OverviewConfigurationPresentation(
            int id,
            String name,
            String image,
            String pokemonName,
            PokemonTypePresentation type,
            String totalStats,
            String ability,
            String item,
            String nature
            ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.pokemonName = pokemonName;
        this.type = type;
        this.totalStats = totalStats;
        this.ability = ability;
        this.item = item;
        this.nature = nature;
    }

    @Override
    public int getViewType() {
        return R.id.pokemon_configuration_view_id;
    }
}
