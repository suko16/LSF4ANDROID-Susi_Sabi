package de.ur.mi.lsf4android;

import android.app.IntentService;
import android.app.Service;
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

// TODO: Quelle einfügen: https://developer.android.com/guide/components/services.html#Notifications

public class BackgroundService extends IntentService {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public ArrayList<String> result;

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    public void onCreate() {

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        downLoadCancelledLectures();
    }


    //onStartCommand nie direkt aufrufen! -> Intent zu startService()
    // Intent intent = new Intent(this, BackgroundService.class); startService(intent);
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "bsp", Toast.LENGTH_LONG).show();
        new DownloadLSFTask().execute("https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=04.05.2017&asi=");

        return Service.START_NOT_STICKY; //legt Verhalten nach Beendigung  des Services durch das System fest zB Service.START_NOT_STICKY
    }

    @Override
    public void onDestroy() {
    }


    public void downLoadCancelledLectures() {
        new DownloadLSFTask().execute("https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=04.05.2017&asi=");

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
                            case 3:
                                String titel = column.select("a").text();
                                result.add(titel);

                                break;
                        }
                        i++;
                    }

                }

                for(int i=0; i<result.size(); i++){
                    Log.d(LOG_TAG, result.get(i));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }
    }
}