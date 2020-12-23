package com.example.tvarkarastis;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.widget.Toast;

import com.example.tvarkarastis.data.language;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button Transition;
    private int kalba;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final int KALBA = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.pradeti);
        Button langButtonLT = findViewById(R.id.ltLang);
        Button langButtonEN = findViewById(R.id.enLang);

        loadData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, secondactivity.class);
                startActivity(intent);
            }
        });

        if(language.kalba == 0) {
            button.setText("Pradėkime!");
        }
        if(language.kalba == 1) {
            button.setText("Let's start!");
        }


        langButtonEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Let's start!");
                saveDataEN();
                language.kalba=1;
            }
        });
        langButtonLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Pradėkime!");
                saveData();
                language.kalba=0;
            }
        });

    }

    public void saveDataEN() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(String.valueOf(KALBA), 1);

        editor.apply();
        Toast.makeText(this, "Changes applied", Toast.LENGTH_SHORT).show();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putInt(String.valueOf(KALBA), 0);

        editor.apply();
        Toast.makeText(this,"Nustatymai pakeisti", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        language.kalba = sharedPreferences.getInt(String.valueOf(KALBA), 1);

    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}