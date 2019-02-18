package com.example.withfire;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class dashboard extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView email;
    private Button logout, set, gotoMap;
    private DatabaseReference databaseReference;
    private DatabaseReference readDatabase;
    private EditText longitude, latitude, tinggi;

    private TextView getLat, getLong, getTinggi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        firebaseAuth = firebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            Intent intent = new Intent(dashboard.this, login.class);
            dashboard.this.startActivity(intent);
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        longitude = (EditText) findViewById(R.id.et_long);
        latitude = (EditText) findViewById(R.id.et_lat);
        tinggi = (EditText) findViewById(R.id.et_tinggi);
        set = (Button)findViewById(R.id.bt_send);
        email = (TextView)findViewById(R.id.textView2);
        logout = (Button)findViewById(R.id.button) ;

        email.setText("welcome "+user.getEmail());

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getLong = longitude.getText().toString().trim();
                String getLat = latitude.getText().toString().trim();
                String getTinggi = tinggi.getText().toString().trim();

                dataPosisi dataPosisi = new dataPosisi(getLong, getLat, getTinggi);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child(user.getUid()).setValue(dataPosisi);
                Toast.makeText(dashboard.this, "save ", Toast.LENGTH_LONG).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(dashboard.this, login.class);
                dashboard.this.startActivity(intent);
            }
        });


        getLong = (TextView)findViewById(R.id.tv_longitude);
        getLat = (TextView)findViewById(R.id.tv_latitude);
        getTinggi = (TextView)findViewById(R.id.tv_tinggi);

        readDatabase = FirebaseDatabase.getInstance().getReference("GlWChybjtkg6NexoUbcvm2zE9mH2");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataPosisi dataPosisi = dataSnapshot.getValue(com.example.withfire.dataPosisi.class);
                //Toast.makeText(dashboard.this, "dapat "+dataPosisi.getLongitude(), Toast.LENGTH_SHORT).show();
                getLong.setText(dataPosisi.getLongitude());
                getLat.setText(dataPosisi.getLatittude());
                getTinggi.setText(dataPosisi.getTinggi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        readDatabase.addValueEventListener(postListener);

        gotoMap = (Button)findViewById(R.id.bt_map);

        gotoMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, myMap.class);
                dashboard.this.startActivity(intent);
            }
        });
    }




}
