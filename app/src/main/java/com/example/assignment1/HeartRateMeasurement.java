package com.example.assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class HeartRateMeasurement extends Activity {

    Uri videoUri;
    int heartRate;

    GraphView graphHeart;
    LineGraphSeries<DataPoint> seriesHeart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_measurement);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        dispatchTakeVideoIntent();
        seriesHeart = new LineGraphSeries<>();
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Log.v("hello", "hello");
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.v("hello", "hello");
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = intent.getData();
            HearRateCalculation hearRateCalculation = new HearRateCalculation();
            hearRateCalculation.execute();
        }
    }

    public class HearRateCalculation extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(getApplicationContext(), videoUri);
            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), videoUri);
            int duration = mediaPlayer.getDuration();
            heartRate = 0;
            float previousIntensity = 0;
            float[] delta = new float[400];
            int heartRateIndex = 0;
            int peakMax = 70;
            for (long i = 4; i <= duration * 1000; i += 1000000 / 4) {

                Bitmap bitmap = retriever.getFrameAtTime(i, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                Bitmap modifiedBitmap = bitmap;
                //Bitmap modifiedBitmap = Bitmap.createScaledBitmap(bitmap, 108, 192, false);
                float intensity = 0;
                // traverse all the pixels of the bitmap
                int m = 0;
                int n = 0;
                while(m < modifiedBitmap.getWidth()/2){
                    while(n < modifiedBitmap.getHeight()/2){

                        intensity += Color.red(modifiedBitmap.getPixel(m, n));
                        n++;
                    }
                    m++;
                }
                //float differenceInIntensity =
                if(heartRateIndex>0) {
                    delta[heartRateIndex] = Math.abs(previousIntensity - intensity);
                    if (delta[heartRateIndex-1] < peakMax && delta[heartRateIndex] > peakMax)
                        heartRate++;
                } else
                     delta[heartRateIndex] = 0;

                heartRateIndex++;
                previousIntensity = intensity;
                Log.v("avg intensity:", String.valueOf(intensity));
            }

            heartRate = (60*heartRate*1000)/duration;
            Log.v("heart rate:", String.valueOf(heartRate));
            retriever.release();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.app_hr_identifier), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Toast.makeText(getApplicationContext(), "Heart Rate:" + heartRate,
                    Toast.LENGTH_SHORT).show();
            editor.putInt(getString(R.string.app_hr_identifier), heartRate);
            editor.apply();
        }
    }
}

