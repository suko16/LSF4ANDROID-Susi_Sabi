package de.ur.mi.lsf4android;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class Cancelled_Courses_Activity extends NavigationActivity {
    private TextView title;
    private TextView begin;
    private TextView end;
    private TextView number;
    private ListView list;
    private Cancelled_Courses_ArrayAdapter adapter;
    private ArrayList<String> htmlList;
    private ArrayList<String[]> result;
    private DatePickerDialog datePickerDialog;
    private Button datePickerButton;
    private TextView dateTextView;
    private  Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstraintLayout contentConstraintLayout = (ConstraintLayout) findViewById(R.id.fragment_content_navigation);
        getLayoutInflater().inflate(R.layout.cancelled_courses_activity, contentConstraintLayout);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String url = "https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=" + date + "&asi=";
        new DownloadLSFTask().execute(url);
        list = (ListView) findViewById(R.id.cancelled_courses_listView);
        datePickerButton = (Button) findViewById(R.id.button_date_picker);
        dateTextView = (TextView) findViewById(R.id.textView_date);
        dateTextView.setText(date);
        title = (TextView) findViewById(R.id.cancelled_courses_title);
        begin = (TextView) findViewById(R.id.cancelled_courses_begin);
        number = (TextView) findViewById(R.id.cancelled_courses_number);
        end = (TextView) findViewById(R.id.cancelled_courses_end);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Cancelled_Courses_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                datePickerButton.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                               String newDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                                Intent reloadIntent = new Intent(Cancelled_Courses_Activity.this,Cancelled_Courses_Activity.class);
                                reloadIntent.putExtra("date", newDate);
                                Cancelled_Courses_Activity.this.finish();
                                startActivity(reloadIntent);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    //passes to the single view version of the special activity
    private void callSingeViewActivity(String title, int j) {
        Intent intent = new Intent(this, Single_View_Fragment_Start.class);
        intent.putExtra("titel", title);
        intent.putExtra("html", htmlList.get(j));
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    //downloads the cancelled courses from LSF
    private class DownloadLSFTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        protected ArrayList<String[]> doInBackground(String... urls) {
            result = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Element table = doc.select("table").last();
                Elements rows = table.select("tr");
                htmlList = new ArrayList<>();
                for (Element row : rows) {
                    String[] string_row = new String[5];
                    Elements columns = row.select("td");
                    int i = 0;
                    for (Element column : columns) {
                        switch (i) {
                            case 0:
                                string_row[0] = column.text();
                                break;
                            case 1:
                                string_row[1] = column.text();
                                break;
                            case 2:
                                string_row[2] = column.text();
                                break;
                            case 3:
                                string_row[3] = column.select("a").text();

                                //get URL from titel
                                Element link = column.select("a").first();
                                string_row[4] = link.attr("href");
                                break;
                        }
                        i++;
                    }
                    result.add(string_row);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }

        protected void onPostExecute(ArrayList<String[]> result) {
            //insert captions for the columns
            begin.setText(R.string.begin);
            end.setText(R.string.end);
            number.setText(R.string.number);
            title.setText(R.string.title);

            //filling an ArrayList with courses and set the adapter
            if (result.size() != 0) {
                final ArrayList<Course> courseArrayList = new ArrayList<Course>();
                for (int i = 1; i < result.size(); i++) {
                    courseArrayList.add(new Course(result.get(i)[0], result.get(i)[1], result.get(i)[2], result.get(i)[3], result.get(i)[4]));
                }
                context = Cancelled_Courses_Activity.this;
                if (context != null) {
                    adapter = new Cancelled_Courses_ArrayAdapter(context, courseArrayList);
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != 0) {
                                callSingeViewActivity(courseArrayList.get(i).getTitle(), i - 1);
                            }
                        }
                    });
                }

            }
        }
    }
}
