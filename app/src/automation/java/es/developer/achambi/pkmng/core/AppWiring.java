package es.developer.achambi.pkmng.core;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.overview.OverviewAssembler;
import es.developer.achambi.pkmng.modules.search.pokemon.SearchPokemonAssembler;

public class AppWiring {
    public static OverviewAssembler overviewAssembler;
    public static SearchPokemonAssembler searchPokemonAssembler;
    public static MainExecutor executor;
}
