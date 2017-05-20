
package de.ur.mi.lsf4android;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Own_Courses_DbHelper extends SQLiteOpenHelper{


    private static final String DB_NAME = "dbEeigeneVeranstaltungen.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_OWN_COURSES = "TabelleDerEigenenVeranstaltungen";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "Nummer";
    public static final String COLUMN_TITEL = "Titel";
    public static final String COLUMN_HTML = "HTML";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_OWN_COURSES +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NUMBER + " TEXT NOT NULL, "+
    COLUMN_TITEL + " TEXT NOT NULL, " + COLUMN_HTML + " TEXT NOT NULL);";



    //constructor
    public Own_Courses_DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_TABLE);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}