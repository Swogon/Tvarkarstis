package com.example.tvarkarastis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class scheduleActivity extends AppCompatActivity {

    Button click;
    public static TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_schedule);

        click = (Button) findViewById(R.id.refresh);
        data = (TextView) findViewById(R.id.fetchedData);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                fetchData process = new fetchData();
                process.execute();
            }
        });

    }
}