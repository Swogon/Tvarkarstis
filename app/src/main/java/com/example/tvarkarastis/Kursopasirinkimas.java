package com.example.tvarkarastis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tvarkarastis.data.language;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.text.UStringsKt;

import static com.example.tvarkarastis.R.id.spinnerDalykas;
import static com.example.tvarkarastis.R.id.spinnerMetai;

public class Kursopasirinkimas extends AppCompatActivity {


    public static final String SHARED_PREFSS = "sharedPrefss";

     Button btnLogout;
     FirebaseAuth mFirebaseAuth;
     private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_pasirinkimas);

        Spinner spinner = findViewById(R.id.spinnerMetai);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Metai, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Spinner spinner2 = findViewById(R.id.spinnerGrupe);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.Grupe4, R.layout.spinner_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        

        Spinner spinner3 = findViewById(spinnerDalykas);
        ArrayAdapter<CharSequence> adapterDalykas = null;
        if(language.kalba == 0) {
            adapterDalykas = ArrayAdapter.createFromResource(this, R.array.Dalykas, R.layout.spinner_item);
        }
        if(language.kalba == 1) {
            adapterDalykas = ArrayAdapter.createFromResource(this, R.array.DalykasEN, R.layout.spinner_item);
        }
        adapterDalykas.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner3.setAdapter(adapterDalykas);


        Button ieskoti = findViewById(R.id.ieskoti);
        btnLogout = findViewById(R.id.atsijungti);

        // atsijungimas
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Kursopasirinkimas.this, secondactivity.class));
            }
        });
        //loadDataSC();

        TextView dalykas = findViewById(R.id.Dalykas);
        TextView metai = findViewById(R.id.Metai);
        TextView grupe = findViewById(R.id.Grupe);


        if(language.kalba == 0) {
            dalykas.setText("Dalykas");
            metai.setText("Metai");
            grupe.setText("Grupė");
            btnLogout.setText("Atsijungti");
            ieskoti.setText("Nustatyti");
        }
        if(language.kalba == 1) {
            dalykas.setText("Bachelor's");
            metai.setText("Year");
            grupe.setText("Group");
            btnLogout.setText("Log out");
            ieskoti.setText("Apply");
        }



        if (language.nustatyta.equals("0")) {

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
                    language.kursas = spinner.getSelectedItem().toString();
                    //Toast.makeText(parent.getContext(),  language.kursas, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {

                    language.grupe = spinner2.getSelectedItem().toString();
                    //Toast.makeText(parent.getContext(),  language.grupe, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);

                    String temp = spinner3.getSelectedItem().toString();
                    if (temp.equals("Bioinformatika"))
                        language.dalykas = "1";
                    if (temp.equals("Duomenų mokslas"))
                        language.dalykas = "2";
                    if (temp.equals("Ekonometrija"))
                        language.dalykas = "3";
                    if (temp.equals("Finansų ir draudimo matematika"))
                        language.dalykas = "4";
                    if (temp.equals("Informacinių sistemų inžinerija"))
                        language.dalykas = "6";
                    if (temp.equals("Informacinės technologijos"))
                        language.dalykas = "5";
                    if (temp.equals("Informatika"))
                        language.dalykas = "7";
                    if (temp.equals("Programų sistemos"))
                        language.dalykas = "10";
                    //Toast.makeText(parent.getContext(), temp+ " " + language.dalykas, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
            ieskoti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveDataSC();
                    fetchData process = new fetchData();
                    process.execute();
                    Intent intent = new Intent(Kursopasirinkimas.this, scheduleActivity.class);
                    startActivity(intent);
                }
            });

        }
        else
        {
            fetchData process = new fetchData();
            process.execute();
            Intent intent = new Intent(Kursopasirinkimas.this, scheduleActivity.class);
            startActivity(intent);
        }
    }
    public void saveDataSC() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFSS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(language.kursas,language.kursas);
        editor.putString(language.grupe,language.grupe);
        editor.putString(language.dalykas,language.dalykas);
        editor.putString(language.nustatyta,"1");
        editor.apply();
        Toast.makeText(this,"Nustatymai pakeisti", Toast.LENGTH_SHORT).show();
    }

    /*public void loadDataSC() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFSS, MODE_PRIVATE);
        language.dalykas = sharedPreferences.getString(language.dalykas, "1");
        language.kursas = sharedPreferences.getString(language.kursas, "1");
        language.grupe = sharedPreferences.getString(language.dalykas, "1");
        language.nustatyta = sharedPreferences.getString(language.nustatyta, "0");
    } */

    @Override
    public void onBackPressed(){}

}
