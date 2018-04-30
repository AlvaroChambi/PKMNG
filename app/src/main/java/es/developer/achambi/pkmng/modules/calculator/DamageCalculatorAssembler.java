package es.developer.achambi.pkmng.modules.calculator;

import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.modules.ConfigurationDataAssembler;
import es.developer.achambi.pkmng.modules.calculator.presenter.DamageCalculatorPresenterFactory;
import es.developer.achambi.pkmng.modules.calculator.presenter.IDamageCalculatorPresenterFactory;

public class DamageCalculatorAssembler {
    private MainExecutor executor;
    private ConfigurationDataAssembler dataAssembler;

    public DamageCalculatorAssembler setExecutor(MainExecutor executor) {
        this.executor = executor;
        return this;
    }

    public DamageCalculatorAssembler setDataAssembler(ConfigurationDataAssembler dataAssembler) {
        this.dataAssembler = dataAssembler;
        return this;
    }

    public IDamageCalculatorPresenterFactory getPresenterFactory() {
        return new DamageCalculatorPresenterFactory(
                dataAssembler.getConfigurationDataAccess(), executor
        );
    }
}
