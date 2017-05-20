package de.ur.mi.lsf4android;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;



public class Own_Courses_DataSource {


    private SQLiteDatabase database;
    private Own_Courses_DbHelper dbHelper;
    private Cursor cursor;


    private String[] columns = {
            Own_Courses_DbHelper.COLUMN_ID,
            Own_Courses_DbHelper.COLUMN_NUMBER,
            Own_Courses_DbHelper.COLUMN_TITEL,
            Own_Courses_DbHelper.COLUMN_HTML
    };




    public Own_Courses_DataSource(Context context) {
        dbHelper = new Own_Courses_DbHelper(context);
    }



    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public Course createVeranstaltung(String titel, String number, String html) {
        ContentValues values = new ContentValues();
        values.put(Own_Courses_DbHelper.COLUMN_TITEL, titel);
        values.put(Own_Courses_DbHelper.COLUMN_NUMBER, number);
        values.put(Own_Courses_DbHelper.COLUMN_HTML, html);

        open();


        long insertId = database.insert(Own_Courses_DbHelper.TABLE_OWN_COURSES, null, values);

        Cursor cursor = database.query(Own_Courses_DbHelper.TABLE_OWN_COURSES,
                columns, Own_Courses_DbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Course course = cursorToCourse(cursor);
        cursor.close();
        return course;
    }


    public void deleteCourse(Course course) {
        long id = course.getId();

        database.delete(Own_Courses_DbHelper.TABLE_OWN_COURSES,
                Own_Courses_DbHelper.COLUMN_ID + "=" + id,
                null);

    }


    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> Courselist = new ArrayList<>();

        cursor = database.query(Own_Courses_DbHelper.TABLE_OWN_COURSES,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        Course course;


        while(!cursor.isAfterLast()) {
            course = cursorToCourse(cursor);
            Courselist.add(course);
            cursor.moveToNext();
        }

        cursor.close();

        return Courselist;
    }


    private Course cursorToCourse(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Own_Courses_DbHelper.COLUMN_ID);
        int idNumber = cursor.getColumnIndex(Own_Courses_DbHelper.COLUMN_NUMBER);
        int idTitel = cursor.getColumnIndex(Own_Courses_DbHelper.COLUMN_TITEL);
        int idHtml = cursor.getColumnIndex(Own_Courses_DbHelper.COLUMN_HTML);

        String title = cursor.getString(idTitel);
        String number = cursor.getString(idNumber);
        long id = cursor.getLong(idIndex);
        String html = cursor.getString(idHtml);

        Course course = new Course(title, number, id, html);

        return course;
    }
}