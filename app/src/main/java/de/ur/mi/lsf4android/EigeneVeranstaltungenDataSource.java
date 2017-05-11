//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

/**
 * Created by Sabi on 25.04.2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class EigeneVeranstaltungenDataSource {

    private static final String LOG_TAG = EigeneVeranstaltungenDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private EigeneVeranstaltungenDbHelper dbHelper;
    private Cursor cursor;


    private String[] columns = {
            EigeneVeranstaltungenDbHelper.COLUMN_ID,
            EigeneVeranstaltungenDbHelper.COLUMN_NUMBER,
            EigeneVeranstaltungenDbHelper.COLUMN_TITEL,


    };



    public EigeneVeranstaltungenDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new EigeneVeranstaltungenDbHelper(context);
    }



    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }


    public EigeneV_Objekt createVeranstaltung(String titel, String number) {
        ContentValues values = new ContentValues();
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_TITEL, titel);
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_NUMBER, number);

        open();


        long insertId = database.insert(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN, null, values);

        Cursor cursor = database.query(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                columns, EigeneVeranstaltungenDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        EigeneV_Objekt veranstaltung = cursorToVeranstaltung(cursor);


        cursor.close();

        return veranstaltung;
    }


    public void deleteVeranstaltung(EigeneV_Objekt veranstaltung) {
        long id = veranstaltung.getId();

        database.delete(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                EigeneVeranstaltungenDbHelper.COLUMN_ID + "=" + id,
                null);

    }



    public List<EigeneV_Objekt> getAllVeranstaltungen() {
        List<EigeneV_Objekt> Veranstaltungsliste = new ArrayList<>();

        cursor = database.query(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        EigeneV_Objekt veranstaltung;


        while(!cursor.isAfterLast()) {
            veranstaltung = cursorToVeranstaltung(cursor);
            Veranstaltungsliste.add(veranstaltung);
            cursor.moveToNext();
        }

        cursor.close();

        return Veranstaltungsliste;
    }




    private EigeneV_Objekt cursorToVeranstaltung(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_ID);
        int idNumber = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_NUMBER);
        int idTitel = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_TITEL);

        String titel = cursor.getString(idTitel);
        String number = cursor.getString(idNumber);
        long id = cursor.getLong(idIndex);

        EigeneV_Objekt veranstaltung = new EigeneV_Objekt(titel, number, id);

        return veranstaltung;
    }




      /*  public EigeneV_Objekt updateID (EigeneV_Objekt veranstaltung, int newIndex) {

        ContentValues values = new ContentValues();
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_BEGINN, veranstaltung.getBeginn());
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_ENDE, veranstaltung.getEnde());
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_TITEL, veranstaltung.getTitel());

        database.update(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                values,
                EigeneVeranstaltungenDbHelper.COLUMN_ID + "=" + newIndex,
                null);
        Log.d(LOG_TAG, "Eintrag gändert! ID: " + veranstaltung.getId() + " Inhalt: " + veranstaltung.toString());


        Cursor cursor = database.query(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                columns, EigeneVeranstaltungenDbHelper.COLUMN_ID + "=" + newIndex,
                null, null, null, null);

        cursor.moveToFirst();
        EigeneV_Objekt v = cursorToVeranstaltung(cursor);
        cursor.close();

       return veranstaltung;
    }

*/
}