package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.os.Parcel;
import android.os.Parcelable;

import es.developer.achambi.pkmng.R;

public class OverviewPokemonRepresentation
        implements SearchListData, Parcelable {
    public final int id;
    public final String name;
    public final String image;
    public final String type;
    public final String totalStats;
    public final String hp;
    public final String defense;
    public final String attack;
    public final String spAttack;
    public final String spDefense;
    public final String speed;

    public OverviewPokemonRepresentation(
            int id,
           String name,
           String image,
           String type,
           String totalStats,
           String hp,
           String defense,
           String attack,
           String spAttack,
           String spDefense,
           String speed) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.totalStats = totalStats;
        this.hp = hp;
        this.defense = defense;
        this.attack = attack;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
    }

    protected OverviewPokemonRepresentation(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        type = in.readString();
        totalStats = in.readString();
        hp = in.readString();
        defense = in.readString();
        attack = in.readString();
        spAttack = in.readString();
        spDefense = in.readString();
        speed = in.readString();
    }

    public static final Creator<OverviewPokemonRepresentation> CREATOR = new Creator<OverviewPokemonRepresentation>() {
        @Override
        public OverviewPokemonRepresentation createFromParcel(Parcel in) {
            return new OverviewPokemonRepresentation(in);
        }

        @Override
        public OverviewPokemonRepresentation[] newArray(int size) {
            return new OverviewPokemonRepresentation[size];
        }
    };

    @Override
    public int getViewType() {
        return R.id.pokemon_view_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(type);
        dest.writeString(totalStats);
        dest.writeString(hp);
        dest.writeString(defense);
        dest.writeString(attack);
        dest.writeString(spAttack);
        dest.writeString(spDefense);
        dest.writeString(speed);
    }
}
