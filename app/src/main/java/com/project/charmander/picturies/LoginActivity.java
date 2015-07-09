package com.project.charmander.picturies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.charmander.picturies.helper.UserSessionManager;
import com.project.charmander.picturies.model.User;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class LoginActivity extends AppCompatActivity {
    public static final String TAG=LoginActivity.class.getSimpleName();

    protected TextView mSignUpTextView;
    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLoginButton;

    // User Session Manager Class
    UserSessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new UserSessionManager(getApplicationContext());

        mSignUpTextView = (TextView) findViewById(R.id.signUpText);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        mUsername = (EditText) findViewById(R.id.usernameField);
        mPassword = (EditText) findViewById(R.id.passwordField);
        mLoginButton = (Button) findViewById(R.id.loginButton);

        //Clicklistener LoginButton
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                username = username.trim();
                password = password.trim();

                if(username.isEmpty() || password.isEmpty() ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.login_error_message)
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    //Login-Absatz

                    if(isNetworkAvailable()){
                        //then login
                        login(username, password);
                    }
                    else{
                        //TODO: Konzept, Login soll auch ohne internet gehen, wegen URlaub und so
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.network_unavailable_error_message)
                                .setTitle(R.string.network_unavailable_error_title)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }

            }
        });
    }

    private void login(final String username, final String password) {

        final String databaseURL = "http://charmander.iriscouch.com/_session";

        final RequestBody requestBody = new FormEncodingBuilder()
                .add("name", username)
                .add("password", password)
                .build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(databaseURL)
                .post(requestBody)
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
                    if(response.code() == 200){
                        loginSucess(username, password);
                    }
                }
                else{
                    if(response.code() == 401){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loginFailed();
                            }
                        });
                    }

                    Log.d(TAG,response.toString() + "ResponseBody: " +response.body().string());
                }
            }
        });

    }

    private void loginFailed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(R.string.login_unauthorized_error_message)
                .setTitle(R.string.login_unauthorized_error_title)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();

        mPassword.setText("");
    }

    public void loginSucess(final String username, final String password){
        //Set User in MainActivity with the data from CouchDB
        //TODO: CONSTANTE DATEBASEURL
        String databaseURL = "http://charmander.iriscouch.com/_users/org.couchdb.user:"+username;

        String credential = Credentials.basic(username, password);

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(databaseURL)
                .header("Authorization", credential)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "Request gescheitert");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                if (response.isSuccessful()) {

                    String jsonData = response.body().string();

                    try {
                        JSONObject jsonUser = new JSONObject(jsonData);

                        //TODO: Infos wie Bilder, Friends, Profilbild fehlen noch
                        String serverId = jsonUser.getString("_id");
                        String email = jsonUser.getString("email");

                        User currentUser = new User(serverId, username, email, password);
                        //TODO: putExtra User

                        session.createUserLoginSession(username,email,password);

                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //Log.d(TAG, jsonData);


                } else {
                    Log.d(TAG, response.toString() + response.body().string());
                    Log.d(TAG, request.toString());
                }

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    //TODO: evtl. auslagern
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
}
