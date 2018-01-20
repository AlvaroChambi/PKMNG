package es.developer.achambi.pkmng.modules.search.ability.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ability implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String descriptionShort;

    public Ability() {
        name = "";
        description = "";
        descriptionShort = "";
    }

    public Ability(int id, String name, String description, String descriptionShort) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.descriptionShort = descriptionShort;
    }

    public Ability( Ability ability ) {
        this( ability.getId(), ability.getName(),
                ability.getDescription(), ability.getDescriptionShort() );
    }

    protected Ability(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        descriptionShort = in.readString();
    }

    public static final Creator<Ability> CREATOR = new Creator<Ability>() {
        @Override
        public Ability createFromParcel(Parcel in) {
            return new Ability(in);
        }

        @Override
        public Ability[] newArray(int size) {
            return new Ability[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(descriptionShort);
    }
}
