package com.example.wgplaner;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.amplify.generated.graphql.CreateCalendarMutation;

import com.amazonaws.amplify.generated.graphql.ListCalendarsQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import type.CreateCalendarInput;
import type.ModelCalendarFilterInput;
import type.ModelIDInput;

import static com.example.wgplaner.AccessWg.wgCode;

public class CalendarAddEvent extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;

    Button getInformation;
    EditText eventName;
    EditText date;
    EditText time;
    ListView eventList;
    public static List<String> events = new ArrayList<String>();

    //private AWSAppSyncClient mAWSAppSyncClient;
    MyAdapter mAdapter;
    public static ArrayList<ListCalendarsQuery.Item> mItems;
    public static ModelIDInput wgCodeIdInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_add_event);


        eventName = (EditText) findViewById(R.id.et_eventName);
        date = (EditText) findViewById(R.id.et_date);
        time = (EditText) findViewById(R.id.et_dateTime);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i("INIT", "onResult: " + userStateDetails.getUserState());
            }

            @Override
            public void onError(Exception e) {
                Log.e("INIT", "Initialization error.", e);
            }
        });
        CognitoUserPool mCognitoUserPool = new CognitoUserPool(getApplicationContext(), new AWSConfiguration(getApplicationContext()));
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .cognitoUserPoolsAuthProvider(new BasicCognitoUserPoolsAuthProvider(mCognitoUserPool))
                .build();

        /**final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());**/

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                events);




        getInformation = (Button) findViewById(R.id.btn_addEventToArray);
        getInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventNameS = eventName.getText().toString();
                String dateS = date.getText().toString();
                String timeS = time.getText().toString();
                runMutation();
                Log.e("After Mutation", "help2");


                events.add(eventNameS + " " +dateS+ " "+  timeS);
                //startActivity(new Intent(CalendarAddEvent.this, Calendar.class));


            }

        });

    }



    public void runMutation() {

        final String name = ((EditText) findViewById(R.id.et_eventName)).getText().toString();
        final String date_ = ((EditText) findViewById(R.id.et_date)).getText().toString();
        final String time_ = ((EditText) findViewById(R.id.et_dateTime)).getText().toString();

        CreateCalendarInput createCalendarInput = CreateCalendarInput.builder()
                .wgID(wgCode)
                .events(name)
                .date(date_)
                .time(time_)
                .build();

        System.out.println(name);
        System.out.println(date_);
        System.out.println(time_);

        mAWSAppSyncClient.mutate(CreateCalendarMutation.builder().input(createCalendarInput).build())
                .enqueue(mutationCallback);
        }


    private GraphQLCall.Callback<CreateCalendarMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateCalendarMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateCalendarMutation.Data> response) {
            Log.i("Results", "Added Event");

           // runQuery();
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

    public void runQuery(){
        wgCodeIdInput = ModelIDInput.builder().contains(wgCode).build();
        mAWSAppSyncClient.query(ListCalendarsQuery.builder()
                .filter(ModelCalendarFilterInput.builder().wgID(wgCodeIdInput).build())
                .build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(queryCallback);
    }

    private GraphQLCall.Callback<ListCalendarsQuery.Data> queryCallback = new GraphQLCall.Callback<ListCalendarsQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListCalendarsQuery.Data> response) {
            mItems = new ArrayList<>(response.data().listCalendars().items());
            Log.e("Results", mItems.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i<mItems.size(); i++){
                        System.out.println(mItems.get(i));

                    }
                }
            });
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }
    };

    public static ArrayList<ListCalendarsQuery.Item> returnArrayList(){
        Log.e("return", "returned the Items to Calendar");
        return mItems;

    }


   /** public static List<String> returnEvents(){
        return events;
    }**/
}
