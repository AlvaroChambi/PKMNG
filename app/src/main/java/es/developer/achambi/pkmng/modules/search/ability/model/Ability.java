package es.developer.achambi.pkmng.modules.search.ability.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ability implements Parcelable {
    public static final String ADAPTABILITY = "Adaptability";
    public static final String GUTS = "Guts";
    public static final String FLUFFY = "Fluffy";
    public static final String SOLID_ROCK = "Solid Rock";
    public static final String FILTER = "Filter";
    public static final String PRISM_ARMOR = "Prism Armor";
    public static final String SNIPER = "Sniper";
    public static final String TINTED_LENS = "Tinted Lens";

    private int id;
    private String name;
    private String description;
    private String descriptionShort;

    public Ability() {
        id = -1;
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

    @Override
    public boolean equals(Object obj) {
        if( this == obj ) {
            return true;
        }

        if( obj == null ) {
            return false;
        }

        if( getClass() != obj.getClass() ) {
            return false;
        }
        Ability ability = (Ability)obj;
        if( ability.id == id &&
                ability.name.equals(name) &&
                ability.description == description &&
                ability.descriptionShort == descriptionShort ) {
            return true;
        }

        return false;
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
