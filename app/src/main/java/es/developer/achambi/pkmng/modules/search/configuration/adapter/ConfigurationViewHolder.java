package es.developer.achambi.pkmng.modules.search.configuration.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.pkmng.modules.ui.screen.AbilityView;
import es.developer.achambi.pkmng.modules.ui.screen.ItemView;
import es.developer.achambi.pkmng.modules.ui.screen.NatureView;
import es.developer.achambi.pkmng.modules.ui.screen.TypeView;
import es.developer.achambi.coreframework.utils.GlideRequests;
import es.developer.achambi.pkmng.modules.search.configuration.screen.presentation.ConfigurationPresentation;

public class ConfigurationViewHolder extends RecyclerView.ViewHolder {
    public TextView configName;

    public ItemView item;
    public AbilityView ability;
    public NatureView nature;
    public TextView baseStats;

    public ImageView pokemonIcon;
    public TextView pokemonName;
    public TypeView pokemonType;
    public TextView pokemonLevel;

    public ConfigurationViewHolder(View itemView) {
        super(itemView);
    }

    public void linkTo( View rootView ) {
        configName = rootView.findViewById(R.id.pokemon_config_name_text);

        pokemonIcon = rootView.findViewById(R.id.pokemon_image_view);
        pokemonName = rootView.findViewById(R.id.pokemon_name_text);
        pokemonType = rootView.findViewById(R.id.pokemon_type_text);
        baseStats = rootView.findViewById(R.id.pokemon_total_base_stats);

        item = rootView.findViewById(R.id.pokemon_item_text);
        ability = rootView.findViewById(R.id.pokemon_ability_text);
        nature = rootView.findViewById(R.id.pokemon_nature_text);
        pokemonLevel = rootView.findViewById(R.id.pokemon_level_text);
    }

    public void bindTo(ConfigurationPresentation configuration, GlideRequests requestManager) {
        requestManager.load(configuration.pokemon.image)
                .placeholder(configuration.pokemon.defaultImage)
                .into(pokemonIcon);
        configName.setText(configuration.name);
        pokemonName.setText(configuration.pokemon.name);
        pokemonType.setType(configuration.pokemon.type);
        baseStats.setText(configuration.pokemon.totalStats);
        item.setItem(configuration.item.name,
                configuration.item.description,
                configuration.item.empty);
        ability.setAbility( configuration.ability.name,
                configuration.ability.description,
                configuration.ability.empty );
        nature.setNature(configuration.nature.name,
                configuration.nature.details.increased,
                configuration.nature.details.decreased,
                configuration.nature.empty);
        pokemonLevel.setText(configuration.pokemon.level);
    }
}
