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

    int activityId = 3;
    DetailsFragment dFragment;
    private ArrayList<Image> images = new ArrayList<>();
    SQLiteDatabase db;

    //Create activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
}