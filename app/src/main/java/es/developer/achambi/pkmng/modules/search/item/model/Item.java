package es.developer.achambi.pkmng.modules.search.item.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{
    public static final String CHILAN_BERRY = "Chilan Berry";
    public static final String OCCA_BERRY = "Occa Berry";
    public static final String PASSHO_BERRY = "Passho Berry";
    public static final String WACAN_BERRY = "Wacan Berry";
    public static final String RINDO_BERRY = "Rindo Berry";
    public static final String YACHE_BERRY = "Yache Berry";
    public static final String CHOPLE_BERRY = "Chople Berry";
    public static final String KEBIA_BERRY = "Kebia Berry";
    public static final String SHUCA_BERRY = "Shuca Berry";
    public static final String COBA_BERRY = "Coba Berry";
    public static final String PAYAPA_BERRY = "Payapa Berry";
    public static final String TANGA_BERRY = "Tanga Berry";
    public static final String CHARTI_BERRY = "Charti Berry";
    public static final String KASIB_BERRY = "Kasib Berry";
    public static final String HABAN_BERRY = "Haban Berry";
    public static final String COLBUR_BERRY = "Colbur Berry";
    public static final String BABIRI_BERRY = "Babiri Berry";

    public static final String EXPERT_BELT = "Expert Belt";
    public static final String LIFE_ORB = "Life Orb";

    private int id;
    private String imageUrl;
    private String name;
    private String descriptionShort;
    private String description;

    public Item() {
        id = -1;
        name = "";
        imageUrl = "";
        descriptionShort = "";
        description = "";
    }

    public Item( int id, String imageUrl, String name,
                 String descriptionShort, String description ) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.descriptionShort = descriptionShort;
        this.description = description;
    }

    public Item( Item item ) {
        this( item.getId(), item.getImageUrl(), item.getName(),
                item.getDescriptionShort(), item.getDescription() );
    }

    protected Item(Parcel in) {
        id = in.readInt();
        imageUrl = in.readString();
        name = in.readString();
        descriptionShort = in.readString();
        description = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeString(descriptionShort);
        dest.writeString(description);
    }
}
