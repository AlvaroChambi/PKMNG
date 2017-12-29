package es.developer.achambi.pkmng.modules.create.view;

import android.os.Parcel;
import android.os.Parcelable;

public class MoveConfigurationRepresentation implements Parcelable{
    public final int id;
    public final String name;
    public final String type;
    public final String power;

    public MoveConfigurationRepresentation(int id, String name, String type, String power) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
    }

    protected MoveConfigurationRepresentation(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        power = in.readString();
    }

    public static final Creator<MoveConfigurationRepresentation> CREATOR = new Creator<MoveConfigurationRepresentation>() {
        @Override
        public MoveConfigurationRepresentation createFromParcel(Parcel in) {
            return new MoveConfigurationRepresentation(in);
        }

        @Override
        public MoveConfigurationRepresentation[] newArray(int size) {
            return new MoveConfigurationRepresentation[size];
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
        dest.writeString(type);
        dest.writeString(power);
    }
}
