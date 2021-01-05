package com.example.tvarkarastis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvarkarastis.data.preferences;

import java.util.Objects;

public class scheduleActivity extends AppCompatActivity {
    ImageView imgBtn;
    Button click;
    public static final String SHARED_PREFSS = "sharedPrefss";
    public static TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_schedule);

        click = (Button) findViewById(R.id.refresh);
        data = (TextView) findViewById(R.id.fetchedData);
        imgBtn = (ImageView) findViewById(R.id.imageButton);
        //Toast.makeText(this,language.kursas + " " + language.grupe + " " + language.dalykas , Toast.LENGTH_SHORT).show();

        if(preferences.kalba == 0) {
            click.setText("Atnaujinti");
        }
        if(preferences.kalba == 1) {
            click.setText("Refresh");
        }


        imgBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               saveDataSC();
               //loadDataSC();
               Intent intent = new Intent(scheduleActivity.this, Kursopasirinkimas.class);
               startActivity(intent);
           }

        });



        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                fetchData process = new fetchData();
                process.execute();
            }
        });

    }



    public void saveDataSC() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFSS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferences.kursas, preferences.kursas);
        editor.putString(preferences.grupe, preferences.grupe);
        editor.putString(preferences.dalykas, preferences.dalykas);
        editor.putString(preferences.nustatyta,"1");
        editor.apply();
        //Toast.makeText(this,"Nustatymai pakeisti", Toast.LENGTH_SHORT).show();
    }

}