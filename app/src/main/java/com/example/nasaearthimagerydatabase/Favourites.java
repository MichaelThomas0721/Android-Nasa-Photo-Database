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
    MyListAdapter myAdapter;
    DetailsFragment dFragment;
    Favourites favourites;
    int activityId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        //Get list and set adapter
        ListView myList = findViewById(R.id.favouriteList);
        myList.setAdapter( myAdapter = new MyListAdapter());

        //Load database
        loadDataFromDatabase();

        //Set activity to this activity
        favourites = this;

        //Create fragment to display information on click of list item
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dFragment = new DetailsFragment();
                    dFragment.importData(images.get(position), favourites, position);
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

    private void loadDataFromDatabase(){
        //Get database
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        //IGNORE: FUNCTIONS USED FOR TESTING
        //dbOpener.deleteDatabase((db));
        //dbOpener.onCreate(db);

        //Get results of query
        String[] columns = {dbOpener.imageId, dbOpener.imageName, dbOpener.longitude, dbOpener.latitude, dbOpener.bitmapArray};
        Cursor results = db.query(false, dbOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Get data from results
        int imageIdIndex = results.getColumnIndex(MyOpener.imageId);
        int imageNameIndex = results.getColumnIndex(MyOpener.imageName);
        int longitudeIndex = results.getColumnIndex(MyOpener.longitude);
        int latitudeIndex = results.getColumnIndex(MyOpener.latitude);
        int bitmapArrayIndex = results.getColumnIndex(MyOpener.bitmapArray);

        //Set the data
        while(results.moveToNext()){
            int imageId = results.getInt(imageIdIndex);
            String imageName = results.getString(imageNameIndex);
            String longitude = results.getString(longitudeIndex);
            String latitude = results.getString(latitudeIndex);
            //Decode and set image as bitmap
            byte[] bitmapArray = results.getBlob(bitmapArrayIndex);
            images.add(new Image(imageName, imageId, longitude, latitude, bitmapArray));
        }
    }

    //Used for deleting an image from database
    public void deleteImage(Image im,int position){
        db.delete(MyOpener.TABLE_NAME, MyOpener.imageId + " = ?", new String[] {Integer.toString(im.imageId)});
        images.remove(position);
        myAdapter.notifyDataSetChanged();
    }

    //Setter and getter for activityId
    public void setActivityId(int activityId){
        this.activityId = activityId;
    }

    public int getActivityId(){
        return activityId;
    }

}