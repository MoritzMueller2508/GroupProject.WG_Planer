package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.amplify.generated.graphql.CreateWgMutation;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.textfield.TextInputEditText;

import javax.annotation.Nonnull;

import type.CreateWGInput;

public class CreateWg extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_wg);
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

        final Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        CreateWgToMyWg();
    }

    public void CreateWgToMyWg(){
        Button navCreateWg_To_MyWg = (Button) findViewById(R.id.btn_createWg);
        navCreateWg_To_MyWg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runMutation();
            }
        });
    }

    public void runMutation(){
        final String name = ((TextInputEditText) findViewById(R.id.createWgName)).getText().toString();
        final String address = ((TextInputEditText) findViewById(R.id.createWgAdress)).getText().toString();
        final String city = ((TextInputEditText) findViewById(R.id.createWgCity)).getText().toString();
        final String plz = ((TextInputEditText) findViewById(R.id.createWgPlz)).getText().toString();
        CreateWGInput createWGInput = CreateWGInput.builder()
                .name(name)
                .address(address)
                .city(city)
                .plz(plz)
                .build();

        mAWSAppSyncClient.mutate(CreateWgMutation.builder().input(createWGInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreateWgMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateWgMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateWgMutation.Data> response) {
            Log.i("Results", "Added Wg");
            startActivity(new Intent(CreateWg.this, MyWg.class));
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };
}
