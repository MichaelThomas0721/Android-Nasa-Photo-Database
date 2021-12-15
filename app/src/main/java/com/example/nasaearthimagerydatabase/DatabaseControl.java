package com.example.nasaearthimagerydatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseControl {

    MyOpener dbOpener;
    SQLiteDatabase db;

    public ArrayList<Image> loadDataFromDatabase(Context context){
        //Get database
        dbOpener = new MyOpener(context);
        db = dbOpener.getWritableDatabase();

        ArrayList<Image> images = new ArrayList<>();

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
        return images;
    }

    public ArrayList<Image> deleteImage(Image im,int position, ArrayList<Image> images, Context context){
        dbOpener = new MyOpener(context);
        db = dbOpener.getWritableDatabase();
        db.delete(MyOpener.TABLE_NAME, MyOpener.imageId + " = ?", new String[] {Integer.toString(im.imageId)});
        images.remove(position);
        return images;
    }
}
