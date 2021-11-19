package com.example.nasaearthimagerydatabase;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class ImageResult extends AppCompatActivity{
    SQLiteDatabase db;
    String lon;
    String lat;
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
        //Execute async task to get imagae
        try{
            dImage.execute("https://api.nasa.gov/planetary/earth/imagery?lon=" + lon + "&lat=" + lat + "&date=2014-02-01&api_key=Jg1GeknqKbCyb0g2Np5nN5TgphXFqiSlOhZmjHCE");
        } catch (Exception e){
            System.out.println(e);
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Showing result for " + lon + ", " + lat, Toast.LENGTH_SHORT);
        toast.show();

         //Create Toast for adding image to favourites
        Button favouriteButton = (Button) findViewById(R.id.addFavouriteButton);
        favouriteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText favName = (EditText) findViewById(R.id.FavouriteNameInput);
                if (favName.getText().toString() != "") {
                    ContentValues newRowValues = new ContentValues();

                    newRowValues.put(MyOpener.imageName, favName.getText().toString());
                    newRowValues.put(MyOpener.longitude, lon);
                    newRowValues.put(MyOpener.latitude, lat);

                    long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

                    Snackbar snackbar = Snackbar.make(v, "Added to favourites!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    //Get and set image view
    public void updateImage(Bitmap bMap){
        ImageView imageResult = (ImageView) findViewById(R.id.imageResult);
        imageResult.setImageBitmap(bMap);
    }

    private class DownloadImage extends AsyncTask<String, Integer, Void> {
        Bitmap bMap;
        @Override
        protected Void doInBackground(String... src) {
            try{
                //Connect to api and download image
                java.net.URL url = new java.net.URL(src[0]);
                publishProgress(10);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                publishProgress(35);
                connection.connect();
                publishProgress(50);
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                bMap = myBitmap;
                publishProgress(100);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void onProgressUpdate(Integer... args){
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.imageProgress);
            progressBar.setProgress(args[0]);
        }

        protected void onPostExecute(Void e){
            updateImage(bMap);
        }
    }
}
