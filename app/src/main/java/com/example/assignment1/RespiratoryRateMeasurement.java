package com.example.assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class RespiratoryRateMeasurement extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float previousZCoordinate = 0;
    private int respRate = 0;
    long startTime;
    int flag = 1;
    float previousChange = 0;
    GraphView graph;
    LineGraphSeries<DataPoint> series;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.respiratory_measurement);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        startTime = System.currentTimeMillis();
        previousChange = 0;
        flag = -1;


//        graph = (GraphView) findViewById(R.id.graph);
//        series = new LineGraphSeries<>();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

//        float xCoordinate = event.values[0];
//        float yCoordinate = event.values[1];
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

//            double y;
//            long x = System.currentTimeMillis();
//            y = event.values[2];
//            series.appendData(new DataPoint(x/1000,y), true, 1000);

            float zCoordinate = event.values[2];
            float changeInHeight = Math.abs(zCoordinate - previousZCoordinate);
            //Toast.makeText(getApplicationContext(), "Respiratory rate calculate:"+zCoordinate, Toast.LENGTH_SHORT).show();
            TextView currentZ = (TextView) findViewById(R.id.zcoordinate);
           // TextView previousZ = (TextView) findViewById(R.id.previousZ);
            //TextView heightDiff = (TextView) findViewById(R.id.heightdiff);
//            previousZ.setText(String.valueOf(previousZCoordinate));
//            currentZ.setText(String.valueOf(zCoordinate));
//            heightDiff.setText(String.valueOf(changeInHeight));

            previousZCoordinate = zCoordinate;

            if (changeInHeight >= 0.0255) {
                respRate++;
            }
            flag = 1;

            previousChange = changeInHeight;
            long currTime = System.currentTimeMillis();

            if (currTime - startTime >= 60000) {

                sensorManager.unregisterListener(this);
                respRate = respRate / 2;
                TextView rr = (TextView) findViewById(R.id.resprate);
                rr.setText(String.valueOf(respRate));
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.app_rr_identifier), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
//                Toast.makeText(getApplicationContext(), "Respiratory Rate:" + respRate,
//                        Toast.LENGTH_SHORT).show();
                editor.putInt(getString(R.string.app_rr_identifier), respRate);
                editor.apply();
                //graph.addSeries(series);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
