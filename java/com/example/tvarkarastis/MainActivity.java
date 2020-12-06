package com.example.tvarkarastis;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Window;

import com.example.tvarkarastis.data.language;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button Transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.pradeti);
        Button langButtonLT = findViewById(R.id.ltLang);
        Button langButtonEN = findViewById(R.id.enLang);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, secondactivity.class);
                startActivity(intent);
            }
        });
        langButtonEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Let's start!");
                language.kalba=1;
            }
        });
        langButtonLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Pradėkime!");
                language.kalba=0;
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}