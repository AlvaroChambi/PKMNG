package es.developer.achambi.pkmng.modules.search.item.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{
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
