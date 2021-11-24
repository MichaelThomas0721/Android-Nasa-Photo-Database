package com.example.nasaearthimagerydatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    /*
    This is the main activity, this activity contains:
    Part 3. 1 edit text, 1 toast, 1 snackbar:
        This contains the edit text element of this part.
    Part 4. 4 Activites, navigation and toolbar:
        This is 1 of 4 activies in this app.
    Part 6. Alert:
        Contains a button that display an alert dialog with instruction on how to use interface
    Part 10. SharedPreferences:
        The shared preferences are used to save the inputted longitude and latitude so they are
        reentered into the appropriate input boxes when the app is loaded again.
    */

    //Creating the final string for the shared preferences.
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get button and inputs
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button favouritesButton = (Button) findViewById(R.id.favouritesButton);
        Button helpButton = (Button) findViewById(R.id.helpButton);
        EditText lonInput = (EditText) findViewById(R.id.LonInput);
        EditText latInput = (EditText) findViewById(R.id.LatInput);

        //Get shared preferences and an editor
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        //Use shared preferences to fill the input boxes
        lonInput.setText(prefs.getString("lon", ""));
        latInput.setText(prefs.getString("lat", ""));

        //Onclick open image result activity
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Create intent to pass inputs to next activity
                Intent imageResultInt = new Intent(MainActivity.this, ImageResult.class);
                //Pass in fields for next activity
                imageResultInt.putExtra("Lon", lonInput.getText().toString());
                imageResultInt.putExtra("Lat", latInput.getText().toString());
                //Start activity
                startActivity(imageResultInt);
            }
        });

        //Button to open favourites activity
        favouritesButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Create intent to pass inputs to next activity
                Intent favouriteInt = new Intent(MainActivity.this, Favourites.class);
                //Start activity
                startActivity(favouriteInt);
            }
        });

        //Button to learn how to use activity
        helpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Create alert builder to create alert
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                //Create alert, set the alert contents and show the alert.
                alertDialogBuilder.setTitle("How to use home page").setMessage("Enter the longitude and latitude into the input boxes" +
                        " then press the search button to display and image of that longitude and latitude, Or press the favourites button" +
                        " to view favourited images").setNegativeButton("Ok", (click, arg) -> { } ).create().show();
            }
        });

        //Get tool bar: IN PROGRESS//TO DO
        Toolbar tBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        //Get drawer: IN PROGRESS//TO DO
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    //Save inputs to shared preferences when paused
    @Override
    protected void onPause(){
        super.onPause();
        ///Get fields
        EditText lonText = (EditText) findViewById(R.id.LonInput);
        EditText latText = (EditText) findViewById(R.id.LatInput);
        //Get shared preferences
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        //Input the text into the shared preferences
        edit.putString("lon", lonText.getText().toString());
        edit.putString("lat", latText.getText().toString());
        //Apply the new fields
        edit.apply();
    }

    //IN PROGRESS: TO DO
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);

        return true;
    }

    //IN PROGRESS: TO DO
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.detailsItem:
                        Intent detailsInt = new Intent(MainActivity.this, Details.class);
                        startActivity(detailsInt);
                break;
            case R.id.mainItem:
                Intent mainInt = new Intent(MainActivity.this, MainActivity.class);
                startActivity(mainInt);
                break;
            case R.id.favouriteItem:
                Intent favouriteInt = new Intent(MainActivity.this, Favourites.class);
                startActivity(favouriteInt);
                break;
        }

        return true;
    }

    //IN PROGRESS: TO DO
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.detailsItem:
                Intent detailsInt = new Intent(MainActivity.this, Details.class);
                startActivity(detailsInt);
                break;
            case R.id.mainItem:
                Intent mainInt = new Intent(MainActivity.this, MainActivity.class);
                startActivity(mainInt);
                break;
            case R.id.favouriteItem:
                Intent favouriteInt = new Intent(MainActivity.this, Favourites.class);
                startActivity(favouriteInt);
                break;
        }

        return false;

    }
}