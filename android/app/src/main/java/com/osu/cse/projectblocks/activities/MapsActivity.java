package com.osu.cse.projectblocks.activities;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.osu.cse.projectblocks.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private int index;
    private String [] cafename = {"12th Avenue Bread Company", "Berry Café", "CFAES Cafe", "The Caffeine Element", "The Campus Grind - McPherson", "Crane Cafe", "The Campus Grind - Drinko", "Terra Byte Cafe", "KSA cafe", "Connecting Grounds", "Curl Market", "Courtside Cafe & Courtside Cafe/Juice2", "Marketplace C-Store", "EspressOH & Union Market", "Oxley's", "The PAD", "Thyme & Change"};
    private double [][] position = {{39.996728,-83.012867}, {39.999525,-83.014812}, {40.005661,-83.027204}, {39.994994,-83.017049}, {40.002693,-83.012330}, {39.998659,-83.010179}, {39.996474,-83.008291}, {40.001876,-83.013276}, {40.004290,-83.017074}, {40.004234,-83.014448}, {40.004545,-83.011000}, {40.000334,-83.018875}, {39.994433,-83.014077}, {39.997925, -83.008586}, {40.003083, -83.017134}, {39.997925, -83.008586}, {40.007337, -83.018192}, {40.000980, -83.015327}};

    private static final String TAG="MAPSACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate() called");
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        index = nearestCafe(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private int nearestCafe(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                //locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        }

        int res = 0;
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        double min = (double) Integer.MAX_VALUE;
        for (int i=0; i<cafename.length; i++)
        {
            double temp = (position[i][0]-lat)*(position[i][0]-lat) + (position[i][1]-lon)*(position[i][1]-lon);
            if (temp < min)
            {
                min = temp;
                res = i;
            }
        }
        return res;
     }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng nearest = new LatLng(position[index][0], position[index][1]);
        map.addMarker(new MarkerOptions().position(nearest).title(cafename[index]));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(nearest, 15));
    }
}
