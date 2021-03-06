package com.project.charmander.picturies;


import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.charmander.picturies.fragments.CreateReportActivity;
import com.project.charmander.picturies.fragments.FriendsActivity;
import com.project.charmander.picturies.fragments.ImageDetailViewActivity;
import com.project.charmander.picturies.fragments.ImageListViewActivity;
import com.project.charmander.picturies.fragments.ReadReportActivity;
import com.project.charmander.picturies.fragments.SettingsActivity;
import com.project.charmander.picturies.helper.PictureHelper;
import com.project.charmander.picturies.helper.UserSessionManager;
import com.project.charmander.picturies.model.Picture;
import com.project.charmander.picturies.model.Roadtrip;
import com.project.charmander.picturies.model.User;
import com.project.charmander.picturies.styles.RoundImage;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends ActionBarActivity implements LocationListener{
    public static final String TAG=MainActivity.class.getSimpleName();

    //Navigation
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerContent;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    MenuItem listIcon;

    //Maps
    private GoogleMap mMap;
    private String provider;
    private LocationManager locationManager;
    private Double Lat = 48.050101;
    private Double Lng = 8.210285;
    boolean mapOpen = true;

    //Fragments
    final Fragment createReport = new CreateReportActivity();
    final Fragment readReport = new ReadReportActivity();
    final Fragment friends = new FriendsActivity();
    final Fragment settings = new SettingsActivity();
    final Fragment imageDetailView = new ImageDetailViewActivity();
    final Fragment imageListView = new ImageListViewActivity();

    //Kamera
    final Context contextForAddPicture = this;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private Dialog addPictureDialog;
    private Dialog addPictureFromCameraDialog;

    //Login
    UserSessionManager session;
    public static User mCurrentUser;

    //Styling
    Display display;
    Point size = new Point();
    int width;
    int height;
    ImageView toRoundPicture;
    TextView userName;
    TextView anzahlPictures;
    RoundImage roundedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new UserSessionManager(getApplicationContext());

        if(session.checkLogin()){
            finish();
        }

        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(UserSessionManager.KEY_NAME);
        String email = user.get(UserSessionManager.KEY_EMAIL);
        String password = user.get(UserSessionManager.KEY_PASSWORD);
        String userId = "org.couchdb.user:"+name;

        mCurrentUser = new User(userId, name, email, password);



        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerContent = (RelativeLayout) findViewById(R.id.drawer_content);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(false);
        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        locationManager.requestLocationUpdates(provider, 400, 1, this);

        try {
            Lat = location.getLatitude ();
            Lng = location.getLongitude ();
        }
        catch (NullPointerException e){
            Log.e(TAG, e.getMessage());
        }

        addDrawerItems();
        setupDrawer();
        setUpMapIfNeeded();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a31258")));

        ImageView toCameraButton = (ImageView) findViewById(R.id.camera_button);
        toCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPictureFromCamera();
            }
        });

        // listener on long map click for gallery picture add
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                addPictureFromGallery(point);
//                Toast.makeText(getBaseContext(), point.toString(), Toast.LENGTH_LONG).show();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                mapOpen = false;
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, imageDetailView).commit();
//                Toast.makeText(getBaseContext(), marker.toString(), Toast.LENGTH_LONG).show();
                String posInArray = marker.getSnippet();
                int position = Integer.parseInt(posInArray);

                Bundle args = new Bundle();
                args.putInt("position", position);
                imageDetailView.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, imageDetailView).commit();
                return false;
            }
        });

        getScreenDimensions();
        getRoadtripInfoFromDatabase();



    }

    private void getScreenDimensions() {
        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    private void addDrawerItems() {

        toRoundPicture = (ImageView) findViewById(R.id.profile_picture);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.ruth);
        roundedImage = new RoundImage(bm);
        toRoundPicture.setImageDrawable(roundedImage);

        userName = (TextView) findViewById(R.id.profile_name);
        userName.setText(mCurrentUser.getUsername());

        String[] osArray = {"Bericht erstellen", "Berichte lesen", "Freunde", "Einstellungen", "Logout"};
        mAdapter = new ArrayAdapter<String>(this, R.layout.navigation_list_item, R.id.navigation_name, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        mapOpen = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, createReport).commit();
                        mDrawerLayout.closeDrawer(mDrawerContent);
                        break;
                    case 1:
                        mapOpen = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, readReport).commit();
                        mDrawerLayout.closeDrawer(mDrawerContent);
                        break;
                    case 2:
                        mapOpen = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, friends).commit();
                        mDrawerLayout.closeDrawer(mDrawerContent);
                        break;
                    case 3:
                        mapOpen = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, settings).commit();
                        mDrawerLayout.closeDrawer(mDrawerContent);
                        break;
                    case 4:
                        mapOpen = false;
                        session.logoutUser();
                        break;
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom, menu);
        listIcon = menu.findItem(R.id.map_Change);
        updateActionBarIcon();
        return true;
    }

    public void updateActionBarIcon() {
        if(!mapOpen) {
            listIcon.setIcon(R.drawable.map);
        } else {
            listIcon.setIcon(R.drawable.liste);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.map_Change) {
            if(!mapOpen) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                updateActionBarIcon();
                return true;
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, imageListView).commit();
                mapOpen = false;
                updateActionBarIcon();
            }
        }

        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);
            //getInfoAboutMarkerFromDatabase();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //icon = bild
        //title = headline
        //snippet = additional information
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 15.0f));
        getInfoAboutMarkerFromDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
