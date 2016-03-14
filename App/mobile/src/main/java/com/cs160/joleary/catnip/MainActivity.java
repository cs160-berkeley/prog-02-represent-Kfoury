package com.cs160.joleary.catnip;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.location.LocationManager;
import android.content.Context;
import android.location.LocationListener;

import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//




import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HTTP;


public class MainActivity extends Activity {
    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

    private Button btnUseCurrentLocation;
    private Button btnNext;
    private TextView txtZipCode;


    LocationManager locationManager;

    LocationListener locationListener;

    JSONObject dataSource;


    private ProgressDialog dialog;



    double current_latitude = 0.0;
    double current_longitude = 0.0;

    double d;

    private void configureBtn() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        dialog = new ProgressDialog(MainActivity.this);
        ///



        ///


        setContentView(R.layout.activity_main);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                current_latitude = location.getLatitude();
                current_longitude = location.getLongitude();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.

                requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
                return;
            }


        } else

        {


            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }


        //Current Location
        btnUseCurrentLocation = (Button) findViewById(R.id.cmdCurrentLocation);

        //Next
        btnNext = (Button) findViewById(R.id.cmdNext);

        //txtZipCode
        txtZipCode = (TextView) findViewById(R.id.txtZip);


        //Add listener to the current location

        btnUseCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (current_latitude != 0) {


                    initRequest(true, current_latitude, current_longitude,"");







//                    //go the next level
//                    // the name of the receiving activity is declared in the Intent Constructor
//                    Intent intent = new Intent(getApplicationContext(), SenatorsListUniqueActivity.class);
//
//                    String sendMessage = "94704";
//                    //put the text inside the intent and send it to another Activity
//                    intent.putExtra("ZIP", sendMessage);
//                    //start the activity
//                    startActivity(intent);
//
//
//                    Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
//                    sendIntent.putExtra("ZIP", "94704");
//                    startService(sendIntent);
                }


            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                initRequest(false,0,0,txtZipCode.getText().toString());




            }
        });


        //   mFredButton = (Button) findViewById(R.id.fred_btn);
        //   mLexyButton = (Button) findViewById(R.id.lexy_btn);

        //   mFredButton.setOnClickListener(new View.OnClickListener() {
     /*       @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Fred");
                startService(sendIntent);
            }
        });

        mLexyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Lexy");
                startService(sendIntent);
            }
        });
        */

    }



    void gotReponseFromServer() throws JSONException {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }


        JSONObject all_data = (JSONObject) dataSource.get("all_data") ;

        int zip = all_data.getInt("zip");





        if (zip > 4) {

            //go the next level

            Intent intent = new Intent(getApplicationContext(), SenatorsListUniqueActivity.class);

            String sendMessage = new Integer(zip).toString();

            //put the text inside the intent and send it to another Activity
            intent.putExtra("ZIP", sendMessage);
            //start the activity
            startActivity(intent);


//            Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
//            sendIntent.putExtra("ZIP", all_data.toString());
//            startService(sendIntent);


        } else {

            //Toast unable to proceed, location is unknown
        }








    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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









    public void initRequest(Boolean isLocation, double lat, double longitude, String zip_ )
    {
        HttpResponse g;





        AsyncHttpClient client = new AsyncHttpClient();
        String json_;


        if (isLocation) {

             json_ = " {\n" +
                    " \t\"type\": \"geo\",\n" +
                    " \t\"data\": [" + new Double(lat).toString() +","+ new Double(longitude).toString() + "]\n" +
                    " }";
        }else

        {

            json_ = " {\n" +
                    " \t\"type\": \"zip\",\n" +
                    " \t\"data\": " +zip_.toString()+"\n" +
                    " }";


        }






        //ByteArrayEntity entity = new ByteArrayEntity(json_.getBytes("UTF-8"));

        StringEntity entity = null;
        try {
            entity = new StringEntity(json_);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ByteArrayEntity entity_ = null;
        try {
            entity_ = new ByteArrayEntity(json_.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




        entity_.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));



        //Here we need to enable the spinner

        dialog.setMessage("Please wait");
        dialog.show();




        ///
        client.post(getApplicationContext(), "http://tagjr.co/represent", entity_, "application/json", new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("tag", "Login success");
                //here is the SUCCESS

                dataSource = response;


                //here we should call the


                try {
                    gotReponseFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }






            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("tag", "Login success");
                //HERE IS THE FAIL

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                Toast.makeText(getApplicationContext(), "Unknown Location", Toast.LENGTH_SHORT).show();



            }


        });


    }


}







