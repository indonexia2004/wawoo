package com.example.pc.aloza;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by PC on 01/06/2015.
 */
public class AloraMapFragment extends MapFragment {

    GoogleMap googleMap = getMap();

    public void addMarker(LatLng latLng) {
        Log.d("MapActivity", "addMarker lat, lng: "+ latLng.latitude + " "+ latLng.longitude);
        GoogleMap googleMap = getMap();

        googleMap.addMarker(new MarkerOptions().position(latLng).title("Hello Tran Hung Dao"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
}
