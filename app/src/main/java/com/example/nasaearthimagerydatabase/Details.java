package com.example.nasaearthimagerydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Details extends AppCompatActivity implements NavInterface{

    /*
    This activity is used to show the details of the app such as the creator.
    This activity contains:
    Part 4. 4 Activites, navigation and toolbar:
        This is 4 of 4 activies in this app.
     */

    int activityId = 3;

    //Create activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar_Navigation tBarNav = new Toolbar_Navigation(this, this);
        tBarNav.CreateToolBar();
        tBarNav.CreateDrawer();
    }

    @Override
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Override
    public int getActivityId() {
        return activityId;
    }
}