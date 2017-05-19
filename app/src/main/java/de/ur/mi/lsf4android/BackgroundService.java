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
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




public class BackgroundService extends IntentService {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public BackgroundService() {
        super("BackgroundService");
    }
    public ArrayList<String[]> result;
    private EigeneVeranstaltungenDataSource dataSource;
    String url;
    String date;
    int countNotifications=0;
    ArrayList<String[]> remindNotifications;


    public BackgroundService(String name){
        super(name);
    }

    @Override
    public void onCreate() {

        remindNotifications = new ArrayList<>();
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }


    //onStartCommand nie direkt aufrufen! -> Intent zu startService()
    // Intent intent = new Intent(this, BackgroundService.class); startService(intent);
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        downLoadCancelledLectures();
// new DownloadLSFTask().execute("https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=04.05.2017&asi=");
        return Service.START_NOT_STICKY;//legt Verhalten nach Beendigung  des Services durch das System fest zB Service.START_NOT_STICKY onStartCommand(intent,flags,startId)
    }

    @Override
    public void onDestroy() {
    }


    public void downLoadCancelledLectures() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH)+1; // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        date = mDay + "." + mMonth + "." + mYear;

        for(int k=10; k>0; k--){
            url = "https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=" + date + "&asi=";
            new DownloadLSFTask().execute(url);
            c.add(Calendar.DATE, 1);
            int newDay =c.get(Calendar.DAY_OF_MONTH);
            int newMonth = c.get(Calendar.MONTH)+1;
            int newYear = c.get(Calendar.YEAR);
            date = newDay + "." + newMonth + "." + newYear;

        }
    }



    private class DownloadLSFTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        String saveDate = date;

        protected ArrayList<String[]> doInBackground(String... urls) {
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

            checkIfCollision(result);

        }
    }

    private void checkIfCollision(ArrayList<String[]> ArrayListAusfallendeVeranstaltungen){
        dataSource = new EigeneVeranstaltungenDataSource(this);
        dataSource.open();
        List<Veranstaltung> VeranstaltungslisteDB = dataSource.getAllVeranstaltungen();


        for (int j = 0; j < VeranstaltungslisteDB.size(); j++) {
            for (int i = 0; i < ArrayListAusfallendeVeranstaltungen.size(); i++) {
                if (VeranstaltungslisteDB.get(j).getNumber().equals(ArrayListAusfallendeVeranstaltungen.get(i)[0])) {
                    countNotifications++;
                    sendNotification(VeranstaltungslisteDB.get(j).getTitel(), ArrayListAusfallendeVeranstaltungen.get(i)[1], countNotifications);
                }
            }
        }

        dataSource.close();
    }


    private void sendNotification(String titelAusfallendeVeranstaltung, String date, int countNotifications){

        // wenn bereits über eine ausfallende Veranstaltung an einem bestimmten Tag benachrichtigt wurde, dann nicht nochmal benachrichtigen
        //(nur wenn andere Veranstaltung oder anderer Tag)
        // Damit der Nutzer nicht jeden Tag die Notification für die Benachrichtigung wegen der Veranstaltung bekommt, die in 3 Tagen ausfällt


        if(remindNotifications!=null){
            for(int w=0; w<remindNotifications.size(); w++){
                if(remindNotifications.get(w)[0].equals(titelAusfallendeVeranstaltung) && remindNotifications.get(w)[1].equals(date)){
                        return;
                }
            }
        }

        String[] current = new String[2];
        current[0]=titelAusfallendeVeranstaltung;
        current[1]= date;
        remindNotifications.add(current);


        //neue Benachrichtigung erzeugen

        CreateNotificationActivity cN = new CreateNotificationActivity(getApplicationContext());
        cN.createNotification(titelAusfallendeVeranstaltung, date, countNotifications);

    }
}

