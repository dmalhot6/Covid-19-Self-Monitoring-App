package com.example.assignment1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SymptomsDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Symptoms.db";

    public SymptomsDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static final String CREATE_TABLE =
            "CREATE TABLE " + SymptomsDbContract.SymptomsTable.TABLE_NAME + " (" +
                    SymptomsDbContract.SymptomsTable._ID + " INTEGER PRIMARY KEY, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_RESPIRATORY_RATE + " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_NAUSEA + " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_HEADACHE + " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_DIARRHEA + " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_HEART_RATE + " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_SOAR_THROAT+ " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_FEVER+ " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_MUSCLE_ACHE+ " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_SMELL_TASTE+ " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_COUGH+ " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_BREATH+ " TEXT, " +
                    SymptomsDbContract.SymptomsTable.COLUMN_FEELING_TIRED+ " TEXT);";
}
