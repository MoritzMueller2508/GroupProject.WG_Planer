package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wgplaner.Calendar;
import com.example.wgplaner.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarAddEvent extends AppCompatActivity {

    Button getInformation;
    EditText eventName;
    EditText date;
    EditText time;
    public static List<String> events = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_add_event);


        eventName = (EditText) findViewById(R.id.et_eventName);
        date = (EditText) findViewById(R.id.et_date);
        time = (EditText) findViewById(R.id.et_dateTime);

        getInformation = (Button) findViewById(R.id.btn_addEventToArray);
        getInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventNameS = eventName.getText().toString();
                String dateS = date.getText().toString();
                String timeS = time.getText().toString();


                events.add(eventNameS + " " +dateS+ " "+  timeS);
                startActivity(new Intent(CalendarAddEvent.this, Calendar.class));


            }


        });

    }

    public static List<String> returnEvents(){
        return events;
    }
}
