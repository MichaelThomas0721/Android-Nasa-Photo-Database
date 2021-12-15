package com.example.nasaearthimagerydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import java.util.ArrayList;

public class Details extends AppCompatActivity implements NavInterface {

    /*
    This activity is used to show the details of the app such as the creator.
    This activity contains:
    Part 4. 4 Activites, navigation and toolbar:
        This is 4 of 4 activies in this app.
     */

    //Variables
    int activityId = 3;
    DetailsFragment dFragment;
    private ArrayList<Image> images = new ArrayList<>();
    SQLiteDatabase db;

    //Create activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Set the fragment and title based on the database of images
        DatabaseControl dbControl = new DatabaseControl();
        images = dbControl.loadDataFromDatabase(this);
        TextView title = (TextView) findViewById(R.id.textView);
        if (images.size() > 0){
            title.setText(getString(R.string.Details));
            dFragment = new DetailsFragment();
            dFragment.importData(images.get((int)(Math.floor(Math.random()*(images.size())))),0, images, this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, dFragment).commit();
        }
        else{
            title.setText(getString(R.string.NoImages));
        }

        //Create toolbar and nav drawer
        Toolbar_Navigation tBarNav = new Toolbar_Navigation(this, this);
        tBarNav.CreateToolBar();
        tBarNav.CreateDrawer();
    }

    //Setter and getter for activityId
    @Override
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Override
    public int getActivityId() {
        return activityId;
    }
}