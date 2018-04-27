package es.developer.achambi.pkmng.modules.calculator.presenter;

import es.developer.achambi.pkmng.core.ui.Screen;

public interface IDamageCalculatorPresenterFactory {
    DamageCalculatorPresenter buildPresenter(Screen screen);
}
