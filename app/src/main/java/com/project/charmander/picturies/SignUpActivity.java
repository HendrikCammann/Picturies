package com.project.charmander.picturies;

import android.content.Context;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.charmander.picturies.helper.UserSessionManager;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class SignUpActivity extends AppCompatActivity {
    public static final String TAG=SignUpActivity.class.getSimpleName();
    public static final MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");

    protected EditText mUsernameEditText;
    protected EditText mPasswordEditText;
    protected EditText mEmailEditText;
    protected Button mSignUpButton;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        session = new UserSessionManager(getApplicationContext());

        mUsernameEditText = (EditText) findViewById(R.id.usernameField);
        mPasswordEditText = (EditText) findViewById(R.id.passwordField);
        mEmailEditText = (EditText) findViewById(R.id.emailField);
        mSignUpButton = (Button) findViewById(R.id.signUpButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String email = mEmailEditText.getText().toString();
                username = username.trim();
                password = password.trim();
                email = email.trim();

                register(username,password,email);
            }
        });
    }


    protected void register(final String username, final String password, final String email){

        if(isNetworkAvailable()){

            String databaseURL = "http://Charmander:charmander@charmander.iriscouch.com/_users/org.couchdb.user:"+username;
            String parameters = "{\"name\": \""+username+"\", \"password\": \""+password+"\", \"roles\": [], \"type\": \"user\", \"email\": \""+email+"\"}";
            RequestBody requestBody = RequestBody.create(mediaType, parameters);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(databaseURL)
                    .put(requestBody)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.d(TAG, "Request gescheitert");
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    if(response.isSuccessful()){
                        Log.d(TAG,response.toString());

                        session.createUserLoginSession(username,email,password);

                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();

                    }
                    else{
                        Log.d(TAG,response.toString());
                    }
                }
            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

//TODO: Weiterleitung auf MAIN
//TODO: ProgressSpinner
//TODO: AlertDialoge einbauen
//TODO: Ribbit vergleichen, da fehlt z.B. der Check ob was eingegebn worden ist