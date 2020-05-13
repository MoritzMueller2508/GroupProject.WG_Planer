package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.amplify.generated.graphql.GetWgQuery;
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
import com.google.android.material.snackbar.Snackbar;

import javax.annotation.Nonnull;

public class AccessWg extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;
    private TextView constraintLayout;
    public static String idWgCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_wg);
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

//        textInputCode = findViewById(R.id.et_wgCode);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        constraintLayout = (TextView) findViewById(R.id.errorAccessWg);
        AccessWgToMyWg();
    }

    public void AccessWgToMyWg() {
        Button navAccessWg_To_MyWg = (Button) findViewById(R.id.btn_searchForWg);
        navAccessWg_To_MyWg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runQuery();

            }
        });

    }

    public void runQuery(){
        String id = ((EditText) findViewById(R.id.et_wgCode)).getText().toString();

        mAWSAppSyncClient.query(GetWgQuery.builder().id(id).build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(queryCallback);
    }

    private GraphQLCall.Callback<GetWgQuery.Data> queryCallback = new GraphQLCall.Callback<GetWgQuery.Data>() {

        @Override
        public void onResponse(@Nonnull Response<GetWgQuery.Data> response) {

            if (response.data().getWG() != null) {
                Log.i("Results", response.data().getWG().toString());
                idWgCode = "WG Code: " + response.data().getWG().id();
                startActivity(new Intent(AccessWg.this, MyWg.class));
            }
            else {
                Snackbar.make(constraintLayout, "Ups, wrong code!", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }
    };

}
