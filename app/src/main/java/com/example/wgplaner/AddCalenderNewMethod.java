/**package com.example.wgplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;


public class AddCalenderNewMethod extends AppCompatActivity {

    Button getInformation;
    EditText eventName;
    EditText date;
    EditText time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_add_event);

        eventName = (EditText) findViewById(R.id.et_eventName);
        date = (EditText) findViewById(R.id.et_date);
        time = (EditText) findViewById(R.id.et_dateTime);

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().
                withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://sns.eu-central-1.amazonaws.com", "eu-central-1"))
                .build();

        DynamoDB db = new DynamoDB(client);
        Table table = db.getTable("Calendar-7el2xb2shrdvrc5fczu5i3godu-dev");

        Button getAddItem = (Button) findViewById(R.id.btn_addEventToArray);

        getAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eventName.getText().toString();
                String date_ = date.getText().toString();
                String time_ = time.getText().toString();

                final Map<String, Object> infoMap = new HashMap<String, Object>();
                infoMap.put("Date", date_);
                infoMap.put("Time", time_);

                try{
                    System.out.println("Adding a new item...");
                    PutItemOutcome outcome = table
                            .putItem(new Item().withPrimaryKey("name", name).withMap("info", infoMap));

                    System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());


                }
                catch (Exception e){

                    System.err.println("Unable to add item:" + name + " " + infoMap);
                    System.err.println(e.getMessage());
                }
            }
        });

    }
}
**/