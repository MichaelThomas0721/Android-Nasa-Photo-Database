package com.example.nasaearthimagerydatabase;

import android.content.Intent;
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

    Toolbar tBar;
    DrawerLayout drawer;
    AppCompatActivity activity;
    String name;

    public Toolbar_Navigation(AppCompatActivity activity, String name){
        this.activity = activity;
        this.name = name;
    }

    public void CreateToolBar(){

        //Get tool bar: IN PROGRESS//TO DO
        tBar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(tBar);

    }

    public void CreateDrawer(){
        drawer = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity,
                drawer, tBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView navTitle = header.findViewById(R.id.header_drawertitle);
        navTitle.setText(name + " Activity, Version 1.0");
        navigationView.setNavigationItemSelectedListener(this);
    }

    //IN PROGRESS: TO DO
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);

        return true;
    }

    //IN PROGRESS: TO DO
    public boolean onOptionsItemSelected(MenuItem item){

        MenuItemOptions(item);

        return true;
    }

    //IN PROGRESS: TO DO
    public boolean onNavigationItemSelected( MenuItem item) {

        MenuItemOptions(item);

        DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;

    }

    public void MenuItemOptions(MenuItem item){
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
                alertDialogBuilder.setTitle("How to use home page").setMessage("Enter the longitude and latitude into the input boxes" +
                        " then press the search button to display and image of that longitude and latitude, Or press the favourites button" +
                        " to view favourited images").setNegativeButton("Ok", (click, arg) -> { } ).create().show();
                break;
        }
    }
}
