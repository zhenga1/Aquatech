package com.example.administrator.envirobros;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager manager;
    private LatLng userloc = new LatLng(22.28, 114.16);
    private LatLng mls = new LatLng(22.43060133, 114.20836061);
    private LatLng wks = new LatLng(22.43004101, 114.23630923);
    private LatLng tungchung = new LatLng(22.29424854, 113.94256443);
    private Location curloc;
    private boolean onceonly = true;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        onceonly = true;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Intent intent = getIntent();
        //int number = Integer.parseInt(intent.getStringExtra("type"));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {

        super.onResume();
        onceonly = true;
        if(permissioncheck())
        {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }
    @Override
    public void onPause() {

        super.onPause();
        onceonly = true;
        manager.removeUpdates(this);
    }
    public boolean permissioncheck(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(21);
        permissions();
        mMap.addMarker(new MarkerOptions().position(tungchung));
        mMap.addMarker(new MarkerOptions().position(mls));
        mMap.addMarker(new MarkerOptions().position(wks));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userloc,18));
    }
    private void permissions()
    {
        if(ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.INTERNET)) {
                Toast.makeText(MapsActivity.this, "This application needs to access the internet in order to provide better service to our clients and make it easier for you to navigate our map. If you do not allow us to use this permission, we may not be able to position the map conveniently.", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.INTERNET},44);

        }
        if(ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        else if(ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(MapsActivity.this, "This application needs location permission in order to provide better service to our clients and make it easier for you to navigate our map", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(MapsActivity.this, "This application needs location permission in order to provide better service to our clients and make it easier for you to navigate our map", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},44);

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        curloc = location;
        userloc = new LatLng(location.getLatitude(),location.getLongitude());
        lat = location.getLatitude();
        lng = location.getLongitude();
        if (onceonly) {

            mMap.animateCamera(CameraUpdateFactory.newLatLng(userloc), 2000, null);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userloc, 18));
            onceonly = false;
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}