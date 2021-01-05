package com.example.tvarkarastis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvarkarastis.data.preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class secondactivity extends AppCompatActivity {
    EditText ETUsername, ETPassword;
    TextView Prisijungimas;
    TextView PaskyrosKlausimas;
    TextView svecias;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_secondactivity);

        ETPassword = findViewById(R.id.editPassword);
        ETUsername = findViewById(R.id.editUsername);
        Prisijungimas = findViewById(R.id.Prisijungimas);
        PaskyrosKlausimas = findViewById(R.id.klausimas);

        mFirebaseAuth = FirebaseAuth.getInstance();

        svecias = findViewById(R.id.guest);




        if(preferences.kalba==1)
        {
            ETPassword.setHint("Password");
            ETUsername.setHint("Email");
            Prisijungimas.setText("Login");
            PaskyrosKlausimas.setText(getString(R.string.susikurtiPaskyraEN));
            svecias.setText(" - or login as guest - ");
        }
        else
        {
            ETPassword.setHint("Slaptažodis");
            ETUsername.setHint("Email");
            Prisijungimas.setText("Prisijungimas");
            PaskyrosKlausimas.setText(getString(R.string.susikurtiPaskyra));
            svecias.setText(" - arba jungtis kaip svečias - ");
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(secondactivity.this, "Prisijungta!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(secondactivity.this, Kursopasirinkimas.class);
                    startActivity(i);
                }
                else {
                }
            }
        };

        ETPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int ActionId, KeyEvent event) {

                String email = ETUsername.getText().toString();
                String passwordStr = ETPassword.getText().toString();
                if(email.isEmpty()){
                    ETUsername.setError("Įrašykite email");
                    ETUsername.requestFocus();
                }
                else if(passwordStr.isEmpty()) {
                    ETPassword.setError("Įrašykite slaptažodį");
                    ETPassword.requestFocus();
                }
                else if(email.isEmpty() && passwordStr.isEmpty()) {
                    Toast.makeText(secondactivity.this,"Laukai yra tušti",Toast.LENGTH_SHORT);
                }
                else if(!(email.isEmpty() && passwordStr.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email,passwordStr).addOnCompleteListener(secondactivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(secondactivity.this, "Prisijungti nepavyko, bandykite dar kartą", Toast.LENGTH_SHORT);
                            } else {
                                Toast.makeText(secondactivity.this, "Prisijungta sėkmingai!", Toast.LENGTH_SHORT);
                                startActivity(new Intent(secondactivity.this, Kursopasirinkimas.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(secondactivity.this,"Error!",Toast.LENGTH_SHORT);
                }
                return false;
            }
        });

        PaskyrosKlausimas.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      Intent intent = new Intent(secondactivity.this, registerActivity.class);
                                      startActivity(intent);
                                  }
        });

        svecias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(secondactivity.this, Kursopasirinkimas.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    public void onBackPressed(){
       // Intent intent = new Intent(secondactivity.this, MainActivity.class);
       // startActivity(intent);
       /* Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a); */
    }
}
