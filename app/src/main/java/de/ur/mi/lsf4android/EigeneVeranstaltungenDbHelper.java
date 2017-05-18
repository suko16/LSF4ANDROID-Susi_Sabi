//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EigeneVeranstaltungenDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = EigeneVeranstaltungenDbHelper.class.getSimpleName();

    public static final String DB_NAME = "dbEeigeneVeranstaltungen.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_EIGENE_VERANSTALTUNGEN = "TabelleDerEigenenVeranstaltungen";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "Nummer";
    public static final String COLUMN_TITEL = "Titel";
    public static final String COLUMN_HTML = "HTML";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_EIGENE_VERANSTALTUNGEN +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NUMBER + " TEXT NOT NULL, "+
    COLUMN_TITEL + " TEXT NOT NULL, " + COLUMN_HTML + " TEXT NOT NULL);";




    public EigeneVeranstaltungenDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) { //tabelle anlegen
        try {

            db.execSQL(SQL_CREATE_TABLE);
        }
        catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}