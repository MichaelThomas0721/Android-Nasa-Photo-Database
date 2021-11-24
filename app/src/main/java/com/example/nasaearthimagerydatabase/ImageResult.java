package com.example.nasaearthimagerydatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class ImageResult extends AppCompatActivity{

    /*
    This activity is used to display the search result of the main activity.
    This activity contains:
    Part 2. Button and progress bar:
        Contains both a button and a progress bar.
    Part 3. 1 edit text, 1 toast, 1 snackbar:
        Contains the toast and snackbar.
    Part 4. 4 Activites, navigation and toolbar:
        This is 3 of 4 activies in this app.
    Part 6. Alert:
        Contains a button that display an alert dialog with instruction on how to use interface.
    Part 9. AsyncTask:
        Contains all of part 9.
     */

    //Create variables
    SQLiteDatabase db;
    String lon;
    String lat;
    Bitmap imageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_result);

        //Load database
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
        //dbOpener.onCreate(db);

        //Get inputs from intent
        Intent i = getIntent();
        lon = i.getStringExtra("Lon");
        lat = i.getStringExtra("Lat");
        //Create async task
        DownloadImage dImage = new DownloadImage();
        //Execute async task to get image
        try{
            dImage.execute("https://api.nasa.gov/planetary/earth/imagery?lon=" + lon + "&lat=" + lat + "&date=2014-02-01&api_key=Jg1GeknqKbCyb0g2Np5nN5TgphXFqiSlOhZmjHCE");
        } catch (Exception e){
            System.out.println(e);
        }

        //Create and show toast with the latitude and longitude of the search result
        Toast toast = Toast.makeText(getApplicationContext(), "Showing result for " + lon + ", " + lat, Toast.LENGTH_SHORT);
        toast.show();

        //Find button
        Button favouriteButton = (Button) findViewById(R.id.addFavouriteButton);
        //Button for adding image to favourites
        favouriteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //Get edit text for name of image
                EditText favName = (EditText) findViewById(R.id.FavouriteNameInput);
                if (favName.getText().toString() != "") {
                    //Create values to be passed to database
                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(MyOpener.imageName, favName.getText().toString());
                    newRowValues.put(MyOpener.longitude, lon);
                    newRowValues.put(MyOpener.latitude, lat);
                    //Convert image into byte array to store in database
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    imageMap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] bitmapArray = bos.toByteArray();
                    //Put values into database
                    newRowValues.put(MyOpener.bitmapArray, bitmapArray);
                    long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

                    //Display snack bar notifying user that image has been added to database.
                    Snackbar snackbar = Snackbar.make(v, "Added to favourites!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        //Button used to display help on how to use activity
        Button helpButton = (Button) findViewById(R.id.resultHelpButton);
        helpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImageResult.this);
                alertDialogBuilder.setTitle("How to use result page").setMessage("Enter a name into the input box and select the favourite button" +
                        " to add the image to your favourites, hit the android back button to return to the main page")
                        .setNegativeButton("Ok", (click, arg) -> { } ).create().show();
            }
        });
    }

    //Get and set image view
    public void updateImage(Bitmap bMap){
        imageMap = bMap;
        ImageView imageResult = (ImageView) findViewById(R.id.imageResult);
        imageResult.setImageBitmap(bMap);
    }

    //AsyncTask used for downloading image and updating progress bar
    private class DownloadImage extends AsyncTask<String, Integer, Void> {
        //Create bitmap
        Bitmap bMap;
        @Override
        protected Void doInBackground(String... src) {
            try{
                //Connect to api and download image
                java.net.URL url = new java.net.URL(src[0]);
                //Updating progress bar
                publishProgress(10);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                //Updating progress bar
                publishProgress(35);
                connection.connect();
                //Updating progress bar
                publishProgress(50);
                InputStream input = connection.getInputStream();
                //Set bitmap with downloaded content
                bMap = BitmapFactory.decodeStream(input);
                //Updating progress bar
                publishProgress(100);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Used for updating progress par.
        public void onProgressUpdate(Integer... args){
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.imageProgress);
            progressBar.setProgress(args[0]);
        }

        //Used to update image after downloaded
        protected void onPostExecute(Void e){
            updateImage(bMap);
        }
    }
}
