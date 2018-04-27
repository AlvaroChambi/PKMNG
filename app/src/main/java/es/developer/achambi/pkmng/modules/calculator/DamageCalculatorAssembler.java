package es.developer.achambi.pkmng.modules.calculator;

import es.developer.achambi.pkmng.modules.calculator.presenter.IDamageCalculatorPresenterFactory;

public class DamageCalculatorAssembler {
    private IDamageCalculatorPresenterFactory presenterFactory;

    public IDamageCalculatorPresenterFactory getPresenterFactory() {
        return presenterFactory;
    }

    public void setPresenterFactory(IDamageCalculatorPresenterFactory presenterFactory) {
        this.presenterFactory = presenterFactory;
    }
}
