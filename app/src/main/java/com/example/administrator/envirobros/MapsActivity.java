package com.example.administrator.envirobros;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LocationManager manager;
    //private LatLng userloc = new LatLng(22.28, 114.16);
    private LatLng userloc = new LatLng(22.43060133, 114.20836061);
    private LatLng mls = new LatLng(22.43060133, 114.20836061);
    private LatLng wks = new LatLng(22.43004101, 114.23630923);
    private LatLng tungchung = new LatLng(22.29424854, 113.94256443);
    //order is TC, WKS, MLS Beaches
    private ArrayList<Marker> markers;
    private boolean onceonly = true;
    private double lat, lng;
    private CustomTextInfoWindowClass infoWindowClass;
    private int one_selected;
    private double ph[] = {8.3,8.25,8.18};
    private String[] strings = {"temperature:  ", "ph:  ","conductivity:  "};
    private double temperature[] = {24.3,22.9,23.4};
    private double conductivity[] = {1.55,2.76,3.06};
    private double m_Text=0.0;
    private Marker newestmarker;
    private String m_Title ="";

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        one_selected = intent.getIntExtra("type", 1);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        infoWindowClass = new CustomTextInfoWindowClass(this, one_selected);
        onceonly = true;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        onceonly = true;
        if(permissioncheck())
        {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

    @Override
    public void onResume() {

        super.onResume();

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
        mMap.setInfoWindowAdapter(infoWindowClass);
        permissions();
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.addMarker(new MarkerOptions().position(tungchung)
                .title("Tung Chung Pier")
                .snippet(strings[one_selected] + arrayreturn(one_selected)[0]));
        mMap.addMarker(new MarkerOptions().position(mls)
                .title("Ma Liu Shui Pier")
                .snippet(strings[one_selected] + arrayreturn(one_selected)[1]));
        mMap.addMarker(new MarkerOptions().position(wks)
                .title("Wu Kai Sha Beach")
                .snippet(strings[one_selected] + arrayreturn(one_selected)[2]));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userloc,18));
    }
    private double[] arrayreturn(int number){
        if(number==0)
        {
            double[] array = temperature;
            return array;
        }
        else if(number==1)
        {
            double[] array = ph;
            return array;
        }else{
            double[] array = conductivity;
            return array;
        }
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        infoWindowClass.getInfoContents(marker);
        return false;
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please input the data that you have collected ");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

// Add a TextView here for the "Title" label, as noted in the comments
        final EditText titleBox = new EditText(this);
        titleBox.setHint("Title");
        titleBox.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        layout.addView(titleBox); // Notice this is an add method

// Add another TextView here for the "Description" label
        final EditText descriptionBox = new EditText(this);
        descriptionBox.setHint("Description");
        descriptionBox.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        descriptionBox.setInputType(InputType.TYPE_CLASS_NUMBER |  InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(descriptionBox); // Another add method
        builder.setView(layout);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Title = titleBox.getText().toString();
                try {
                    m_Text = Double.parseDouble(descriptionBox.getText().toString());
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                    dialog.cancel();
                    Toast.makeText(MapsActivity.this, "Sorry, the description you provided must be in the form of a decimial integer, thank you.", Toast.LENGTH_LONG).show();
                    return;
                }
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(m_Title)
                        .snippet(strings[one_selected] + m_Text));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}