package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.amplify.generated.graphql.CreateCalendarMutation;
import com.amazonaws.amplify.generated.graphql.CreateShoppingListMutation;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import type.CreateCalendarInput;
import type.CreateShoppingListInput;

import static com.example.wgplaner.AccessWg.wgCode;

public class CalendarAddEvent extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;

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

    public void runMutation(){
        //final String name = ((TextInputEditText) findViewById(R.id.ti_addItem)).getText().toString();
        //final String value = ((TextInputEditText) findViewById(R.id.ti_addValue)).getText().toString();
        CreateCalendarInput createCalendarInput = CreateCalendarInput.builder()
                .wgID(wgCode)
                .events()
                .date()
                .time()
                .build();

        mAWSAppSyncClient.mutate(CreateCalendarMutation.builder().input(createCalendarInput).build())
                .enqueue(mutationCallback);

    }
    private GraphQLCall.Callback<CreateCalendarMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateCalendarMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateCalendarMutation.Data> response) {
            Log.i("Results", "Added Event");
            //runQuery();
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

    public static List<String> returnEvents(){
        return events;
    }
}
