package es.developer.achambi.pkmng.modules.search.move.model;

import android.os.Parcel;
import android.os.Parcelable;

import es.developer.achambi.pkmng.core.utils.ParcelUtil;
import es.developer.achambi.pkmng.modules.overview.model.Type;

public class Move implements Parcelable{
    public enum Category {
        PHYSICAL,
        SPECIAL,
        EMPTY
    }
    private int id;
    private String name;
    private String effect;
    private Type type;
    private Category category;
    private int power;
    private int accuracy;
    private int pp;
    private boolean contact;

    public Move( int id ) {
        this.id = id;
        name = "";
        type = Type.EMPTY;
        category = Category.EMPTY;
        contact = false;
    }

    public Move() {
        this( -1 );
    }

    public Move(int id, String name, String effect, Type type, Category category,
                int power, int accuracy, int pp, boolean contact) {
        this.id = id;
        this.name = name;
        this.effect = effect;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accuracy = accuracy;
        this.pp = pp;
        this.contact = contact;
    }

    public Move( Move move ) {
        this( move.getId(), move.getName(), move.getEffect(), move.getType(), move.getCategory(),
                move.getPower(), move.getAccuracy(), move.getPp(), move.isContact() );
    }

    protected Move(Parcel in) {
        id = in.readInt();
        name = in.readString();
        effect = in.readString();
        type = ParcelUtil.readEnumFromParcel(in, Type.class);
        category = ParcelUtil.readEnumFromParcel(in, Category.class);
        power = in.readInt();
        accuracy = in.readInt();
        pp = in.readInt();
        contact = ParcelUtil.readBoolean( in );
    }

    public void setContact( boolean isContact ) {
        this.contact = isContact;
    }

    public boolean isContact() {
        return contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
        ParcelUtil.writeEnumToParcel(dest, category);
        dest.writeInt(power);
        dest.writeInt(accuracy);
        dest.writeInt(pp);
    }
}
