package es.developer.achambi.pkmng.modules.overview.view.representation;

import android.os.Parcel;
import android.os.Parcelable;

public class OverviewConfigurationRepresentation
        implements OverviewListItemViewRepresentation, Parcelable{
    public final String name;
    public final String image;
    public final String pokemonName;
    public final String type;
    public final String totalStats;
    public final String ability;
    public final String item;
    public final String nature;

    public OverviewConfigurationRepresentation(
            String name,
            String image,
            String pokemonName,
            String type,
            String totalStats,
            String ability,
            String item,
            String nature
            ) {
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
    public ViewType getViewType() {
        return ViewType.POKEMON_CONFIG;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
