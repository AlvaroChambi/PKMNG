package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcelable;

public interface BasePokemon extends Parcelable {
    int getHP();
    int getAttack();
    int getDefense();
    int getSpAttack();
    int getSPDefense();
    int getSpeed();
    boolean hasConfig();
}
