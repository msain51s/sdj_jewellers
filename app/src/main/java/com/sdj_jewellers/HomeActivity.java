package com.sdj_jewellers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sdj_jewellers.utility.OnSwipeTouchListener;
import com.sdj_jewellers.utility.Preference;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public FrameLayout frameLayout;
    ImageView logo;
    Preference preference;
    View homeView;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        preference=new Preference(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logo= (ImageView) findViewById(R.id.home_lgo);
    //    Animation animation= AnimationUtils.loadAnimation(this,R.anim.fade_out);

    //    logo.startAnimation(animation);
        frameLayout= (FrameLayout) findViewById(R.id.contentFrame);
        homeView=findViewById(R.id.home_view);
        homeView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
               Intent intent=new Intent(HomeActivity.this,GalleryActivity.class);
                startActivity(intent);
            }

            @Override
            public void onSwipeRight() {
                Intent intent=new Intent(HomeActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
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

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if(x2>x1){}
                    else
                    startActivity(new Intent(HomeActivity.this,GalleryActivity.class));
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
             startActivity(new Intent(HomeActivity.this,GalleryActivity.class));
        } else if (id == R.id.nav_education) {
            startActivity(new Intent(HomeActivity.this,EducationActivity.class));
        } else if (id == R.id.nav_basket) {
            startActivity(new Intent(HomeActivity.this,CartActivity.class));
        }else if (id == R.id.nav_logout) {
           preference.clearAllPrefereces();
           Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }  else if (id == R.id.nav_about_us) {
            startActivity(new Intent(HomeActivity.this,AboutUsActivity.class));
        }else if (id == R.id.nav_reach_us) {
            startActivity(new Intent(HomeActivity.this,ReachUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
