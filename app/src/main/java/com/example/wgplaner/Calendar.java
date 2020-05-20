package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Calendar extends AppCompatActivity {

    Button addEvent;
    CalendarView calendar;
    ListView eventList;
    List<String> events = CalendarAddEvent.returnEvents();
    public static List<String> sorted = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        eventList = (ListView) findViewById(R.id.lv_events);
        addEvent = (Button) findViewById(R.id.btn_addEvent);
        calendar = (CalendarView) findViewById(R.id.cv_calendar);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                events);
        for (int i = 0; i < events.size(); i++){

            String[] x = events.get(i).toString().split(" ");
           // if x[]



        };


        eventList.setAdapter(arrayAdapter);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, CalendarAddEvent.class));
            }
        });
    }
}
