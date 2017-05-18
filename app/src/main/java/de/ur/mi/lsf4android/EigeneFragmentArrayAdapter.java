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

public class EigeneFragmentArrayAdapter extends ArrayAdapter<EigeneV_Objekt> {

    private final Context context;
    private final ArrayList<EigeneV_Objekt> veranstaltungen;

    public EigeneFragmentArrayAdapter(@NonNull Context context, ArrayList<EigeneV_Objekt> veranstaltungen) {
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
        number.setText(veranstaltungen.get(position).getNumber());
        titel.setText(veranstaltungen.get(position).getTitel());
        return rowView;
    }

    @Override
    public int getCount() {
        return veranstaltungen.size();
    }
}


