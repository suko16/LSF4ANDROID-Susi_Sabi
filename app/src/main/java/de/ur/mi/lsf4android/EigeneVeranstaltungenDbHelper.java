//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

/**
 * Created by Sabi on 25.04.2017.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EigeneVeranstaltungenDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = EigeneVeranstaltungenDbHelper.class.getSimpleName();

    public static final String DB_NAME = "eigene_Veranstaltungen_db_new.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_EIGENE_VERANSTALTUNGEN = "eigeneVeranstaltungenTable_new";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "Nummer";
    public static final String COLUMN_TITEL = "Titel";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_EIGENE_VERANSTALTUNGEN +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NUMBER + " TEXT NOT NULL, "+
                    COLUMN_TITEL + " TEXT NOT NULL);";




    public EigeneVeranstaltungenDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) { //tabelle anlegen
        try {

            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}