package es.developer.achambi.pkmng.core.ui;

import android.os.Bundle;

public interface ViewPresenter {
    void onSaveInstanceState(Bundle bundle);
    void onRestoreInstanceState(Bundle bundle);
}
