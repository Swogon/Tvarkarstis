package com.example.tvarkarastis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class registerActivity extends AppCompatActivity {

    EditText emailID, CreatePassword, passwordConfirm;
    Button registruotis;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.Email);
        CreatePassword = findViewById(R.id.CreatePassword);
        passwordConfirm = findViewById(R.id.confirmPassword);
        registruotis = findViewById(R.id.buttonRegister);


        registruotis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String passwordStr = CreatePassword.getText().toString();

                if(email.isEmpty()){
                    emailID.setError("Įrašykite email");
                    emailID.requestFocus();
                }
                else if(passwordStr.isEmpty()) {
                    CreatePassword.setError("Įrašykite slaptažodį");
                    CreatePassword.requestFocus();
                }

                else if(email.isEmpty() && passwordStr.isEmpty()) {
                    Toast.makeText(registerActivity.this,"Laukai yra tušti",Toast.LENGTH_SHORT);
                }
                else if(!(email.isEmpty() && passwordStr.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,passwordStr).addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(registerActivity.this, "Paskyros sukurti nepavyko, bandykite dar kartą", Toast.LENGTH_SHORT);
                            } else {
                                startActivity(new Intent(registerActivity.this, Kursopasirinkimas.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(registerActivity.this,"Error!",Toast.LENGTH_SHORT);
                }
            }
        });
    }
}