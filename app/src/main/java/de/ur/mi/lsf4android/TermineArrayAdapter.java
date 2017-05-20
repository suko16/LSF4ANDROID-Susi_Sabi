package de.ur.mi.lsf4android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;



public class TermineArrayAdapter extends ArrayAdapter<Course> {
    private final Context context;
    private final ArrayList<Course> courseArrayList;

    public TermineArrayAdapter(@NonNull Context context, ArrayList<Course> courseArrayList) {
        super(context, -1, courseArrayList);
        this.context = context;
        this.courseArrayList = courseArrayList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.single_view_time_row, parent, false);
        TextView group = (TextView) rowView.findViewById(R.id.group);
        TextView day = (TextView) rowView.findViewById(R.id.day);
        TextView time = (TextView) rowView.findViewById(R.id.time);
        TextView room = (TextView) rowView.findViewById(R.id.room);
        TextView teacher = (TextView) rowView.findViewById(R.id.teacher);
        group.setText("" + courseArrayList.get(position).getGroup());
        day.setText(courseArrayList.get(position).getTitle());
        time.setText(courseArrayList.get(position).getBegin());
        room.setText(courseArrayList.get(position).getEnd());
        teacher.setText(courseArrayList.get(position).getNumber());

        return rowView;
    }

    @Override
    public int getCount() {
        return courseArrayList.size();
    }

}


