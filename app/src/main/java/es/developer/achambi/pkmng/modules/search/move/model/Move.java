package es.developer.achambi.pkmng.modules.search.move.model;

import android.os.Parcel;
import android.os.Parcelable;

import es.developer.achambi.pkmng.core.utils.ParcelUtil;
import es.developer.achambi.pkmng.modules.overview.model.Pokemon;

public class Move implements Parcelable{
    private int id;
    private String name;
    private String effect;
    private Pokemon.Type type;
    private String category;
    private int power;
    private int accuracy;
    private int pp;

    public Move() {
        type = Pokemon.Type.EMPTY;
    }

    protected Move(Parcel in) {
        id = in.readInt();
        name = in.readString();
        effect = in.readString();
        type = ParcelUtil.readEnumFromParcel(in, Pokemon.Type.class);
        category = in.readString();
        power = in.readInt();
        accuracy = in.readInt();
        pp = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pokemon.Type getType() {
        return type;
    }

    public void setType(Pokemon.Type type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public static final Creator<Move> CREATOR = new Creator<Move>() {
        @Override
        public Move createFromParcel(Parcel in) {
            return new Move(in);
        }

        @Override
        public Move[] newArray(int size) {
            return new Move[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(effect);
        ParcelUtil.writeEnumToParcel(dest, type);
        dest.writeString(category);
        dest.writeInt(power);
        dest.writeInt(accuracy);
        dest.writeInt(pp);
    }
}
