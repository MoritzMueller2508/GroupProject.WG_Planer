package com.example.wgplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;
import com.amazonaws.services.securitytoken.model.Tag;
import com.google.android.material.tabs.TabLayout;


public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);






       //CreateAccountToMenu();
    }
/**
    public void CreateAccountToMenu(){

        Button navCreateAcc_To_MainMenu = (Button) findViewById(R.id.btn_createAcc);
        navCreateAcc_To_MainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, MainMenu.class));
            }
        });

    }
**/



    public String[] GetAccountDetails(){

        Button getData = (Button) findViewById(R.id.btn_createAcc);
        final String[] data = new String[2];
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data[0] = String.valueOf((EditText) findViewById(R.id.ti_accountEmail));
                data[1] = String.valueOf((EditText) findViewById(R.id.ti_accountPwd));
                // data[2] = String.valueOf((EditText) findViewById(R.id.ti_accountName));


                // setup AWS service configuration. Choosing default configuration
                ClientConfiguration clientConfiguration = new ClientConfiguration();

                // Create a CognitoUserPool object to refer to your user pool

                CognitoUserPool userPool = new CognitoUserPool(
                        context   ,
                        "eu-central-1_HA50ECxEg",
                        "1vlmpatoclg7be762baijdu090",
                        "ect5olnn8434jkuq8pqm6h1nn8rv91ehu54937chsr0eamimd5k",
                        clientConfiguration
                );

                //EditText name = (EditText) findViewById(R.id.ti_accountName);


                //String[] data = new String[3];

                //data = GetAccountDetails();


                // Create a CognitoUserAttributes object and add user attributes
                CognitoUserAttributes userAttributes = new CognitoUserAttributes();

                SignUpHandler signupCallback = new SignUpHandler() {

                    @Override
                    public void onSuccess(CognitoUser user, SignUpResult isUserConfirmed) {
                        // Sign-up was successful
                        Log.i( "sign up", "Sign up success ... is not confirmed yet");

                        // Check if this user (cognitoUser) has to be confirmed
                        if(isUserConfirmed.equals(false)) {

                            Log.i("Confirm user", "Sign up confirmation is sent to your email; User is not confirmed yet ");
                            // This user has to be confirmed and a confirmation code was sent to the user
                            // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                            // Get the confirmation code from user
                        }
                        else {
                            Log.i("Sign Up success... confirmed", "Sign Up was a success; User is confirmed");
                            // The user has already been confirmed
                        }
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        // Sign-up failed, check exception for the cause
                        Log.i("Failure", "Sign Up failure" + exception);
                    }
                };

                // Sign up this user
                userPool.signUpInBackground(data[0], data[1], userAttributes, null, signupCallback);

                // Call back handler for confirmSignUp API
                GenericHandler confirmationCallback = new GenericHandler() {

                    @Override
                    public void onSuccess() {
                        Log.i("onSuccess", "User was successfully confirmed");
                        // User was successfully confirmed
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Log.i("onFailure", "User confirmation failed. Check exception for the cause." + exception);
                        // User confirmation failed. Check exception for the cause.
                    }
                };
            }


        });
        return data;

    }

}
