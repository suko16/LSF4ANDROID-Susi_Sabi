
package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import android.widget.TextView;
import android.widget.Toast;


public class Own_Courses_Fragment extends android.support.v4.app.Fragment implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {
    private Own_Courses_DataSource dataSource;
    private ListView own_Courses_ListView;
    private TextView number;
    private TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.own_courses_fragment, container, false);
        number = (TextView) view.findViewById(R.id.own_courses_fragment_number);
        title = (TextView) view.findViewById(R.id.own_courses_fragment_title);
        own_Courses_ListView = (ListView) view.findViewById(R.id.own_courses_fragment_listView);
        dataSource = new Own_Courses_DataSource(getActivity());
        own_Courses_ListView.setOnItemClickListener(this);
        own_Courses_ListView.setOnItemLongClickListener(this);
        return view;
    }


    private void showAllListEntries() {

        number.setText(R.string.number);
        title.setText(R.string.title);
        Context context = getActivity();
        if (context != null) {
            ArrayList<Course> courseListDB = dataSource.getAllCourses();
            Own_Courses_ArrayAdapter adapter = new Own_Courses_ArrayAdapter(context, courseListDB);
            own_Courses_ListView.setAdapter(adapter);

            //TODO: wenn ich noch zeit hab lösung findne sonst löschen!!
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

    //open the single view if the user clicks at the course
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int positionListView = (int)id;
        Course selectedCourse = (Course) own_Courses_ListView.getItemAtPosition(positionListView);

        Intent intent = new Intent(getActivity(), Single_View_With_Fragments.class);
        intent.putExtra("titel", selectedCourse.getTitle());
        intent.putExtra("html", selectedCourse.getHtml());
        intent.putExtra("open_detail_start", true);
        startActivity(intent);
    }


    //delete the course if the user clicks long at one entry
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        int positionListView = (int)id;
        Course selectedCourse = (Course) own_Courses_ListView.getItemAtPosition(positionListView);

        dataSource.deleteCourse(selectedCourse);
        CharSequence text = selectedCourse.getTitle() + R.string.deleted;
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
        toast.show();

        showAllListEntries();
        return true;

    }

    }

    public Own_Courses_Fragment() {
        // Required empty public constructor
    }

    public static Own_Courses_Fragment newInstance() {
        Own_Courses_Fragment own_courses_fragment = new Own_Courses_Fragment();
        return own_courses_fragment;
    }
}