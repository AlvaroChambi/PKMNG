package es.developer.achambi.pkmng.core.utils;

import android.os.Parcel;
import android.support.v4.util.Pair;

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

    public static <K extends Enum> HashMap<K, Integer> readParcelableMap(
            Parcel parcel, Class<K> kClass) {
        int size = parcel.readInt();
        HashMap<K, Integer> map = new HashMap<K, Integer>(size);
        for(int i = 0; i < size; i++){
            map.put(ParcelUtil.readEnumFromParcel(parcel, kClass),
                    Integer.valueOf(parcel.readInt()));
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

    public static void writeBoolean( Parcel parcel, boolean value ) {
        if(value) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
    }

    public static Boolean readBoolean( Parcel parcel ) {
        int value = parcel.readInt();
        if( value == 0 ) {
            return false;
        } else {
            return true;
        }
    }
}
