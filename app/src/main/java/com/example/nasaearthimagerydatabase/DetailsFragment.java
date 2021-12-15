package com.example.nasaearthimagerydatabase;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    /*
    This script is used for the fragment that is used to present the information in the favourites
    activity.
    This activity contains:
    Part 5. Fragment:
    This is the actual fragment that is presented.
     */

    //Create variables
    private AppCompatActivity parentActivity;
    SQLiteDatabase db;
    private Favourites favourites;
    private ImageView fragImage;
    private TextView fragLon;
    private TextView fragLat;
    private ProgressBar fragProgress;
    private Image image;
    private byte[] bitmapArray;
    private String longitude;
    private String latitude;
    private int position;
    private ArrayList<Image> images = new ArrayList<Image>();
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view
        View result = inflater.inflate(R.layout.fragment_details, container, false);

        //Get elements in view
        fragImage = (ImageView) result.findViewById(R.id.fragImage);
        fragLon = (TextView) result.findViewById(R.id.fragLon);
        fragLat = (TextView) result.findViewById(R.id.fragLat);

        //Set values
        fragSetData();

        //Get database
        MyOpener dbOpener = new MyOpener(parentActivity.getApplicationContext());
        db = dbOpener.getWritableDatabase();

        //Button used to delete the image from the database
        Button deleteButton = (Button) result.findViewById(R.id.fragDelButton);
        deleteButton.setOnClickListener(clk -> {
            //Delete from database
            db.delete(MyOpener.TABLE_NAME, MyOpener.imageId + " = ?", new String[] {Integer.toString(image.imageId)});
            //Delete from and update list
            DatabaseControl dbControl = new DatabaseControl();
            dbControl.deleteImage(image, position, images, context);
            if (favourites != null)
            {
                favourites.deleteImage();
            }
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        //Return view
        return result;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //Attach context
        parentActivity = (AppCompatActivity)context;
    }

    //Import data passed in from activity
    public void importData(Image image, Favourites favourites, int position, ArrayList<Image> images, Context context) {
        this.image = image;
        this.favourites = favourites;
        this.bitmapArray = image.bitmapArray;
        this.longitude = image.longitude;
        this.latitude = image.latitude;
        this.position = position;
        this.images = images;
        this.context = context;
    }

    public void importData(Image image, int position, ArrayList<Image> images, Context context) {
        this.image = image;
        this.bitmapArray = image.bitmapArray;
        this.longitude = image.longitude;
        this.latitude = image.latitude;
        this.position = position;
        this.images = images;
        this.context = context;
    }

    //Set data on the fragment
    private void fragSetData(){
        //Create bitmap
        Bitmap image = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        //Set bitmap
        fragImage.setImageBitmap(image);
        //Set text fields
        Resources res = getResources();
        fragLon.setText(res.getText(R.string.Longitude) + ": " + longitude);
        fragLat.setText(res.getText(R.string.Latitude) + ": " + latitude);
    }
}