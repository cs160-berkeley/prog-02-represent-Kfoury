package com.cs160.joleary.catnip;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.wearable.view.GridViewPager;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class MainActivity extends Activity {

    private TextView mTextView;
    private Button mFeedBtn;




    private String currentZip;

    private ProgressDialog dialog;

    private JSONArray dataSource;
    private JSONObject dataSource_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // dialog = new ProgressDialog(this);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            currentZip = extras.getString("ZIP");

            JSONObject all_data = null;
            try {
                all_data = new JSONObject(currentZip );
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            try {
//                int zip = all_data.getInt("zip");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


            try {
                dataSource = (JSONArray)all_data.get("people");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

            SampleGridPagerAdapter adapter_ = new SampleGridPagerAdapter(this, getFragmentManager());

            adapter_.setDataSource(dataSource);

            try {
                adapter_.setCurrentZip(all_data.getString("zip"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                adapter_.setVotesData(all_data.getJSONObject("votes"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            pager.setAdapter(adapter_);



        }else

        {
            currentZip = "94704";
        }


       // initRequest(false,0,0,currentZip);





        /*
        mFeedBtn = (Button) findViewById(R.id.feed_btn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String catName = extras.getString("CAT_NAME");
            mFeedBtn.setText("Feed " + catName);
        }*/





/*
        mFeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                startService(sendIntent);
            }
        });
*/


           /* do this in onCreate */
        mSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;



    }

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity




    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter




            if (mAccel > 12 ) {



                Log.d("CREATION","acceleration alert");

                Intent sendIntent = new Intent(getApplicationContext(), WatchToPhoneService.class);
                sendIntent.putExtra("POS", -1);

                getApplicationContext().startService(sendIntent);



            }


        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }




    ///SPINNER ADAPTER


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

      //  dialog.setMessage("Please wait");
       // dialog.show();




        ///
        client.post(getApplicationContext(), "http://tagjr.co/represent", entity_, "application/json", new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("tag", "Login success");
                //here is the SUCCESS

                dataSource_ = response;


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


    void gotReponseFromServer() throws JSONException {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }


        JSONObject all_data = (JSONObject) dataSource_.get("all_data") ;

        int zip = all_data.getInt("zip");





        dataSource = (JSONArray)all_data.get("people");



        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

        SampleGridPagerAdapter adapter_ = new SampleGridPagerAdapter(this, getFragmentManager());

        adapter_.setDataSource(dataSource);

        pager.setAdapter(adapter_);




    }




}
