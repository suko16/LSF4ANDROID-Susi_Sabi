package de.ur.mi.lsf4android;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class Own_Courses_Activity extends NavigationActivity {

//TODO: wenn noch zeit nochmal versuchen (manifest, layout und main ändern), wenn nicht löschen
    private Own_Courses_DataSource dataSource;
    private ListView own_Courses_ListView;
    private TextView number;
    private TextView title;
    private  Context context =Own_Courses_Activity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstraintLayout contentConstraintLayout = (ConstraintLayout) findViewById(R.id.fragment_content_navigation); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.own_courses_fragment, contentConstraintLayout);
        number = (TextView) findViewById(R.id.own_courses_fragment_number);
        title = (TextView) findViewById(R.id.own_courses_fragment_title);
        own_Courses_ListView = (ListView) findViewById(R.id.own_courses_fragment_listView);
        dataSource = new Own_Courses_DataSource(Own_Courses_Activity.this);
    }

    private void showAllListEntries() {

        number.setText(R.string.number);
        title.setText(R.string.title);
        if (context != null) {
            dataSource.open();
            ArrayList<Course> courseListDB = dataSource.getAllCourses();
            Own_Courses_ArrayAdapter adapter = new Own_Courses_ArrayAdapter(context, courseListDB);
            own_Courses_ListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            /*own_Courses_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long ld) {

                    Course selectedCourse = (Course) own_Courses_ListView.getItemAtPosition(position);
                    dataSource.deleteCourse(selectedCourse);
                    CharSequence text = selectedCourse.getTitle() + " wurde gelöscht";
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                    toast.show();
                }
            });*/

            adapter.notifyDataSetChanged();


            //Abgleich mit Downloadergebnis aus BackgroundService
            //Problem: result = null
          /* BackgroundService bS = new BackgroundService();
            if(bS.result != null) {
                arrayListAusfallende_Number = new ArrayList<>();
                for (int i = 1; i < bS.result.size(); i++) {
                    arrayListAusfallende_Number.add(bS.result.get(i));
                }
            }
            if(ArrayListAusfallendeVeranstaltungen!= null && courseListDB != null){
                for(int i=0; i<courseListDB.size(); i++){
                    for(int j = 0; j< arrayListAusfallende_Number.size(); j++){
                        if(courseListDB.get(i).getNumber().equals(arrayListAusfallende_Number.get(j))){
                            own_Courses_ListView.getChildAt(i).setBackgroundColor(Color.GREEN);
                        }
                    }
                }
            }*/

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        dataSource.open();
        showAllListEntries();
    }


    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
    }




    public Own_Courses_Activity() {
        // Required empty public constructor
    }

    public static Own_Courses_Activity newInstance() {
        Own_Courses_Activity ownCoursesActivity = new Own_Courses_Activity();
        return ownCoursesActivity;
    }
}

