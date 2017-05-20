package de.ur.mi.lsf4android;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BackgroundService extends IntentService {


    private String date;
    private int countNotifications=0;
    private ArrayList<String[]> remindNotifications;


    public BackgroundService(String name){
        super(name);
    }

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    public void onCreate() {
        remindNotifications = new ArrayList<>();
    }

    @Override
    protected void onHandleIntent(Intent intent) {}

    //called via StartService in MainActivity to download the cancelled courses from LSF
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        downLoadCancelledLectures();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
    }


    private void downLoadCancelledLectures() {
        //set the current date for the first download
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH)+1; // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        date = mDay + "." + mMonth + "." + mYear;

        //change the date to get the cancelled courses of the next 3 days
        for(int k=3; k>0; k--){
            String url = "https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=" + date + "&asi=";
            new DownloadLSFTask().execute(url);
            c.add(Calendar.DATE, 1);
            int newDay =c.get(Calendar.DAY_OF_MONTH);
            int newMonth = c.get(Calendar.MONTH)+1;
            int newYear = c.get(Calendar.YEAR);
            date = newDay + "." + newMonth + "." + newYear;

        }
    }


        //download via AsyncTask in the background, returning an Arraylist with Stringarray
    private class DownloadLSFTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        String saveDate = date;

        protected ArrayList<String[]> doInBackground(String... urls) {
            ArrayList<String[]> result = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Element table = doc.select("table").last();
                Elements rows = table.select("tr");

                for (Element row : rows) {
                    Elements columns = row.select("td");
                    int i = 0;
                    for (Element column : columns) {
                        switch (i) {
                            case 2:
                                String[] NumberAndDate = new String[2];
                                NumberAndDate[0] = column.text();
                                NumberAndDate[1] = saveDate;
                                result.add(NumberAndDate);

                                break;
                        }
                        i++;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                return result;
            }

        }


        protected void onPostExecute(ArrayList<String[]> result) {
            checkifMatching(result);
        }
    }

    //the method checks if one of the cancelled courses matches with one of the entries in the database
    private void checkifMatching(ArrayList<String[]> cancelled_Courses_ArrayList){
        Own_Courses_DataSource dataSource = new Own_Courses_DataSource(this);
        dataSource.open();
        List<Course> coursesDB = dataSource.getAllCourses();
        for (int j = 0; j < coursesDB.size(); j++) {
            for (int i = 0; i < cancelled_Courses_ArrayList.size(); i++) {
                if (coursesDB.get(j).getNumber().equals(cancelled_Courses_ArrayList.get(i)[0])) {
                    countNotifications++;
                    sendNotification(coursesDB.get(j).getTitle(), cancelled_Courses_ArrayList.get(i)[1], countNotifications);
                }
            }
        }
        dataSource.close();
    }

    //gets called if a match is found
    //call the notificationActivity with the relevant parameters
    private void sendNotification(String title_cancelled_course, String date, int countNotifications){

        //just send a notification if the same wasn't send before and already deleted by the user
        if(remindNotifications!=null){
            for(int w=0; w<remindNotifications.size(); w++){
                //so just send a notification if different name OR date
                if(remindNotifications.get(w)[0].equals(title_cancelled_course) && remindNotifications.get(w)[1].equals(date)){
                        return;
                }
            }
        }
        //includes new notification in Arraylist
        String[] current = new String[2];
        current[0]=title_cancelled_course;
        current[1]= date;
        remindNotifications.add(current);


        //create the new notification
        NotificationActivity cN = new NotificationActivity(getApplicationContext());
        cN.createNotification(title_cancelled_course, date, countNotifications);

    }
}

