package org.msucsclub.spirianbible;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    Firebase myFirebaseRef;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback();
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Firebase myFirebaseRef = new Firebase("https://spire-app-msu.firebaseio.com/");
        Log.d("app", "inside onCreate");
        LoginButton fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        fbLoginButton.setReadPermissions("user_events, user_friends");
        fbLoginButton.registerCallback(CallbackManager.Factory.create(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("app", "inside onSuccess of callback");
                onFacebookAccessTokenChange(loginResult.getAccessToken());
                connectToFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("app", "inside onSuccess of callback");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("app", "inside onError of callback");
                Log.d("app", "The error: " + error.toString());
            }
        });

        LinearLayout calendarLayout = (LinearLayout) findViewById(R.id.calendarLayout);
        LinearLayout tempLayout;
        LinearLayout.LayoutParams tempParams;

        String[] colors = { "#ffb5d6e1", "#20394018", "#482058a1", "#af492057", "#ab491041", "#d3104951", "#60294aa2"};

        for (int i = 0; i < 7; i++) {
            tempLayout = new LinearLayout(getApplicationContext());
            tempLayout.setBackgroundColor(Color.parseColor(colors[i]));
//            tempParams = (LinearLayout.LayoutParams) tempLayout.getLayoutParams();
            tempParams = new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth()/7, 800);
            tempLayout.setLayoutParams(tempParams);
            calendarLayout.addView(tempLayout);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public List<Event> getSampleEvents() {
        List<Event> events = new ArrayList<Event>();
        events.add(new Event(new Date(2016, 2, 28, 16, 0), new Date(2016, 2, 28, 18, 0), "Write Essay"));
        events.add(new Event(new Date(2016, 2, 28, 20, 0), new Date(2016, 2, 28, 22, 0), "Clean Room"));
        events.add(new Event(new Date(2016, 2, 29, 15, 30), new Date(2016, 2, 29, 18, 30), "Do Laundry"));
        events.add(new Event(new Date(2016, 2, 30, 16, 0), new Date(2016, 2, 30, 18, 0), "Go to CSC?"));
        return events;
    }

    public void connectToFacebook(AccessToken token) {

        Firebase.setAndroidContext(this);
        Firebase myFirebaseRef = new Firebase("https://spire-app-msu.firebaseio.com/");
        //myFirebaseRef.authWithOAuthToken("facebook", )
        Toast.makeText(getApplicationContext(), token.getUserId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private void onFacebookAccessTokenChange(AccessToken token) {
        final Button btn = (Button) findViewById(R.id.btn_connect_to_fb);
        Log.v("app", "inside onFacebookAccessTokenChange");
        if (token != null) {
            Log.d("app", "inside onFacebookAccessTokenChange - token is not null");
            myFirebaseRef.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    // The Facebook user is now authenticated with your Firebase app
                    btn.setText("authenticated");
                }
                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error
                    btn.setText("error");
                }
            });
        } else {
            Log.d("app", "inside onFacebookAccessTokenChange - token IS null");
        /* Logged out of Facebook so do a logout from the Firebase app */
            myFirebaseRef.unauth();
        }
    }

}
