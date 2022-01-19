package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_measure_resp = (Button) findViewById(R.id.button_resp);
        btn_measure_resp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent measureRespiratoryRate = new Intent(getApplicationContext()
                        , RespiratoryRateMeasurement.class);
                startActivity(measureRespiratoryRate);
            }
        });

        Button btn_symptoms_logging = (Button) findViewById(R.id.button_symptom);
        btn_symptoms_logging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent symptomsLogging = new Intent(getApplicationContext(), SymptomsLogging.class);
                startActivity(symptomsLogging);
            }
        });

        Button btn_measure_heart_rate = (Button) findViewById(R.id.heart_rate_measurement);
        btn_measure_heart_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent measureHeartRate = new Intent(getApplicationContext(), HeartRateMeasurement.class);
                startActivity(measureHeartRate);
            }
        });
    }
}