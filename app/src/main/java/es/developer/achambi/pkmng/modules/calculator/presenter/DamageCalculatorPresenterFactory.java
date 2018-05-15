package es.developer.achambi.pkmng.modules.calculator.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.ui.Screen;
import es.developer.achambi.pkmng.modules.search.configuration.data.IConfigurationDataAccess;

public class DamageCalculatorPresenterFactory implements IDamageCalculatorPresenterFactory {
    private MainExecutor executor;
    private IConfigurationDataAccess dataAccess;

    public DamageCalculatorPresenterFactory(IConfigurationDataAccess dataAccess,
                                            MainExecutor executor) {
        this.executor = executor;
        this.dataAccess = dataAccess;
    }

    @Override
    public DamageCalculatorPresenter buildPresenter(Screen screen) {
        return new DamageCalculatorPresenter( screen, dataAccess, executor );
    }
}
