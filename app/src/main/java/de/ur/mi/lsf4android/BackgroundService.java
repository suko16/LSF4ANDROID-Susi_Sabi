package de.ur.mi.lsf4android;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


// TODO: URL-Code-Anpassung für aktuelles Datum aus Ausfallende Veranstaltungen von Susi übernehmen
//TODO: Überprüfung auf nächsten 3 Tage

public class BackgroundService extends IntentService {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public BackgroundService() {
        super("BackgroundService");
    }
    ArrayList<String> result;
    private EigeneVeranstaltungenDataSource dataSource;


    @Override
    public void onCreate() {

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // soll den Download übernehmen, aber wie aufrufen, mit welchen parametern?

    }


    //onStartCommand nie direkt aufrufen! -> Intent zu startService()
    // Intent intent = new Intent(this, BackgroundService.class); startService(intent);
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        new DownloadLSFTask().execute("https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=04.05.2017&asi=");
        downLoadCancelledLectures();
        return Service.START_NOT_STICKY; //legt Verhalten nach Beendigung  des Services durch das System fest zB Service.START_NOT_STICKY
    }

    @Override
    public void onDestroy() {
    }


    public void downLoadCancelledLectures() {
        new DownloadLSFTask().execute("https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=29.05.2017&&HISCalendar_Date=30.05.2017&&HISCalendar_Date=26.05.2017&&HISCalendar_Date=19.05.2017&&HISCalendar_Date=18.05.2017&asi=");

    }



    private class DownloadLSFTask extends AsyncTask<String, Integer, ArrayList<String>> {
        protected ArrayList<String> doInBackground(String... urls) {
           result = new ArrayList<>();
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
                                result.add(column.text());
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

        protected void onPostExecute(ArrayList<String> result) {
            checkIfCollision(result);
        }
    }

    private void checkIfCollision(ArrayList<String> VeranstaltungsArray){
        dataSource = new EigeneVeranstaltungenDataSource(this);
        dataSource.open();
        List<Veranstaltung> Veranstaltungsliste = dataSource.getAllVeranstaltungen();

        for (int j = 0; j < Veranstaltungsliste.size(); j++){
            for(int i=0; i<VeranstaltungsArray.size(); i++){
                if(Veranstaltungsliste.get(j).getNumber().equals(VeranstaltungsArray.get(i))) {
                    sendNotification(Veranstaltungsliste.get(j).getTitel());
                }
            }
        }
    }


    private void sendNotification(String titelAusfallendeVeranstaltung){
        CreateNotificationActivity cN = new CreateNotificationActivity(getApplicationContext());
        cN.createNotification(titelAusfallendeVeranstaltung);
    }
}