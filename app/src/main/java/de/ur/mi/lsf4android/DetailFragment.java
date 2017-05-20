package de.ur.mi.lsf4android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Susanne on 19.05.2017.
 */

public class DetailFragment extends android.support.v4.app.Fragment  {
    OpenOtherFragment mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_detail_start, container, false);
        final Button[] buttonArray = new Button[3];
        buttonArray[0] = (Button) view.findViewById(R.id.grunddaten_button);
        buttonArray[1] = (Button) view.findViewById(R.id.termine_button);
        buttonArray[2] = (Button) view.findViewById(R.id.inhalt_button);
        for (int i = 0; i < buttonArray.length; i++) {
            final int counter = i;
            buttonArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleClickOnButton(counter);
                    buttonArray[counter].setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            });
        }
            return view;
    }

    public interface OpenOtherFragment {
        public void getFragment(Class fragmentClass, String fragmentName);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OpenOtherFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private void handleClickOnButton (int counter){
        Class fragmentClass;
        String fragmentName;
        switch (counter) {
            case 0:
                fragmentClass = GrunddatenFragment.class;
                fragmentName = "GRUNDDATEN";
                break;
            case 1:
                fragmentClass = TermineFragment.class;
                fragmentName = "TERMINE";
                break;
            case 2:
                fragmentClass = InhaltFragment.class;
                fragmentName = "INHALT";
                break;
            default:
                fragmentClass = GrunddatenFragment.class;
                fragmentName = "GRUNDDATEN";
                break;
        }
        mCallback.getFragment(fragmentClass, fragmentName);
    }
}
