package com.project.charmander.picturies;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.project.charmander.picturies.helper.UserSessionManager;
import com.project.charmander.picturies.model.User;

import java.util.HashMap;

public class MainActivity extends ActionBarActivity implements LocationListener{
    public static final String TAG=MainActivity.class.getSimpleName();

    //Navigation
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    //Maps
    private GoogleMap mMap;
    private String provider;
    private LocationManager locationManager;
    private Double Lat = 47.61;
    private Double Lng = 7.61;
    ImageView addImage;
    boolean mapOpen = true;

    //Fragments
    final Fragment createReport = new CreateReportActivity();
    final Fragment readReport = new ReadReportActivity();
    final Fragment friends = new FriendsActivity();
    final Fragment settings = new SettingsActivity();
    final Fragment imageDetailView = new ImageDetailViewActivity();
    final Fragment imageListView = new ImageListViewActivity();

    //?
    EditText titel;

    //Kamera
    final Context contextForAddPicture = this;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private Dialog addPictureDialog;
    private Dialog addPictureFromCameraDialog;

    //Login
    UserSessionManager session;
    public User mCurrentUser;


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

        Log.d(TAG, name + email);


        mDrawerList = (ListView) findViewById(R.id.navList);
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
            e.printStackTrace();
        }

        addDrawerItems();
        setupDrawer();
        setUpMapIfNeeded();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a31258")));

        ImageButton toCameraButton = (ImageButton) findViewById(R.id.camera_button);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, imageDetailView).commit();
                Toast.makeText(getBaseContext(), marker.toString(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void addDrawerItems() {
        String[] osArray = {"Bericht erstellen", "Berichte lesen", "Freunde", "Einstellungen", "Logout"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        mapOpen = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, createReport).commit();
                        break;
                    case 1:
                        mapOpen = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, readReport).commit();
                        break;
                    case 2:
                        mapOpen = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, friends).commit();
                        break;
                    case 3:
                        mapOpen = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, settings).commit();
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
                getSupportActionBar().setTitle("Navigation!");
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.map_Change) {
            if(!mapOpen) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, imageListView).commit();
                mapOpen = false;
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
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 15.0f));
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
        String str = "Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude();
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
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

        // result for picture add from gallery
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            addImage = (ImageView) addPictureFromCameraDialog.findViewById(R.id.selectedImage);
            titel = (EditText) addPictureDialog.findViewById(R.id.editText);
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            addImage.setImageBitmap(photo);
        }
    }

    private void addPictureFromGallery(final LatLng point) {
        addPictureDialog = new Dialog(contextForAddPicture);
        addPictureDialog.setContentView(R.layout.on_map_add_picture_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addPictureDialog.setTitle("Erinnerung hinzufügen");

        final EditText titel = (EditText) addPictureDialog.findViewById(R.id.editText);
        final ImageView addImage = (ImageView) addPictureDialog.findViewById(R.id.selectedImage);

        Button saveButton = (Button) addPictureDialog.findViewById(R.id.speichern_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titelInput = titel.getText().toString();
                Bitmap imageInput = ((BitmapDrawable) addImage.getDrawable()).getBitmap();
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageInput, 128, 128, false);
                mMap.addMarker(new MarkerOptions().position(point).title(titelInput).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
                addPictureDialog.dismiss();
                Toast.makeText(getBaseContext(), "Bild hinzugefügt", Toast.LENGTH_LONG).show();
                // den ganzen Scheiss in die DB
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
        addPictureFromCameraDialog.setContentView(R.layout.on_map_add_picture_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addPictureFromCameraDialog.setTitle("Erinnerung hinzufügen");

        Button saveButton = (Button) addPictureFromCameraDialog.findViewById(R.id.speichern_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText titel = (EditText) addPictureFromCameraDialog.findViewById(R.id.editText);
                final ImageView addImage = (ImageView) addPictureFromCameraDialog.findViewById(R.id.selectedImage);
                String titelInput = titel.getText().toString();
                Bitmap imageInput = ((BitmapDrawable) addImage.getDrawable()).getBitmap();
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageInput, 128, 128, false);

                mMap.addMarker(new MarkerOptions().position(new LatLng(Lat, Lng)).title(titelInput).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
                addPictureFromCameraDialog.dismiss();
                Toast.makeText(getBaseContext(), "Bild hinzugefügt", Toast.LENGTH_LONG).show();
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
}




