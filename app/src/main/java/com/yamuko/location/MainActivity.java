package com.yamuko.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv_gps, tv_location;
    Boolean b;
    Button btn_permission;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_gps = (TextView) findViewById(R.id.tv_gps);
        tv_location = (TextView) findViewById(R.id.tv_location);

        btn_permission = (Button) findViewById(R.id.btn_permission);

        getCurrentLocation();

        btn_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*b = checkAndRequestPermissions();
                tv_permision.setText(" Permision : " + String.valueOf(b));*/

                getCurrentLocation();
            }
        });

    }

    private void getCurrentLocation() {
//        Log.e("getLocation","Called");

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double mlat = location.getLatitude();
                double mlon = location.getLongitude();
                String mLogn = Double.toString(mlon);
                String mLatd = Double.toString(mlat);
//                Log.e("mLogn",""+mLogn);
//                Log.e("mLatd",""+mLatd);

                tv_location.setText("Log " + mLogn + " Lat " + mLatd);
                mLogn=mLogn.trim();
                mLatd=mLatd.trim();
                if (mLatd==null || mLogn==null) {
                    if (mLatd.isEmpty()) {
                        mLatd = "No data found";
                    }
                    if (mLogn.isEmpty()) {
                        mLogn = "No data found";
                    }
                }

                // Log.e("Location",""+mlon+" "+mlat);
//                Toast.makeText(CampaignTrain.this, "your location is " + mLogn + " " + mLatd, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        String locationProvide = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},COARSE_LOCATION_REQUEST_CODE);
                return;
            }


        }
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_REQUEST_CODE);
                return;
            }

        }
        locationManager.requestLocationUpdates(locationProvide, 0, 0, locationListener);
//        Location lastLocation=locationManager.getLastKnownLocation(locationProvide);



    }
    int FINE_LOCATION_REQUEST_CODE=101;
    int COARSE_LOCATION_REQUEST_CODE=102;

}
