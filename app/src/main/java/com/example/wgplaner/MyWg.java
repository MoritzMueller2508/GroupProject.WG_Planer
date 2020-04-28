package com.example.wgplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MyWg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wg);

        MyWG_To_Calendar();
        MyWG_To_News();
        MyWG_To_ShoppingList();
        MyWG_To_HouseholdDevices();
    }

    public void MyWG_To_Calendar(){

        ImageView navMyWG_To_Calendar = (ImageView)findViewById(R.id.iv_Calendar);
        navMyWG_To_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, Calendar.class));
            }
        });

    }

    public void MyWG_To_News(){

        ImageView navMyWG_To_News = (ImageView) findViewById(R.id.iv_News);
        navMyWG_To_News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, News.class));
            }
        });
    }

    public void MyWG_To_ShoppingList(){

        ImageView navMyWG_To_ShoppingList = (ImageView) findViewById(R.id.iv_ShoppingList);
        navMyWG_To_ShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, ShoppingList.class));
            }
        });

    }

    public void MyWG_To_HouseholdDevices(){

        ImageView navMyWG_To_HouseholdDevices = (ImageView) findViewById(R.id.iv_HouseholdDevices);
        navMyWG_To_HouseholdDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, HouseholdDevices.class));
            }
        });
    }
}

