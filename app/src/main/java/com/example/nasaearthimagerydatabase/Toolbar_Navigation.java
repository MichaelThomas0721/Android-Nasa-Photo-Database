package com.example.nasaearthimagerydatabase;

import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Toolbar_Navigation implements NavigationView.OnNavigationItemSelectedListener{

    //Create variables
    Toolbar tBar;
    DrawerLayout drawer;
    AppCompatActivity activity;
    Resources res;
    String[] help_bodies;
    String[] activity_name;
    String[] help_titles;
    int activityId;

    //Constructor to set initial variables and get strings
    public Toolbar_Navigation(AppCompatActivity activity, NavInterface activityIdSet){
        this.activity = activity;
        activityId = activityIdSet.getActivityId();
        res = activity.getResources();
        help_bodies = res.getStringArray(R.array.help_bodies);
        activity_name = res.getStringArray(R.array.activites_names);
        help_titles = res.getStringArray(R.array.help_titles);
    }

    public void CreateToolBar(){

        //Get tool bar
        tBar = (Toolbar) activity.findViewById(R.id.toolbar);
        tBar.inflateMenu(R.menu.navigation_menu);
        //Set menu listener
        tBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Call generalized class for selecting items
            MenuItemOptions(item);
                return false;
            }
        });
    }

    //Used to create the drawer
    public void CreateDrawer(){
        //Get drawer
        drawer = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity,
                drawer, tBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        //Set header
        View header = navigationView.getHeaderView(0);
        TextView navTitle = header.findViewById(R.id.header_drawertitle);
        navTitle.setText(activity_name[activityId] + " Activity, Version 1.0");
        //Set listener
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Used to create toolbar
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);

        return true;
    }

    //Calls generalized class for selecting items
    public boolean onOptionsItemSelected(MenuItem item){

        MenuItemOptions(item);

        return true;
    }

    //Calls generalized class for selecting items
    public boolean onNavigationItemSelected( MenuItem item) {

        MenuItemOptions(item);

        DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;

    }

    //Generalized class for selecting items
    public void MenuItemOptions(MenuItem item){
        //Switch case based on which item is selected
        switch(item.getItemId())
        {
            case R.id.detailsItem:
                Intent detailsInt = new Intent(activity, Details.class);
                activity.startActivity(detailsInt);
                break;
            case R.id.mainItem:
                Intent mainInt = new Intent(activity, MainActivity.class);
                activity.startActivity(mainInt);
                break;
            case R.id.favouriteItem:
                Intent favouriteInt = new Intent(activity, Favourites.class);
                activity.startActivity(favouriteInt);
                break;
            case R.id.helpItem:
                //Create alert builder to create alert
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                //Create alert, set the alert contents and show the alert.
                alertDialogBuilder.setTitle(help_titles[0] + " " + activity_name[activityId] + " " +
                        help_titles[1]).setMessage(help_bodies[activityId])
                        .setNegativeButton("Ok", (click, arg) -> { } ).create().show();
                break;
        }
    }
}
