package es.developer.achambi.pkmng.modules;

import android.app.Application;

import es.developer.achambi.pkmng.core.data.OverviewAssembler;
import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.utils.DatabaseUtils;
import es.developer.achambi.pkmng.modules.overview.presenter.OverviewPresenterFactory;
import es.developer.achambi.pkmng.modules.search.pokemon.data.PokemonDataAccessFactory;

public class PKMNGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OverviewAssembler overviewAssembler = new OverviewAssembler();
        overviewAssembler.setDataAccessFactory( new PokemonDataAccessFactory() );
        overviewAssembler.setPresenterFactory( new OverviewPresenterFactory() );

        try {
            DatabaseUtils.copyDataBase(this, AppDatabase.DB_NAME);
            AppDatabase.buildDatabase(this);
        } catch ( Exception e ){
            e.printStackTrace();
        }
    }
}
