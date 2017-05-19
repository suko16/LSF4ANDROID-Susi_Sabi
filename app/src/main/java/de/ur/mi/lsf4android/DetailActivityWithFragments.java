package de.ur.mi.lsf4android;




import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Susanne on 19.05.2017.
 */

public class DetailActivityWithFragments extends NavigationActivity implements DetailFragment.OpenOtherFragment {
    private ListView detailActivityListview;
    private DetailActivityArrayAdapter detailActivityArrayAdapter;
    private Button button;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConstraintLayout contentConstraintLayout = (ConstraintLayout) findViewById(R.id.fragment_content_navigation); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_detail_aktivity_with_fragments, contentConstraintLayout);

        Intent intent = getIntent();

        if(savedInstanceState == null) {
            if (intent != null && intent.getBooleanExtra("open_detail_start", false)) {
                Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = DetailFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentManager.beginTransaction().replace(R.id.fragment_content_navigation, fragment, "DETAIL_FRAGMENT").commit();
                setTitle("Detail");
            }
        }



    }

    public void getFragment (Class fragmentClass, String fragmentName) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("titel", intent.getStringExtra("titel"));
        bundle.putString("html", intent.getStringExtra("html"));

            Fragment fragment = null;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment_content_navigation, fragment, fragmentName).commit();
            setTitle(fragmentName);
        }

   /*     @Override
    public void onBackPressed(){
            Fragment nextFragment = null;
            Class fragmentClass;
            fragmentClass = DetailFragment.class;
            try {
                nextFragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (nextFragment != null && nextFragment.isVisible() == false) {
                fragmentManager.beginTransaction().replace(R.id.fragment_content_navigation, nextFragment, "DETAIL_FRAGMENT").commit();
                fragmentManager.beginTransaction().show(nextFragment).commit();
            }else if (nextFragment != null && nextFragment.isVisible() == true){
                super.onBackPressed();
            }
        }*/
    }




