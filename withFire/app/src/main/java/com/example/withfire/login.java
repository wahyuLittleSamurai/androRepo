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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener{

    private EditText email, password;
    private Button login;
    private TextView register;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.bt_submit);
        register = (TextView)findViewById(R.id.tv_register);

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    private void userLogin(){
        String getEmail = email.getText().toString().trim();
        String getPassword = password.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(login.this, dashboard.class);
                            login.this.startActivity(intent);
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == login){
            userLogin();
        }
        if(view == register){
            finish();
            Intent intent = new Intent(login.this, MainActivity.class);
            login.this.startActivity(intent);
        }
    }
}
