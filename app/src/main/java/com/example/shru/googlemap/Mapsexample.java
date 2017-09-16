package com.example.shru.googlemap;

/**
 * Created by Shru on 2/9/2016.
 */

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
/*import com.google.android.gms.maps.GeoPoint;
import com.google.android.gms.maps.Overlay;*/
import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
/*import com.google.android.gms.maps.GeoPoint;
import com.google.android.gms.maps.Overlay;*/
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by manoj on 2/1/2016.
 */
public class Mapsexample extends FragmentActivity implements LocationListener,View.OnClickListener {


    List<Overlay> mapOverlays;
    // GeoPoint point1, point2;
    LocationManager locManager;
    Drawable drawable;
    Document document;
    Double latitude, longitude;
    GMapV2GetRouteDirection v2GetRouteDirection;
    LatLng fromPosition;
    LatLng toPosition;
    GoogleMap mGoogleMap;
    MarkerOptions markerOptions;
    LatLng latlang;
    String Provider;
    Location location;
    TextView tv;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv_distance_time);
        mWebView = (WebView) findViewById(R.id.webview);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        loc(locationManager);
        v2GetRouteDirection = new GMapV2GetRouteDirection();
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map1);
            mGoogleMap = supportMapFragment.getMap();
            mGoogleMap.setMyLocationEnabled(true);
            UiSettings settings = mGoogleMap.getUiSettings();
            settings.setZoomControlsEnabled(true);
            Criteria criteria = new Criteria();
            Provider = locationManager.getBestProvider(criteria, true);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            toPosition = new LatLng(12.9972686, 77.66959010000005);

            if(location!=null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                fromPosition = new LatLng(latitude, longitude);
                System.out.println("lat=" + latitude + "lang=" + longitude);
                mGoogleMap.addMarker(new MarkerOptions().position(fromPosition).title("You are HERE").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(fromPosition));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                markerOptions = new MarkerOptions();

                GetRouteTask getRoute = new GetRouteTask();
                getRoute.execute();

                Location l1 = new Location("One");
                l1.setLatitude(fromPosition.latitude);
                l1.setLongitude(fromPosition.longitude);

                Location l2 = new Location("Two");
                l2.setLatitude(toPosition.latitude);
                l2.setLongitude(toPosition.longitude);
                double distanceInMeters = l1.distanceTo(l2);
                System.out.println("Location"+l1);
                double dist = distanceInMeters;

                double kilometers = 0;
                if (distanceInMeters > 1000.0f) {
                    distanceInMeters = distanceInMeters / 1000.0f;
                    dist = distanceInMeters;
                    kilometers = (float) (dist * 1.609344);
                }
                double newdist = Math.round(kilometers*100.0)/100.0;
                String s=Double.toString(newdist);
                tv.setText(s+"KM");


               /* Intent recIntent = getIntent();
                String text = recIntent.getStringExtra("key");
                TextView distance = (TextView) findViewById(R.id.text_distance); //get a reference to the textview on the game.xml file.
                tv.setText(s+"KM");*/
            }

        }
        Button b1 = (Button) findViewById(R.id.Button1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent();

                i1.setClass(getApplicationContext(), ListActivity.class);
                startActivity(i1);
                /*i1.putExtra("Dis","s");*/

            }
        });


        Button b2= (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent();
                Toast.makeText(Mapsexample.this, "Rejected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onClick(View v) {

    }

    private class GetRouteTask extends AsyncTask<String, Void, String> {

        private ProgressDialog Dialog;
        String response = "";

        @Override
        protected void onPreExecute() {
            Dialog = new ProgressDialog(Mapsexample.this);
            Dialog.setMessage("Loading route...");
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            //Get All Route values
            document = v2GetRouteDirection.getDocument(fromPosition, toPosition, GMapV2GetRouteDirection.MODE_DRIVING);
            response = "Success";
            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            mGoogleMap.clear();
            if (response.equalsIgnoreCase("Success")) {
                ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);
                PolylineOptions rectLine = new PolylineOptions().width(7).color(
                        Color.RED);

                for (int i = 0; i < directionPoint.size(); i++) {
                    rectLine.add(directionPoint.get(i));
                }

                // Adding route on the map
                mGoogleMap.addPolyline(rectLine);
                markerOptions.position(fromPosition);
                markerOptions.draggable(true);
                mGoogleMap.addMarker(markerOptions);

            }

            Dialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private class Overlay {
    }
    private void loc(final LocationManager locationManager) {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // buildAlertMessageNoGps();
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                            System.exit(0);
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }
/*=============================================================================================*/
    // when user clicks Hardware back button it ask for the permission to exit app or not
    @Override
    public void onBackPressed()
    {
        if(mWebView.canGoBack()){
            mWebView.goBack();

        }else{
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit!")
                    .setMessage("Are you sure you want to close?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
/*=============================================================================================*/
}