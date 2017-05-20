package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabi on 17.05.2017.
 */

public class Cancelled_Courses_ArrayAdapter extends ArrayAdapter<Course> {
    private final Context context;
    private final ArrayList<Course> courseArrayList;
    private Own_Courses_DataSource dataSource;
    View rowView;
    TextView title;


    public Cancelled_Courses_ArrayAdapter(@NonNull Context context, ArrayList<Course> courseArrayList) {
        super(context, -1, courseArrayList);
        this.context = context;
        this.courseArrayList = courseArrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.cancelled_courses_row, parent, false);
            TextView begin = (TextView) rowView.findViewById(R.id.cancelled_courses_begin);
            TextView end = (TextView) rowView.findViewById(R.id.cancelled_courses_end);
            TextView number = (TextView) rowView.findViewById(R.id.cancelled_courses_number);
            title = (TextView) rowView.findViewById(R.id.cancelled_courses_title);
            begin.setText(courseArrayList.get(position).getBegin());
            end.setText(courseArrayList.get(position).getEnd());
            number.setText(courseArrayList.get(position).getNumber());
            title.setText(courseArrayList.get(position).getTitle());

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callDetailActivity(courseArrayList.get(position).getTitle(), courseArrayList.get(position).getHtml());
                }
            });

        dataSource = new Own_Courses_DataSource(context);
        dataSource.open();
        List<Course> CourseListDB = dataSource.getAllCourses();

        for (int j = 0; j < CourseListDB.size(); j++) {
                if (CourseListDB.get(j).getNumber().equals(number.getText().toString())) {
                    title.setText(title.getText().toString().toUpperCase());
                    rowView.setBackgroundColor(Color.YELLOW);
                }
        }
        dataSource.close();
        return rowView;
    }

    @Override
    public int getCount() {
        return courseArrayList.size();
    }

    private void callDetailActivity(String title, String html) {
        Intent intent = new Intent(context, Single_View_With_Fragments.class);
        intent.putExtra("titel", title);
        intent.putExtra("html", html);
        intent.putExtra("open_detail_start",true);
        context.startActivity(intent);
    }
}


