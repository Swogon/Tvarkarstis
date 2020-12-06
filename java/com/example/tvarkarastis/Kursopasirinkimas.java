package com.example.tvarkarastis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Kursopasirinkimas extends AppCompatActivity {

     TextView ISI1kr;
     TextView BioI1kr;
     Button btnLogout;
     FirebaseAuth mFirebaseAuth;
     private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_pasirinkimas);

        ISI1kr = findViewById(R.id.ISI1kr);
        BioI1kr = findViewById(R.id.BioI1kr);

        btnLogout = findViewById(R.id.atsijungti);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Kursopasirinkimas.this, secondactivity.class));
            }
        });

        ISI1kr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData process = new fetchData();
                process.execute();

                Intent intent = new Intent(Kursopasirinkimas.this, scheduleActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed(){

    }
}