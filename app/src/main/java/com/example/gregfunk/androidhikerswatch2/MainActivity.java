package com.example.gregfunk.androidhikerswatch2;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView tlat;
    TextView tlng;
    TextView taltitude;
    TextView tbearing;
    TextView tspeed;
    TextView taccuracy;
    TextView taddress;

    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tlat = (TextView) findViewById(R.id.lat);
        tlng = (TextView) findViewById(R.id.lng);
        taltitude = (TextView) findViewById(R.id.altitude);
        tbearing = (TextView) findViewById(R.id.bearing);
        tspeed = (TextView) findViewById(R.id.speed);
        taccuracy = (TextView) findViewById(R.id.accuracy);
        taddress = (TextView) findViewById(R.id.address);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(provider);
        onLocationChanged(location);
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        Double alt = location.getAltitude();
        Float bearing = location.getBearing();
        Float speed = location.getSpeed();
        Float accuracy = location.getAccuracy();

        tlat.setText("Latitude: " + lat.toString());
        tlng.setText("Longitude:" + lng.toString());
        taltitude.setText("Altitude: " + alt.toString() + "m");
        tbearing.setText("Beaning: " + bearing.toString());
        tspeed.setText("Speed: " + speed.toString() + "m/s");
        taccuracy.setText("Accuracy: " + accuracy.toString() + "m");

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lat, lng, 1);

            if (listAddresses != null && listAddresses.size() > 0) {
                String addressHolder = "";
                for (int i=0; i <= listAddresses.get(0).getMaxAddressLineIndex(); i++) {
                    addressHolder += listAddresses.get(0).getAddressLine(i) + "\n";
                }
                taddress.setText("Address:\n" + addressHolder);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
