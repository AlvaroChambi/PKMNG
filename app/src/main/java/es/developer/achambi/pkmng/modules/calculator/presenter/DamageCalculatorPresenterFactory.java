package es.developer.achambi.pkmng.modules.calculator.presenter;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.ui.Screen;

public class DamageCalculatorPresenterFactory implements IDamageCalculatorPresenterFactory {
    private MainExecutor executor;
    private IConfigurationDataAccessFactory dataAccessFactory;

    public DamageCalculatorPresenterFactory(IConfigurationDataAccessFactory dataAccessFactory,
                                            MainExecutor executor) {
        this.executor = executor;
        this.dataAccessFactory = dataAccessFactory;
    }

    @Override
    public DamageCalculatorPresenter buildPresenter(Screen screen) {
        return new DamageCalculatorPresenter( screen,
                dataAccessFactory.buildDataAccess(), executor );
    }
}
