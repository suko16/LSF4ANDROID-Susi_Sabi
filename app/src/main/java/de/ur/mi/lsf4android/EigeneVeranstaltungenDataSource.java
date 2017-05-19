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
            EigeneVeranstaltungenDbHelper.COLUMN_HTML


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


    public Veranstaltung createVeranstaltung(String titel, String number, String html) {
        ContentValues values = new ContentValues();
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_TITEL, titel);
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_NUMBER, number);
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_HTML, html);

        open();


        long insertId = database.insert(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN, null, values);

        Cursor cursor = database.query(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                columns, EigeneVeranstaltungenDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Veranstaltung veranstaltung = cursorToVeranstaltung(cursor);


        cursor.close();

        return veranstaltung;
    }

   /* public EigeneV_Objekt createVeranstaltung(String titel, String number, String html) {
        ContentValues values = new ContentValues();
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_TITEL, titel);
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_NUMBER, number);
        values.put(EigeneVeranstaltungenDbHelper.COLUMN_HTML, html);

        open();


        long insertId = database.insert(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN, null, values);

        Cursor cursor = database.query(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                columns, EigeneVeranstaltungenDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        EigeneV_Objekt veranstaltung = cursorToVeranstaltung(cursor);


        cursor.close();

        return veranstaltung;
    }*/


    public void deleteVeranstaltung(Veranstaltung veranstaltung) {
        long id = veranstaltung.getId();

        database.delete(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                EigeneVeranstaltungenDbHelper.COLUMN_ID + "=" + id,
                null);

    }


    public ArrayList<Veranstaltung> getAllVeranstaltungen() {
        ArrayList<Veranstaltung> Veranstaltungsliste = new ArrayList<>(); //List?

        cursor = database.query(EigeneVeranstaltungenDbHelper.TABLE_EIGENE_VERANSTALTUNGEN,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        Veranstaltung veranstaltung;


        while(!cursor.isAfterLast()) {
            veranstaltung = cursorToVeranstaltung(cursor);
            Veranstaltungsliste.add(veranstaltung);
            cursor.moveToNext();
        }

        cursor.close();

        return Veranstaltungsliste;
    }

    /*public ArrayList<EigeneV_Objekt> getAllVeranstaltungen() {
        ArrayList<EigeneV_Objekt> Veranstaltungsliste = new ArrayList<>(); //List?

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
    }*/


    private Veranstaltung cursorToVeranstaltung(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_ID);
        int idNumber = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_NUMBER);
        int idTitel = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_TITEL);
        int idHtml = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_HTML);

        String titel = cursor.getString(idTitel);
        String number = cursor.getString(idNumber);
        long id = cursor.getLong(idIndex);
        String html = cursor.getString(idHtml);

        Veranstaltung veranstaltung = new Veranstaltung(titel, number, id, html);

        return veranstaltung;
    }

    /*private EigeneV_Objekt cursorToVeranstaltung(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_ID);
        int idNumber = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_NUMBER);
        int idTitel = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_TITEL);
        int idHtml = cursor.getColumnIndex(EigeneVeranstaltungenDbHelper.COLUMN_HTML);

        String titel = cursor.getString(idTitel);
        String number = cursor.getString(idNumber);
        long id = cursor.getLong(idIndex);
        String html = cursor.getString(idHtml);

        EigeneV_Objekt veranstaltung = new EigeneV_Objekt(titel, number, id, html);

        return veranstaltung;
    }*/




}