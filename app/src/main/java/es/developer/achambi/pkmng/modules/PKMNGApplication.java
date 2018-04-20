package es.developer.achambi.pkmng.modules;

import android.app.Application;

import es.developer.achambi.pkmng.core.AppWiring;

public class PKMNGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new AppWiring().appWiring(this);
    }
}
