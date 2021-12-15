package com.example.nasaearthimagerydatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity implements NavInterface{

    /*
    This activity is used to display the database of favourite images
    This activity contains:
    Part 1. Listview:
        Contains all of part 1.
    Part 4. 4 Activites, navigation and toolbar:
        This is 2 of 4 activies in this app.
    Part 5. Fragment:
        This is the activity that display the fragment.
    Part 6. Alert:
        Contains a button that display an alert dialog with instruction on how to use interface
    Part 8. Database:
        This activity displays the contents of the database and deleting of the items
        in the database.
     */

    //Create variables
    private ArrayList<Image> images = new ArrayList<>();
    SQLiteDatabase db;
    DetailsFragment dFragment;
    Favourites favourites;
    int activityId = 1;
    MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        //Get list and set adapter
        ListView myList = findViewById(R.id.favouriteList);
        myList.setAdapter( myAdapter = new MyListAdapter());

        //Set activity to this activity
        favourites = this;

        //Create fragment to display information on click of list item
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dFragment = new DetailsFragment();
                    dFragment.importData(images.get(position), favourites, position, images, favourites);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, dFragment).commit();
            }
        });

        //Create toolbar and nav drawer
        Toolbar_Navigation tBarNav = new Toolbar_Navigation(this, this);
        tBarNav.CreateToolBar();
        tBarNav.CreateDrawer();
    }

    //Adapter for list
    private class MyListAdapter extends BaseAdapter {

        //Get size of list
        @Override
        public int getCount() {
            return images.size();
        }

        //Return object based on position
        @Override
        public Object getItem(int position) {
            return (images.get(position).imageId);
        }

        //Get id of item based on position
        @Override
        public long getItemId(int position) {
            return (long)position;
        }

        //Create view for items
        @Override
        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();
            newView = inflater.inflate(R.layout.list_favourites, parent, false);

            //Get layout elements
            TextView textId = newView.findViewById(R.id.fListId);
            TextView textName = newView.findViewById(R.id.fListName);

            //Set text for layout elements
            Resources res = getResources();
            textId.setText(res.getText(R.string.Id) + ": " + String.valueOf(images.get(position).imageId));
            textName.setText(res.getText(R.string.Name) + ": " + images.get(position).imageName);

            return newView;
        }
    }

    //Used for deleting an image from database
    public void deleteImage() {
        myAdapter.notifyDataSetChanged();
    }

    //Setter and getter for activityId
    public void setActivityId(int activityId){
        this.activityId = activityId;
    }

    public int getActivityId(){
        return activityId;
    }

    //Update in case changes happened while not open also when first opened
    @Override
    protected void onResume() {
        super.onResume();
        DatabaseControl dbControl = new DatabaseControl();
        images = dbControl.loadDataFromDatabase(this);
        myAdapter.notifyDataSetChanged();
    }
}