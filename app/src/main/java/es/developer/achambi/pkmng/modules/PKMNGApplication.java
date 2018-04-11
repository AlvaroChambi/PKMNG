package es.developer.achambi.pkmng.modules;

import android.app.Application;

import es.developer.achambi.pkmng.core.db.AppDatabase;
import es.developer.achambi.pkmng.core.db.utils.DatabaseUtils;

public class PKMNGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            DatabaseUtils.copyDataBase(this, AppDatabase.DB_NAME);
            AppDatabase.getDatabase(this);
        } catch ( Exception e ){
            e.printStackTrace();
        }
    }
}
