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

    public Own_Courses_ArrayAdapter(@NonNull Context context, ArrayList<Course> own_courses_arrayList) {
        super(context, -1, own_courses_arrayList);
        this.context = context;
        this.own_courses_arrayList = own_courses_arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.own_courses_row, parent, false);
        TextView number = (TextView) rowView.findViewById(R.id.own_courses_number);
        TextView title = (TextView) rowView.findViewById(R.id.own_courses_title);

        //TODO: Textsize
        //setTextSize ist vmlt schlecht fürs responsive oder? kann man des im layout mit dimens angeben? //
        // android:textSize="@dimen/font_size"/> funktioniert aber nur bei TextViews und wir haben ja n listview
        //oder wir legen ein layout textview an das alle textviews referenzieren und geben da ne size mit dimens an?

        number.setTextSize(18);
        number.setTextColor(Color.BLACK);
        title.setTextSize(18);
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



