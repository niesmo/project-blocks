package com.osu.cse.projectblocks.activities;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
//import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.data.Repository;
import com.osu.cse.projectblocks.models.Cafeteria;
import com.osu.cse.projectblocks.models.Coordinates;

import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Cafeteria index;
    private List<Cafeteria> allcafe;

    //private static final String TAG = MapsActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG,"onCreate() called");
        setContentView(R.layout.activity_maps);
        allcafe = Repository.getAllCafeterias();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        index = nearestCafe(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d(TAG, "onDestroy() called");
    }

    private Cafeteria nearestCafe(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        }

        Cafeteria res = allcafe.get(0);
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        float min = (float) Integer.MAX_VALUE;
        for (Iterator<Cafeteria> i = allcafe.iterator(); i.hasNext();)
        {
            Cafeteria cafeteria = i.next();
            double cafe_lat = cafeteria.getLocation().getCoordinates().getLatitude();
            double cafe_lon = cafeteria.getLocation().getCoordinates().getLongitude();
            float[] results = new float[10];
            Location.distanceBetween(lat, lon, cafe_lat, cafe_lon, results);
            if(results[0] < min){
                min = results[0];
                res = cafeteria;
            }
        }
        return res;
     }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng nearest = new LatLng(index.getLocation().getCoordinates().getLatitude(), index.getLocation().getCoordinates().getLongitude());
        map.addMarker(new MarkerOptions().position(nearest).title(index.getName()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(nearest, 15));
    }
}