//        String str = "Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude();
//        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
        Lat = location.getLatitude();
        Lng = location.getLongitude();
        Float zoom = mMap.getCameraPosition().zoom;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), zoom));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "GPS turned on", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "GPS turned off", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // result for picture add from gallery
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) addPictureDialog.findViewById(R.id.selectedImage);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

        // result for picture add from camera
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            ImageView addImage = (ImageView) addPictureFromCameraDialog.findViewById(R.id.selectedImage);
//            titel = (EditText) addPictureDialog.findViewById(R.id.editText);
           //TODO: Bilderegröße
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            addImage.setImageBitmap(photo);
        }
    }

    private void addPictureFromGallery(final LatLng point) {
        addPictureDialog = new Dialog(contextForAddPicture);
        addPictureDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        addPictureDialog.setContentView(R.layout.on_map_add_picture_dialog);
        addPictureDialog.getWindow().setLayout(width - 20, height - 20);

        final EditText title = (EditText) addPictureDialog.findViewById(R.id.pictureTitleEditText);
        final EditText description = (EditText) addPictureDialog.findViewById(R.id.pictureDescriptionEditText);
        final ImageView addImage = (ImageView) addPictureDialog.findViewById(R.id.selectedImage);

        Button saveButton = (Button) addPictureDialog.findViewById(R.id.speichern_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleInput = title.getText().toString();
                String descriptionInput = description.getText().toString();

                Bitmap imageInput = ((BitmapDrawable) addImage.getDrawable()).getBitmap();
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageInput, 175, 175, false);
                Bitmap resizedBitmapForDB = Bitmap.createScaledBitmap(imageInput, 120, 80, false);
                Marker marker = mMap.addMarker(new MarkerOptions().position(point).title(titleInput).snippet(descriptionInput).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
                double lat = marker.getPosition().latitude;
                double lng = marker.getPosition().longitude;

                addPictureDialog.dismiss();
                Toast.makeText(getBaseContext(), "Bild hinzugefügt", Toast.LENGTH_LONG).show();

                //Datenbank

                sendImageToDatabase(titleInput, descriptionInput, lat, lng, resizedBitmapForDB); //resizedBitmap/resizedBitmapForDB statt imageInput
            }
        });

        Button cancelButton = (Button) addPictureDialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPictureDialog.dismiss();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(contextForAddPicture, R.anim.image_click));
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        addPictureDialog.show();
    }

    private void addPictureFromCamera() {
        addPictureFromCameraDialog = new Dialog(contextForAddPicture);
        addPictureFromCameraDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addPictureFromCameraDialog.setContentView(R.layout.on_map_add_picture_dialog);
        addPictureFromCameraDialog.getWindow().setLayout(width-20, height-20);

        Button saveButton = (Button) addPictureFromCameraDialog.findViewById(R.id.speichern_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titel = (EditText) addPictureFromCameraDialog.findViewById(R.id.pictureTitleEditText);
                EditText description = (EditText) addPictureFromCameraDialog.findViewById(R.id.pictureDescriptionEditText);
                ImageView addImage = (ImageView) addPictureFromCameraDialog.findViewById(R.id.selectedImage);
                String titleInput = titel.getText().toString();
                String descriptionInput = description.getText().toString();
                Bitmap imageInput = ((BitmapDrawable) addImage.getDrawable()).getBitmap();
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageInput, 175, 175, false);
                Bitmap resizedBitmapForDB = Bitmap.createScaledBitmap(imageInput, 120, 80, false);

                //TODO: WIIIIICHTIG!!!!
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Lat, Lng)).title(titleInput).snippet(descriptionInput).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
                double lat = marker.getPosition().latitude;
                double lng = marker.getPosition().longitude;

                addPictureFromCameraDialog.dismiss();
                Toast.makeText(getBaseContext(), "Bild hinzugefügt", Toast.LENGTH_LONG).show();

                //Datenbank
                sendImageToDatabase(titleInput, descriptionInput, lat, lng, resizedBitmapForDB);
            }
        });

        Button cancelButton = (Button) addPictureFromCameraDialog.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPictureFromCameraDialog.dismiss();
            }
        });

        addPictureFromCameraDialog.show();

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, CAMERA_REQUEST);
    }

    public void sendImageToDatabase(String title, String description, double latitude, double longitude, Bitmap picture){

        //TODO: Abfangen kein Bild!
        //TODO: ProgressSpinner
        UUID id = UUID.randomUUID();
        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();

        Picture uploadedPicture = new Picture(id, title, mCurrentUser, currentDate, latitude, longitude, picture, description);
        String json = uploadedPicture.generateJson();
        //TODO: Bild in Array einfügen

        final String databaseURL ="http://charmander.iriscouch.com/pictures/"+id.toString();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(JSON, json);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(databaseURL)
                .header("Content-Type", "application/json")
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
                    if(response.code() == 201){
                        Log.d(TAG, response.body().string());
                    }
                }
                else{
                    Log.d(TAG,response.toString() + "ResponseBody: " +response.body().string());
                }
            }
        });

    }

    public void getInfoAboutMarkerFromDatabase() {

        mCurrentUser.setPictures(new ArrayList<Picture>());


        String parameter = "";
        try {
            parameter = URLEncoder.encode("\""+mCurrentUser.getUserId()+"\"", "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String databaseURL = "http://charmander.iriscouch.com/pictures/_design/mypictures/_view/mypictures?key="+parameter;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(databaseURL)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "Request gescheitert in getInfoAboutMarkerFromDatabase()");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                if(response.isSuccessful()){
                    String jsonData = response.body().string();

                    //TODO: JSON auslesen & in Pictures schreiben, Pictures set Marker in runonGui

                    try {
                        JSONObject mypictures = new JSONObject(jsonData);
                        JSONArray rows = mypictures.getJSONArray("rows");

                        for(int i=0; i < rows.length(); i++){
                            JSONObject pictures = rows.getJSONObject(i);
                            JSONObject picture = pictures.getJSONObject("value");


                            UUID id = UUID.fromString(picture.getString("_id"));
                            final String title = picture.getString("title");
                            User creator = mCurrentUser;

                            DateFormat format = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss z");
                            Date created = format.parse(picture.getString("created"));

                            double latitude = picture.getDouble("latitude");
                            double longitude = picture.getDouble("longitude");
                            String description = picture.getString("description");

                            setMarker(id, title, creator, created, latitude, longitude, description);

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Log.d(TAG, response.toString());
                }

            }
        });

    }

    private void setMarker(final UUID id, final String title, final User creator, final Date created, final double latitude, final double longitude, final String description) {
        String imageUrl = "http://charmander.iriscouch.com/pictures/"+id.toString()+"/"+id.toString()+".png";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "Request gescheitert");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                if(response.isSuccessful()){
                    Picture picture = new Picture(id,title, creator, created, latitude, longitude, bitmap, description);
                    mCurrentUser.addPictureToList(picture);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Bitmap resizedImage = Bitmap.createScaledBitmap(bitmap, 175, 175, false);
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(title).snippet(mCurrentUser.getPictures().size() - 1 + "").icon(BitmapDescriptorFactory.fromBitmap(resizedImage)));

                            anzahlPictures = (TextView) findViewById(R.id.profile_upload_pictures);
                            anzahlPictures.setText(mCurrentUser.getPictures().size()+ " Bilder");
                        }
                    });

                }
            }
        });
    }



     public void getRoadtripInfoFromDatabase() {

        mCurrentUser.setRoadtrips(new ArrayList<Roadtrip>());


        String parameter = "";
        try {
            parameter = URLEncoder.encode("\"" + mCurrentUser.getUserId() + "\"", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String databaseURL = "http://charmander.iriscouch.com/roadtrips/_design/myroadtrips/_view/myroadtrips?key="+parameter;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(databaseURL)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "Request gescheitert in getRoadtripInfoFromDatabase()");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                if(response.isSuccessful()){
                    String jsonData = response.body().string();

                    try {
                        JSONObject myroadtrips = new JSONObject(jsonData);
                        JSONArray rows = myroadtrips.getJSONArray("rows");

                        for(int i=0; i < rows.length(); i++){
                            JSONObject roadtrips = rows.getJSONObject(i);
                            JSONObject roadtrip = roadtrips.getJSONObject("value");


                            UUID id = UUID.fromString(roadtrip.getString("_id"));
                            final String title = roadtrip.getString("title");
                            User creator = mCurrentUser;

                            DateFormat format = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss z");
                            Date created = format.parse(roadtrip.getString("created"));

                            String description = roadtrip.getString("description");
                            JSONArray pictureIds= roadtrip.getJSONArray("pictures");

                            ArrayList<String> pIds = new ArrayList<String>();

                            for (int j = 0; j < pictureIds.length(); j++) {
                                JSONObject row = pictureIds.getJSONObject(j);
                                String pictureIdAsString = row.getString("id");
                                pIds.add(pictureIdAsString);
                            }

                            Roadtrip roadtripFromDatabase = new Roadtrip(id, title, creator, created, pIds);
                            mCurrentUser.addRoadtripToList(roadtripFromDatabase);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            TextView anzahlRoadtrips = (TextView) findViewById(R.id.profile_report);
                            anzahlRoadtrips.setText(mCurrentUser.getRoadtrips().size()+ " Berichte");
                        }
                    });

                }
                else {
                    Log.d(TAG, response.toString());
                }

            }
        });

    }


    public static User getCurrentUser(){
        return mCurrentUser;
    }

}

//TODO: network available




