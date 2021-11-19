package com.example.nasaearthimagerydatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "NasaAppDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "Favourites";
    public final static String mID = "_id";
    public final static String imageId = "ImageId";
    public final static String imageName = "ImageName";
    public final static String longitude = "Longitude";
    public final static String latitude = "Latitude";

    public MyOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + imageId + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + imageName + " varchar(255), " + longitude + " varchar(255), " + latitude + " varchar(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersoin, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void deleteDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}