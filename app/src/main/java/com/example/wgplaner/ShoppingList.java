package com.example.wgplaner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.amplify.generated.graphql.CreateShoppingListMutation;
import com.amazonaws.amplify.generated.graphql.ListShoppingListsQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import type.CreateShoppingListInput;

public class ShoppingList extends AppCompatActivity {

    private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        runMutation();
        AddShoppingListItem();
    }

    public void runMutation() {
        CreateShoppingListInput createShoppingListInput = CreateShoppingListInput.builder().
                itemName("Milch").
                value(1).
                build();

        mAWSAppSyncClient.mutate(CreateShoppingListMutation.builder().input(createShoppingListInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreateShoppingListMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateShoppingListMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateShoppingListMutation.Data> response) {
            Log.i("Results", "Added item");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }

    };

    public void runQuery() {
        mAWSAppSyncClient.query(ListShoppingListsQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(shoppingListCallback);
    }

    private GraphQLCall.Callback<ListShoppingListsQuery.Data> shoppingListCallback = new GraphQLCall.Callback<ListShoppingListsQuery.Data>() {

        @Override
        public void onResponse(@Nonnull Response<ListShoppingListsQuery.Data> response) {
            Log.i("Results", response.data().listShoppingLists().items().toString());

        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }
    };


    public void AddShoppingListItem(){

        Button addShoppingListItem = (Button) findViewById(R.id.btn_addShoppingListItem);
        addShoppingListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runQuery();
            }
        });
    }
}
