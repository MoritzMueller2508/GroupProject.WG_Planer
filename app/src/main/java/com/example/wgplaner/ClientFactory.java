package com.example.wgplaner;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;

public class ClientFactory extends AppCompatActivity {
    private static volatile AWSAppSyncClient client;

    public static synchronized void init(final Context context) {
       // CognitoUserPool mCognitoUserPool = new CognitoUserPool(getApplicationContext(), new AWSConfiguration(getApplicationContext()));
        //BasicCognitoUserPoolsAuthProvider basicCognitoUserPoolsAuthProvider = new BasicCognitoUserPoolsAuthProvider(mCognitoUserPool);
        if (client == null) {
            final AWSConfiguration awsConfiguration = new AWSConfiguration(context);
            client = AWSAppSyncClient.builder()
                    .context(context)
                    .awsConfiguration(awsConfiguration)
                    .cognitoUserPoolsAuthProvider(() -> {
                        try {
                            return AWSMobileClient.getInstance().getTokens().getIdToken().getTokenString();
                        } catch (Exception e){
                            Log.e("APPSYNC_ERROR", e.getLocalizedMessage());
                            return e.getLocalizedMessage();
                        }
                    })
                    .build();
        }
    }

    public static synchronized AWSAppSyncClient appSyncClient() {
        return client;
    }
}
