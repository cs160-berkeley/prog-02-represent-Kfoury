package com.cs160.joleary.catnip;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

///


import android.content.Intent;


import android.content.IntentFilter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


//

public class SenatorsListUniqueActivity extends Activity {

    private List<repPerson> mySenators = new ArrayList<repPerson>();

    private IntentFilter mIntentFilter;
    BroadcastReceiver myReceive;

    private JSONArray dataSource;
    private JSONObject dataSource_;
    private String currentZip;


    private boolean shakeHappened = false;


    private ProgressDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dialog  = new ProgressDialog(SenatorsListUniqueActivity.this);

        Intent intent_i = getIntent();

        setContentView(R.layout.senators_list_unique);


        currentZip = intent_i.getStringExtra("ZIP");


        //here we need to call  initRequest Based on the zip

        initRequest(false,0.0,0.0,currentZip);




        myReceive = new BroadcastReceiver(){



            @Override
            public void onReceive(Context context, Intent intent) {


                Toast.makeText(context, "Intent Detected from activity.", Toast.LENGTH_LONG).show();


                int watchPosition = intent.getIntExtra("POS", 0);



                if (watchPosition != -1) {
                    Intent intent_ = new Intent(getApplicationContext(), detailViewUnique.class);


                    finishActivity(2);
                    int sendMessage = 1;
                    //put the text inside the intent and send it to another Activity
                    intent_.putExtra("POS", watchPosition);
                    intent_.putExtra("ZIP", currentZip);
                    //start the activity
                    //startActivity(intent);

                    startActivityForResult(intent_, 2);
                }
                else
                {
                    //chnage the zip

                    if (shakeHappened == false) {
                        shakeHappened = true;



                        initRequest(false,0.0,0.0,"99999");



                        ///UPDATE THE WATCH
                      //  mySenators = dataSource.get(currentZip);

//                        populateListView();
//
//                        Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
//                        sendIntent.putExtra("ZIP", currentZip);
//                        startService(sendIntent);
                    }

                }


            }
        };

        IntentFilter mIntentFilter=new IntentFilter("com.example.Broadcast");


        registerReceiver(myReceive, mIntentFilter);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(myReceive);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_senators_list_unique, menu);
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


    //Links the data source with the view
    private void populateListView() {
        ArrayAdapter<repPerson> adapter= new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.lblSenators);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Intent intent = new Intent(getApplicationContext(), detailViewUnique.class);

                int sendMessage = position;
                //put the text inside the intent and send it to another Activity
                intent.putExtra("POS", position);

                intent.putExtra("ZIP", currentZip);


                //start the activity
                //startActivity(intent);

                startActivityForResult(intent, 2);







                Toast.makeText(getBaseContext(), String.format("pos %d",position), Toast.LENGTH_SHORT).show();
            }
        });


    }









    private class MyListAdapter extends ArrayAdapter<repPerson>{
        public MyListAdapter()
        {
            super(SenatorsListUniqueActivity.this, R.layout.senator_list_item );

        }

        @Override
        public int getCount() {
            return dataSource_.length();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            //basically de-ques item for identifier
            //it does the heavy weight lifting for us
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.senator_list_item,parent,false);
            }

            //Now we have the view ready to be consumed, let's populate the view

            //we need to find the exercise to work with then fill the view

            JSONObject currentPerson = null ;
            try {
                currentPerson = (JSONObject)dataSource.get(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            TextView txtName = (TextView) itemView.findViewById(R.id.txtName);
            try {
                txtName.setText(currentPerson.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            ImageView imgView = (ImageView) itemView.findViewById(R.id.imgSenator_);
            try {
                Picasso.with(getContext()).load(currentPerson.getString("picture")).fit().centerCrop().into(imgView);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            TextView txtTwitterName = (TextView) itemView.findViewById(R.id.txttwittername);
            try {
                txtTwitterName.setText(currentPerson.getString("twitter"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextView txtTweet = (TextView) itemView.findViewById(R.id.txttweet);
            try {
                txtTweet.setText(currentPerson.getString("last_tweet"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            TextView txtemail = (TextView) itemView.findViewById(R.id.txtEmail);
            try {
                txtemail.setText(currentPerson.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            TextView txtwebsite = (TextView) itemView.findViewById(R.id.txtWeb);
            try {
                txtwebsite.setText(currentPerson.getString("website"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            TextView txtParty = (TextView) itemView.findViewById(R.id.txtParty);
            try {

                if (currentPerson.getString("type").equals("house"))
                {
                    txtParty.setText(currentPerson.getString("party") + " - House");
                }else
                {
                    txtParty.setText(currentPerson.getString("party") + " - Senate");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }





            //Fill in the view


/*

ImageView imgView = (ImageView) itemView.findViewById(R.id.imgSenator);
            imgView.setImageDrawable(getResources().getDrawable(currentPerson.getIconID()));



            //Fill in the txts

            // figure out the unit

            TextView txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtName.setText(currentPerson.getName());

            TextView txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtName.setText(currentPerson.getEmail());

            TextView txtTweet = (TextView) itemView.findViewById(R.id.txtTweet);
            txtName.setText(currentPerson.getTweet());


            TextView txtParty = (TextView) itemView.findViewById(R.id.txtParty);
            txtName.setText(currentPerson.getParty());


            TextView txtWeb = (TextView) itemView.findViewById(R.id.txtWeb);
            txtName.setText(currentPerson.getWebsite());

*/
            return itemView;



            //  return super.getView(position, convertView, parent);


        }






    }



    //A function that populates the list view data source
//    private void populatePersons()
//
//    {
//        dataSource = new HashMap<String, ArrayList<repPerson>>() ;
//
//
//
//        String[] comm = {"Hello", "World"};
//        String[] bills = {"Hello", "World"};
//        ArrayList<repPerson> mySenators_zip1 = new ArrayList<repPerson>();
//
//
//        mySenators_zip1.add(new repPerson(comm,"Fadi","fadi_zip1@fadi.space","http://fadi.space","Independent", "YES WE CAN #FREEDOM", R.drawable.fred_160,bills));
//        mySenators_zip1.add(new repPerson(comm,"Pascal","fadi@fadi.space","http://fadi1.space","Independent", "TODAY IS OUR DAY ", R.drawable.fred_160,bills));
//        mySenators_zip1.add(new repPerson(comm,"Scala","fadi@fadi.space","http://fadi2.space","Independent", "MAKE AMERICA GREAT AGAIN", R.drawable.fred_160,bills));
//
//
//        dataSource.put("94704",mySenators_zip1);
//
//        ArrayList<repPerson> mySenators_zip2 = new ArrayList<repPerson>();
//
//        mySenators_zip2.add(new repPerson(comm,"Antoine","fadi_zip1@fadi.space","http://fadi.space","Independent", "YES WE CAN #FREEDOM", R.drawable.fred_160,bills));
//        mySenators_zip2.add(new repPerson(comm,"Rick","fadi@fadi.space","http://fadi.space","Independent", "WE ARE GETTING RID OF THE TYRANY OF OIL ONCE AND FOR ALL", R.drawable.fred_160,bills));
//
//        dataSource.put("94703", mySenators_zip2);
//
//
//    }


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

        dialog.setMessage("Please wait");
        dialog.show();




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

        currentZip = new Integer(all_data.getInt("zip")).toString();






        dataSource = (JSONArray)all_data.get("people");



        populateListView();


        Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
        sendIntent.putExtra("ZIP", all_data.toString());
        startService(sendIntent);






    }






}
