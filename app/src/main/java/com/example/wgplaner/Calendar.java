package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Calendar extends AppCompatActivity {

    Button addEvent;
    CalendarView calendar;
    ListView eventList;
    ArrayList events;
    //List<String> events = CalendarAddEvent.returnEvents();
    public static List<String> sorted = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        eventList = (ListView) findViewById(R.id.lv_events);
        addEvent = (Button) findViewById(R.id.btn_addEvent);
        calendar = (CalendarView) findViewById(R.id.cv_calendar);
        //ArrayList events = CalendarAddEvent.returnArrayList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
               events);

       /** final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());**/



        //eventList.setAdapter(arrayAdapter);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, CalendarAddEvent.class));
            }
        });

    }
}
