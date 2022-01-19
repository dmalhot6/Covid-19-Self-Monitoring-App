package com.example.assignment1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static java.security.AccessController.getContext;

public class SymptomsLogging extends Activity {

    SymptomsDbHelper symptomsDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging_symptoms);

        Button btn_upload_symptoms = (Button) findViewById(R.id.button_upload_symptoms);
        RatingBar nausea = (RatingBar) findViewById(R.id.nausea);
        RatingBar headache = (RatingBar) findViewById(R.id.headache);
        RatingBar diarrhea = (RatingBar) findViewById(R.id.diarrhea);
        RatingBar soarThroat = (RatingBar) findViewById(R.id.soarThroat);
        RatingBar fever = (RatingBar) findViewById(R.id.fever);
        RatingBar muscleAche = (RatingBar) findViewById(R.id.muscleAche);
        RatingBar smellTaste = (RatingBar) findViewById(R.id.smellTaste);
        RatingBar cough = (RatingBar) findViewById(R.id.cough);
        RatingBar breath = (RatingBar) findViewById(R.id.breath);
        RatingBar feelingTired = (RatingBar) findViewById(R.id.feelingTired);

        btn_upload_symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // respiratory rate retrieval
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.app_rr_identifier), Context.MODE_PRIVATE);
                int respiratoryRate = sharedPref.getInt(getString(R.string.app_rr_identifier), 0);

                // heart rate retrieval
                sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.app_hr_identifier), Context.MODE_PRIVATE);
                int heartRate = sharedPref.getInt(getString(R.string.app_hr_identifier), 0);
                symptomsDbHelper = new SymptomsDbHelper(getApplicationContext());

                SQLiteDatabase symptomsSQLiteDb = symptomsDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_HEART_RATE, heartRate);
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_RESPIRATORY_RATE, respiratoryRate);
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_NAUSEA, nausea.getRating());
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_HEADACHE, headache.getRating());
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_DIARRHEA, diarrhea.getRating());

                values.put(SymptomsDbContract.SymptomsTable.COLUMN_SOAR_THROAT, soarThroat.getRating());
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_FEVER, fever.getRating());
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_MUSCLE_ACHE, muscleAche.getRating());
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_SMELL_TASTE, smellTaste.getRating());
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_COUGH, cough.getRating());
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_BREATH, breath.getRating());
                values.put(SymptomsDbContract.SymptomsTable.COLUMN_FEELING_TIRED, feelingTired.getRating());

                long newRowId = symptomsSQLiteDb.insert(SymptomsDbContract.SymptomsTable.TABLE_NAME, null, values);
                Toast.makeText(getApplicationContext(), "data stored successfully or not:" + newRowId, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
