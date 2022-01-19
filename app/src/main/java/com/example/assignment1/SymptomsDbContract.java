package com.example.assignment1;

import android.provider.BaseColumns;

public final class SymptomsDbContract {

    // private constructor
    private SymptomsDbContract() {
    }

    public static class SymptomsTable implements BaseColumns {
        public static final String TABLE_NAME = "symptoms";
        public static final String COLUMN_RESPIRATORY_RATE = "respiratory_rate";
        public static final String COLUMN_NAUSEA = "nausea";
        public static final String COLUMN_HEADACHE = "headache";
        public static final String COLUMN_DIARRHEA = "diarrhea";
        public static final String COLUMN_HEART_RATE = "heart_rate";
        public static final String COLUMN_SOAR_THROAT = "soar_throat";
        public static final String COLUMN_FEVER = "fever";
        public static final String COLUMN_MUSCLE_ACHE = "muscle_ache";
        public static final String COLUMN_SMELL_TASTE = "smell_taste";
        public static final String COLUMN_COUGH = "cough";
        public static final String COLUMN_BREATH = "breath";
        public static final String COLUMN_FEELING_TIRED = "feeling_tired";
    }
}

