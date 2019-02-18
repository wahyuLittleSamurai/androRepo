package com.example.withfire;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference readDatabase;

    private String getLongitude;
    private String getLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        readDatabase = FirebaseDatabase.getInstance().getReference("GlWChybjtkg6NexoUbcvm2zE9mH2");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mMap.clear();
                mMap.setMinZoomPreference(14.0f);

                dataPosisi dataPosisi = dataSnapshot.getValue(com.example.withfire.dataPosisi.class);
                //Toast.makeText(myMap.this, "dapat "+dataPosisi.getLongitude(), Toast.LENGTH_SHORT).show();
                getLongitude = dataPosisi.getLongitude();
                getLat = dataPosisi.getLatittude();

                Double longDouble = Double.parseDouble(getLongitude);
                Double latDouble = Double.parseDouble(getLat);

                LatLng sydney = new LatLng(latDouble, longDouble);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        readDatabase.addValueEventListener(postListener);


        // Add a marker in Sydney and move the camera

    }


}
