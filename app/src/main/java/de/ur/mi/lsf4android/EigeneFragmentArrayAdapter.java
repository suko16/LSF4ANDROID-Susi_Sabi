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

public class EigeneFragmentArrayAdapter extends ArrayAdapter<Veranstaltung> {

    private final Context context;
    private final ArrayList<Veranstaltung> veranstaltungen;

    public EigeneFragmentArrayAdapter(@NonNull Context context, ArrayList<Veranstaltung> veranstaltungen) {
        super(context, -1, veranstaltungen);
        this.context = context;
        this.veranstaltungen = veranstaltungen;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_eigene, parent, false);
        TextView number = (TextView) rowView.findViewById(R.id.number_eigene);
        TextView titel = (TextView) rowView.findViewById(R.id.titel_eigene);

        //TODO: Textsize
        //setTextSize ist vmlt schlecht fürs responsive oder? kann man des im layout mit dimens angeben? //
        // android:textSize="@dimen/font_size"/> funktioniert aber nur bei TextViews und wir haben ja n listview
        //oder wir legen ein layout textview an das alle textviews referenzieren und geben da ne size mit dimens an?

        number.setTextSize(18);
        number.setTextColor(Color.BLACK);
        titel.setTextSize(18);
        titel.setTextColor(Color.BLACK);
        number.setText(veranstaltungen.get(position).getNumber());
        titel.setText(veranstaltungen.get(position).getTitel());
        return rowView;
    }

    @Override
    public int getCount() {
        return veranstaltungen.size();
    }
}



