package com.orchtech.assem.rxrecap;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.orchtech.assem.rxrecap.basic_examples.BasicExamplesActivity;
import com.orchtech.assem.rxrecap.basic_examples.OperatorsExampleActivity;
import com.orchtech.assem.rxrecap.fligh_app.view.FlightAppActivity;
import com.orchtech.assem.rxrecap.notes_app.view.NotesAppActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.basic_examples).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BasicExamplesActivity.class)));
        findViewById(R.id.operators_examples).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OperatorsExampleActivity.class)));
        findViewById(R.id.notes_app).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NotesAppActivity.class)));
        findViewById(R.id.flights_app).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FlightAppActivity.class)));
    }
}
