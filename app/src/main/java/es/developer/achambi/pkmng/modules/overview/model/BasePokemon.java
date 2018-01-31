package es.developer.achambi.pkmng.modules.overview.model;

import android.os.Parcelable;

public interface BasePokemon extends Parcelable {
    public static final int EMPTY_ID = -1;
    int getHP();
    int getAttack();
    int getDefense();
    int getSpAttack();
    int getSPDefense();
    int getSpeed();
    int getId();
}
