package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MyWg extends AppCompatActivity {

    public MainActivity firstLayout = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wg);



        final Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyWg.this, MainMenu.class));
            }
        });

        firstLayout.setAccess(1);
        MyWgToShoppingList();
        MyWgToDevices();
        MyWgToCalendar();
        MyWgToNews();
    }

    private void MyWgToNews() {
        ImageButton navMyWg_To_News = (ImageButton) findViewById(R.id.btn_news);
        navMyWg_To_News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, News.class));
            }
        });
    }

    private void MyWgToCalendar() {
        ImageButton navMyWg_To_Calendar = (ImageButton) findViewById(R.id.btn_calendar);
        navMyWg_To_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, Calendar.class));
            }
        });
    }

    private void MyWgToDevices() {
        ImageButton navMyWg_To_Devices = (ImageButton) findViewById(R.id.btn_devices);
        navMyWg_To_Devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, HouseholdDevices.class));
            }
        });
    }

    public void MyWgToShoppingList(){

        ImageButton navMyWg_To_ShoppingList = (ImageButton) findViewById(R.id.btn_shoppingList);
        navMyWg_To_ShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, ShoppingList.class));
            }
        });

    }

}
