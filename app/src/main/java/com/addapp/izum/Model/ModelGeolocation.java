package com.addapp.izum.Model;

import com.addapp.izum.Adapter.AdapterMarkerMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ILDAR on 21.07.2015.
 */
public class ModelGeolocation {

    public ArrayList<AdapterMarkerMap.MapItem> getArray(){
        ArrayList<AdapterMarkerMap.MapItem> items = new ArrayList<>();

        items.add(new AdapterMarkerMap.MapItem.Builder("1", "Азат")
                .setAvatarUrl("http://im.topufa.org/")
                .setLatLng(new LatLng(-34, -34))
                .build());
        items.add(new AdapterMarkerMap.MapItem.Builder("3", "Ильдар")
                .setAvatarUrl("http://im.topufa.org/quadro/a61fdbde432bd25506b9608264dbc693/thumb_848a5b653931063a108bec3aa100f543.jpg")
                .setLatLng(new LatLng(34, -34))
                .build());
        items.add(new AdapterMarkerMap.MapItem.Builder("2", "Рустам")
                .setAvatarUrl("http://im.topufa.org/quadro/49da3572d00c53bdd77258fe35e9a23d/thumb_06c6e7ea041615c2a949a47db9fa1b0a.jpg")
                .setLatLng(new LatLng(-34, 34))
                .build());

        return items;
    }
}
