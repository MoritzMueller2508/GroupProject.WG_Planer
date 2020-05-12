package com.example.wgplaner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.amplify.generated.graphql.CreateShoppingListMutation;
import com.amazonaws.amplify.generated.graphql.ListShoppingListsQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import type.CreateShoppingListInput;

public class ShoppingList extends AppCompatActivity {

    private AWSAppSyncClient mAWSAppSyncClient;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ListShoppingListsQuery.Item> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        CognitoUserPool mCognitoUserPool = new CognitoUserPool(getApplicationContext(), new AWSConfiguration(getApplicationContext()));
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .useClientDatabasePrefix(true)
                .cognitoUserPoolsAuthProvider(new BasicCognitoUserPoolsAuthProvider(mCognitoUserPool))
                .build();

        final Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        runQuery();

        Button btnAddItem = (Button) findViewById(R.id.btn_addShoppingListItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                runMutation();
            }
        });


        mRecyclerView = findViewById(R.id.list_show_ShoppingListItems);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Query list data when we return to the screen
        runQuery();
    }

    public void runMutation(){
        final String name = ((TextInputEditText) findViewById(R.id.ti_addItem)).getText().toString();
        final String value = ((TextInputEditText) findViewById(R.id.ti_addValue)).getText().toString();
        final int valueInt = Integer.parseInt(value);
        CreateShoppingListInput createShoppingListInput = CreateShoppingListInput.builder()
                .itemName(name)
                .value(valueInt)
                .build();

        mAWSAppSyncClient.mutate(CreateShoppingListMutation.builder().input(createShoppingListInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreateShoppingListMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateShoppingListMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateShoppingListMutation.Data> response) {
            Log.i("Results", "Added Item");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

    public void runQuery(){
        mAWSAppSyncClient.query(ListShoppingListsQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(queryCallback);
    }

    private GraphQLCall.Callback<ListShoppingListsQuery.Data> queryCallback = new GraphQLCall.Callback<ListShoppingListsQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListShoppingListsQuery.Data> response) {
            Log.i("Results", response.data().listShoppingLists().items().toString());
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }
    };


}
