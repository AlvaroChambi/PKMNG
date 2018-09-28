package es.developer.achambi.pkmng.modules.search.configuration.adapter;

import android.view.View;

import es.developer.achambi.pkmng.R;
import es.developer.achambi.coreframework.ui.SearchAdapterDecorator;
import es.developer.achambi.coreframework.utils.GlideRequests;
import es.developer.achambi.pkmng.modules.search.configuration.screen.presentation.ConfigurationPresentation;

public class SearchConfigurationAdapter extends SearchAdapterDecorator<ConfigurationPresentation,
        ConfigurationViewHolder> {

    private GlideRequests requestManager;

    public SearchConfigurationAdapter(SearchAdapterDecorator adapter, GlideRequests requestManager) {
        super(adapter);
        this.requestManager = requestManager;
    }

    public SearchConfigurationAdapter(GlideRequests requestManager) {
        super();
        this.requestManager = requestManager;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.pokemon_config_list_cardview_item_layout;
    }

    @Override
    public int getAdapterViewType() {
        return R.id.pokemon_configuration_view_id;
    }

    @Override
    public ConfigurationViewHolder createViewHolder(View rootView ) {
        final ConfigurationViewHolder viewHolder = new ConfigurationViewHolder(rootView);
        viewHolder.linkTo( rootView );
        return viewHolder;
    }

    @Override
    public void bindViewHolder( ConfigurationViewHolder holder,
                                ConfigurationPresentation configuration ) {
        holder.bindTo( configuration, requestManager );
    }
}
