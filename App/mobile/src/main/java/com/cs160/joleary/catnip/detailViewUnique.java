package com.cs160.joleary.catnip;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

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

public class detailViewUnique extends Activity {


    private String chosenZip;
    private int chosenPosition;
    private JSONObject chosenSenate;

    private JSONArray dataSource;
    private JSONObject dataSource_;



    private ProgressDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog  = new ProgressDialog(detailViewUnique.this);

        setContentView(R.layout.detail_view_unique);


        Intent myIntent = getIntent();

        chosenZip = myIntent.getStringExtra("ZIP");

        chosenPosition = myIntent.getIntExtra("POS", 0);




       // populatePersons();

        initRequest(false, 0, 0, chosenZip);







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_view_unique, menu);
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

    //A function that populates the list view data source
//    private void populatePersons()
//
//    {
//        //dataSource = new HashMap<String, ArrayList<repPerson>>() ;
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

        int zip = all_data.getInt("zip");





        dataSource = (JSONArray)all_data.get("people");


//refresh view
        //populateListView();

        chosenSenate = (JSONObject) dataSource.get(chosenPosition);




        TextView txtName = (TextView) this.findViewById(R.id.txtSenatorName);
        txtName.setText(chosenSenate.getString("name"));



        TextView txtParty = (TextView) this.findViewById(R.id.txtSenatorTitle);
        try {

            if (chosenSenate.getString("type").equals("house"))
            {
                txtParty.setText(chosenSenate.getString("party") + " - House");
            }else
            {
                txtParty.setText(chosenSenate.getString("party") + " - Senate");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }




        TextView txtterm = (TextView) this.findViewById(R.id.txtSenatorTerm);
        txtterm.setText("Term ends: " +chosenSenate.getString("term_end"));


        JSONArray committees =  chosenSenate.getJSONArray("commities");

        String committees_str = "";

        for (int i = 0; i < committees.length();i++)


        {
            committees_str = committees_str + "\n \n - " + committees.getString(i) ;
        }

        committees_str = committees_str + "\n \n";

        JSONArray bills = chosenSenate.getJSONArray("bills");

        String bills_str = "";

        for (int i = 0; i < bills.length();i++)


        {
            bills_str = bills_str + "\n \n - " +bills.getString(i) ;
        }

        bills_str = bills_str + "\n \n";

        TextView txtBill = (TextView) this.findViewById(R.id.txtBills);
        txtBill.setText(bills_str);

        TextView txtCommitte = (TextView) this.findViewById(R.id.txtCommit);
        txtCommitte.setText(committees_str);


        ImageView imgView = (ImageView) this.findViewById(R.id.imgSenator_);
        Picasso.with(this).load(chosenSenate.getString("picture")).fit().centerCrop().into(imgView);













    }


}





