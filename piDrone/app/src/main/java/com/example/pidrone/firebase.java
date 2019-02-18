package com.example.pidrone;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firebase extends AppCompatActivity {

    private DatabaseReference database;

    private Button btnSubmit, btnAuth;
    private EditText longitude;
    private EditText latitude;
    private EditText tinggi;
    private TextView balasan;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
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

        longitude = (EditText)findViewById(R.id.et_longitude);
        latitude = (EditText)findViewById(R.id.et_latitude);
        tinggi = (EditText)findViewById(R.id.et_tinggi);
        btnSubmit = (Button)findViewById(R.id.bt_submit);
        btnAuth = (Button)findViewById(R.id.bt_auth);


        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = firebaseAuth.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getLong = longitude.getText().toString();
                String getLat = latitude.getText().toString();
                String getTinggi = tinggi.getText().toString();

                if (getLong != null && getLat != null && getTinggi != null)
                    submitBarang(new Barang(getLong, getLat, getTinggi));
                else{
                    Snackbar.make(findViewById(R.id.bt_submit), "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            longitude.getWindowToken(), 0);
                }

            }
        });

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getLong = longitude.getText().toString();
                String getLat = latitude.getText().toString();
                String getTinggi = tinggi.getText().toString();
                final String getBalasan = balasan.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(getLong, getLat)
                        .addOnCompleteListener(firebase.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(firebase.this, "oke", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(firebase.this, "not sukses", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void submitBarang(Barang barang) {

        database.child("pidrone").push().setValue(barang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                longitude.setText("");
                latitude.setText("");
                tinggi.setText("");
                Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }



}
