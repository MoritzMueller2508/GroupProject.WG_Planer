package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.amplify.generated.graphql.GetWgQuery;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

public class AccessWg extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;
    private EditText textInputCode;
    public MainActivity firstLayout = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_wg);


        textInputCode = findViewById(R.id.et_wgCode);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        AccessWgToMyWg();
    }

    private boolean validateEmail() {

        String codeInput = textInputCode.getText().toString().trim();
        if (codeInput.equals("#ABCDEF")) {
            textInputCode.setError(null);
            return true;
        } else {
            textInputCode.setError("Code is wrong!");
            return false;
        }
    }

    public void AccessWgToMyWg() {
        Button navAccessWg_To_MyWg = (Button) findViewById(R.id.btn_searchForWg);
        navAccessWg_To_MyWg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  runQuery();

                if (validateEmail()) {
                    startActivity(new Intent(AccessWg.this, MyWg.class));
                    //firstLayout.setAccess(1);
                } else {
                    return;
                }
            }
        });

    }


    final String id = ((EditText) findViewById(R.id.et_wgCode)).getText().toString();

    public void runQuery(){
        mAWSAppSyncClient.query(GetWgQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(queryCallback);
    }

    private GraphQLCall.Callback<GetWgQuery.Data> queryCallback = new GraphQLCall.Callback<GetWgQuery.Data>() {

        @Override
        public void onResponse(@Nonnull Response<GetWgQuery.Data> response) {
            assert response.data() != null;
            Log.i("Results", response.data().getWG().toString());
            startActivity(new Intent(AccessWg.this, MyWg.class));
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }
    };
}
