package com.example.nasaearthimagerydatabase;

public class Image {

    /*
    This class is for the creation of images.
     */

    //Image variables
    int imageId;
    String imageName;
    String longitude;
    String latitude;
    byte[] bitmapArray;

    //Constructor method.
    Image(String imageName, int imageId, String longitude, String latitude, byte[] bitmapArray){
        this.imageName = imageName;
        this.imageId = imageId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.bitmapArray = bitmapArray;
    }

    //Getters and setters.
    public String getImageName(){
        return imageName;
    }

    public void setImageName(String imageName){
        this.imageName = imageName;
    }

    public int getImageId(){
        return imageId;
    }

    public void setImageId(int imageId){
        this.imageId = imageId;
    }

    public String getLongitude(){
        return longitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getLatitude(){
        return latitude;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public byte[] getBitmapArray(){
        return bitmapArray;
    }

    public void setBitmapArray(byte[] bitmapArray){
        this.bitmapArray = bitmapArray;
    }
}
