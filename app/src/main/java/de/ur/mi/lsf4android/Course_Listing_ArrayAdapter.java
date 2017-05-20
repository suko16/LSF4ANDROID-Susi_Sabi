package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;


public class Course_Listing_ArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] number;
    private final String[] title;
    private final String[] html;
    public Own_Courses_DataSource dataSource;


    public Course_Listing_ArrayAdapter(Context context, String[] number, String[] title, String[] html) {
        super(context, R.layout.course_overview_path_row, number);
        this.context = context;
        this.number = number;
        this.title = title;
        this.html = html;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.course_overview_path_row, parent, false);
        TextView textViewNumber = (TextView) rowView.findViewById(R.id.course_listing_number);
        TextView textViewName = (TextView) rowView.findViewById(R.id.course_listing_name);
        Button button = (Button) rowView.findViewById(R.id.course_listing_button);
        button.setId(position);
        textViewNumber.setText(number[position]);
        textViewName.setText(title[position]);
        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Single_View_With_Fragments.class);
                intent.putExtra("titel", title[position]);
                intent.putExtra("html", html[position]);
                intent.putExtra("open_detail_start",true);
                context.startActivity(intent);
            }
        });

       //Überprüft vor dem erstellen welche Buttons für welche Veranstaltung
        //dementsprechender ClickListener gesetzt


        dataSource = new Own_Courses_DataSource(getContext());
        dataSource.open();
        final List<Course> CourseListDB = dataSource.getAllCourses();
        String current = textViewNumber.getText().toString();
        for(int j=0; j< CourseListDB.size(); j++) {
            if (CourseListDB.get(j).getNumber().equals(current)) {
                final int temp = j;

                button.setBackgroundResource(R.mipmap.remove_button);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view){

                        LinearLayout vwParentRow = (LinearLayout)view.getParent();
                        Button tempButton = (Button) vwParentRow.getChildAt(2);
                        dataSource.deleteCourse(CourseListDB.get(temp));
                        CharSequence text = CourseListDB.get(temp).getTitle() + " wurde gelöscht";

                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                        tempButton.setBackgroundResource(R.mipmap.add_button);
                    }
                });

                break;
            } else {

              button.setBackgroundResource(R.mipmap.add_button);
              button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        LinearLayout vwParentRow = (LinearLayout)view.getParent();
                        TextView tempTitle = (TextView) vwParentRow.getChildAt(1);
                        TextView tempNumber = (TextView) vwParentRow.getChildAt(0);
                        Button tempButton = (Button) vwParentRow.getChildAt(2);
                        dataSource.createVeranstaltung(tempTitle.getText().toString(), tempNumber.getText().toString(), html[position]);
                        CharSequence text = tempTitle.getText().toString() + " wurde in Eigene Veranstaltungen gespeichert";
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                        tempButton.setBackgroundResource(R.mipmap.remove_button);
                    }
                });
            }
        }

        return rowView;
    }
}
