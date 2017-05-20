
package de.ur.mi.lsf4android;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class Own_Courses_ArrayAdapter extends ArrayAdapter<Course> {
    private final Context context;
    private final ArrayList<Course> own_courses_arrayList;

    //constructor
    public Own_Courses_ArrayAdapter(@NonNull Context context, ArrayList<Course> own_courses_arrayList) {
        super(context, -1, own_courses_arrayList);
        this.context = context;
        this.own_courses_arrayList = own_courses_arrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.own_courses_row, parent, false);
        TextView number = (TextView) rowView.findViewById(R.id.own_courses_number);
        TextView title = (TextView) rowView.findViewById(R.id.own_courses_title);
        number.setTextColor(Color.BLACK);
        title.setTextColor(Color.BLACK);
        number.setText(own_courses_arrayList.get(position).getNumber());
        title.setText(own_courses_arrayList.get(position).getTitle());
        return rowView;
    }

    @Override
    public int getCount() {
        return own_courses_arrayList.size();
    }
}


