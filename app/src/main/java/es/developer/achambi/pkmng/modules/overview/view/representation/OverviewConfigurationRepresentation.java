package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.os.Parcel;
import android.os.Parcelable;

import es.developer.achambi.pkmng.R;

public class OverviewConfigurationRepresentation
        implements SearchListData, Parcelable{
    public final int id;
    public final String name;
    public final String image;
    public final String pokemonName;
    public final String type;
    public final String totalStats;
    public final String ability;
    public final String item;
    public final String nature;

    public OverviewConfigurationRepresentation(
            int id,
            String name,
            String image,
            String pokemonName,
            String type,
            String totalStats,
            String ability,
            String item,
            String nature
            ) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.pokemonName = pokemonName;
        this.type = type;
        this.totalStats = totalStats;
        this.ability = ability;
        this.item = item;
        this.nature = nature;
    }

    protected OverviewConfigurationRepresentation(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        pokemonName = in.readString();
        type = in.readString();
        totalStats = in.readString();
        ability = in.readString();
        item = in.readString();
        nature = in.readString();
    }

    public static final Creator<OverviewConfigurationRepresentation> CREATOR = new Creator<OverviewConfigurationRepresentation>() {
        @Override
        public OverviewConfigurationRepresentation createFromParcel(Parcel in) {
            return new OverviewConfigurationRepresentation(in);
        }

        @Override
        public OverviewConfigurationRepresentation[] newArray(int size) {
            return new OverviewConfigurationRepresentation[size];
        }
    };

    @Override
    public int getViewType() {
        return R.id.pokemon_configuration_view_id;
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
        dest.writeString(pokemonName);
        dest.writeString(type);
        dest.writeString(totalStats);
        dest.writeString(ability);
        dest.writeString(item);
        dest.writeString(nature);
    }
}
