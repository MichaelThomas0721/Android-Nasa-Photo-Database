package com.example.nasaearthimagerydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    private ArrayList<Image> images = new ArrayList<>();
    SQLiteDatabase db;
    MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        ListView myList = findViewById(R.id.favouriteList);
        myList.setAdapter( myAdapter = new MyListAdapter());

        loadDataFromDatabase();
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return (images.get(position).imageId);
        }

        @Override
        public long getItemId(int position) {
            return (long)position;
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();
            newView = inflater.inflate(R.layout.list_favourites, parent, false);

            //Get layout elements
            TextView textId = newView.findViewById(R.id.fListId);
            TextView textName = newView.findViewById(R.id.fListName);

            //Set text for layout elements
            textId.setText("Id: " + String.valueOf(images.get(position).imageId));
            textName.setText("Name: " + images.get(position).imageName);

            return newView;
        }
    }

    private void loadDataFromDatabase(){
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        //dbOpener.deleteDatabase((db));
        //dbOpener.onCreate(db);

        String[] columns = {dbOpener.imageId, dbOpener.imageName, dbOpener.longitude, dbOpener.latitude};

        Cursor results = db.query(false, dbOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int imageIdIndex = results.getColumnIndex(MyOpener.imageId);
        int imageNameIndex = results.getColumnIndex(MyOpener.imageName);
        int longitudeIndex = results.getColumnIndex(MyOpener.longitude);
        int latitudeIndex = results.getColumnIndex(MyOpener.latitude);

        while(results.moveToNext()){
            int imageId = results.getInt(imageIdIndex);
            String imageName = results.getString(imageNameIndex);
            String longitude = results.getString(longitudeIndex);
            String latitude = results.getString(latitudeIndex);
            images.add(new Image(imageName, imageId, longitude, latitude));
        }
    }

    protected void deleteMessage(Image im){
        db.delete(MyOpener.TABLE_NAME, MyOpener.imageId + " = ?", new String[] {Integer.toString(im.imageId)});
    }

}