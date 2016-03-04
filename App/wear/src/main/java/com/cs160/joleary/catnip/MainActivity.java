package com.cs160.joleary.catnip;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    private TextView mTextView;
    private Button mFeedBtn;

    private HashMap<String, ArrayList<repPerson>> dataSource  ;


    private String currentZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            currentZip = extras.getString("ZIP");
        }else

        {
            currentZip = "94704";
        }

        /*
        mFeedBtn = (Button) findViewById(R.id.feed_btn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String catName = extras.getString("CAT_NAME");
            mFeedBtn.setText("Feed " + catName);
        }*/


        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

        SampleGridPagerAdapter adapter_ = new SampleGridPagerAdapter(this, getFragmentManager());

        adapter_.setCurrentZip(currentZip);

        pager.setAdapter(adapter_);


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




    private void populatePersons()

    {
        dataSource = new HashMap<String, ArrayList<repPerson>>() ;



        String[] comm = {"Hello", "World"};
        String[] bills = {"Hello", "World"};
        ArrayList<repPerson> mySenators_zip1 = new ArrayList<repPerson>();


        mySenators_zip1.add(new repPerson(comm,"Fadi","fadi_zip1@fadi.space","http://fadi.space","Independent", "YES WE CAN #FREEDOM", R.drawable.food_bg_160,bills));
        mySenators_zip1.add(new repPerson(comm,"Pascal","fadi@fadi.space","http://fadi1.space","Independent", "TODAY IS OUR DAY ", R.drawable.food_bg_160,bills));
        mySenators_zip1.add(new repPerson(comm,"Scala","fadi@fadi.space","http://fadi2.space","Independent", "MAKE AMERICA GREAT AGAIN", R.drawable.food_bg_160,bills));


        dataSource.put("94704",mySenators_zip1);

        ArrayList<repPerson> mySenators_zip2 = new ArrayList<repPerson>();

        mySenators_zip2.add(new repPerson(comm,"Antoine","fadi_zip1@fadi.space","http://fadi.space","Independent", "YES WE CAN #FREEDOM", R.drawable.food_bg_160,bills));
        mySenators_zip2.add(new repPerson(comm, "Rick", "fadi@fadi.space", "http://fadi.space", "Independent", "WE ARE GETTING RID OF THE TYRANY OF OIL ONCE AND FOR ALL", R.drawable.food_bg_160, bills));

        dataSource.put("94703", mySenators_zip2);


    }


}
