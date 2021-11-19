package com.example.nasaearthimagerydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get button and inputs
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button favouritesButton = (Button) findViewById(R.id.favouritesButton);
        EditText lonInput = (EditText) findViewById(R.id.LonInput);
        EditText latInput = (EditText) findViewById(R.id.LatInput);

        //Onclick open image result activity
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Create intent to pass inputs to next activity
                Intent imageResultInt = new Intent(MainActivity.this, ImageResult.class);
                imageResultInt.putExtra("Lon", lonInput.getText().toString());
                imageResultInt.putExtra("Lat", latInput.getText().toString());
                startActivity(imageResultInt);
            }
        });

        favouritesButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Create intent to pass inputs to next activity
                Intent favouriteInt = new Intent(MainActivity.this, Favourites.class);
                startActivity(favouriteInt);
            }
        });
    }
}