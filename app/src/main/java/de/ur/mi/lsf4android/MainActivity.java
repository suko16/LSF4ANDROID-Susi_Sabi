package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView urLogo = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.urLogo);
        urLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUrLogoClicked();
            }
        });

        //If Activity changes right Fragment loads by Extra.
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("open_eigene_fragment",false)){
            startEigeneFragment();
            setTitle(intent.getStringExtra("Button_Eigene"));
        }
        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_point_1:
                startAusfallendeActivity();
                break;
            case R.id.menu_point_2:
               startEigeneFragment();
                break;
            case R.id.menu_point_3:
                startAlleActivity();
                break;
            default:
                startAusfallendeActivity();
        }
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //redirects to homescreen when logo s clicked
   private void onUrLogoClicked(){
        Intent i = new Intent(this,StartActivity.class);
        startActivity(i);
    }

    private void startAusfallendeActivity(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy");
        String date = datumsformat.format(calendar.getTime());
        Intent intentAusfallende = new Intent(this, Cancelled_Courses_Activity.class);
        intentAusfallende.putExtra("date", date);
        startActivity(intentAusfallende);
    }

    private void startEigeneFragment(){
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = Own_Courses_Fragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit();
    }

    private void startAlleActivity(){
        Intent intentAlle = new Intent(this, Course_Overview_Path_Activity.class);
        intentAlle.putExtra("HtmlExtra","https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852&P.vx=mittel");
        startActivity(intentAlle);
    }

    @Override
    public void onPause(){
        super.onPause();

        //starts the Service in the background when App pauses/ is minimized
        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
    }
}

