package es.developer.achambi.pkmng.core.ui;

import android.os.Parcel;
import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class ParcelUtil {
    public static void writeEnumToParcel(Parcel in, Enum e) {
        in.writeInt(e.ordinal());
    }

    public static <E> E readEnumFromParcel(Parcel out, Class<E> clazz) {
        E[] enumConstants = clazz.getEnumConstants();
        return enumConstants[out.readInt()];
    }

    public static <K extends Enum,V extends  Integer> void writeParcelableMap(
            Parcel parcel, Map<K, V > map) {
        parcel.writeInt(map.size());
        for(Map.Entry<K, V> e : map.entrySet()){
            ParcelUtil.writeEnumToParcel(parcel, e.getKey());
            parcel.writeInt(e.getValue().intValue());
        }
    }

    public static <K extends Enum,V extends  Integer> HashMap<K,V> readParcelableMap(
            Parcel parcel, Class<K> kClass, Class<V> vClass) {
        int size = parcel.readInt();
        HashMap<K, V> map = new HashMap<K, V>(size);
        for(int i = 0; i < size; i++){
            map.put(ParcelUtil.readEnumFromParcel(parcel, kClass),
                    vClass.cast(parcel.readParcelable(vClass.getClassLoader())));
        }
        return map;
    }

    public static <K extends Enum, V extends Enum> void writeParcelablePair(
            Parcel parcel, Pair<K,V> pair ) {
        ParcelUtil.writeEnumToParcel(parcel, pair.first);
        ParcelUtil.writeEnumToParcel(parcel, pair.second);
    }


    public static <K extends Enum, V extends Enum> Pair<K,V> readParcelablePair(
            Parcel parcel, Class<K> kClass, Class<V> vClass ) {
        Pair<K,V> pair = new Pair<>(
                ParcelUtil.readEnumFromParcel(parcel, kClass),
                ParcelUtil.readEnumFromParcel(parcel, vClass)
        );

        return pair;
    }
}
