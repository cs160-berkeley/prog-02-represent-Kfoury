package com.cs160.joleary.catnip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;


import java.util.ArrayList;

///
import android.widget.Toast;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.EditText;
import android.text.InputType;
import android.content.DialogInterface;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.content.SharedPreferences;
import android.app.DialogFragment;
import android.widget.AdapterView;

import android.support.v4.app.TaskStackBuilder;

import android.support.v4.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;


import android.content.res.Resources;

import android.app.Dialog;
import android.widget.TimePicker;
import android.text.format.DateFormat;
import android.app.AlarmManager;
import android.content.Intent;
import android.widget.Spinner;

import android.view.View;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


//

public class SenatorsListUniqueActivity extends Activity {

    private List<repPerson> mySenators = new ArrayList<repPerson>();

    private IntentFilter mIntentFilter;
    BroadcastReceiver myReceive;

    private Map<String, ArrayList<repPerson>> dataSource;

    private String currentZip;


    private boolean shakeHappened = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        populatePersons();






        Intent intent_i = getIntent();

        currentZip = intent_i.getStringExtra("ZIP");


        setContentView(R.layout.senators_list_unique);


        mySenators = dataSource.get(currentZip);

        populateListView();


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

                        if (currentZip.equals("94704")) {
                            currentZip = "94703";
                        } else {
                            currentZip = "94704";
                        }


                        ///UPDATE THE WATCH
                        mySenators = dataSource.get(currentZip);

                        populateListView();

                        Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
                        sendIntent.putExtra("ZIP", currentZip);
                        startService(sendIntent);
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
            super(SenatorsListUniqueActivity.this, R.layout.senator_list_item, mySenators );

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

            repPerson currentPerson = mySenators.get(position);




            TextView txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtName.setText(currentPerson.getName());


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
    private void populatePersons()

    {
        dataSource = new HashMap<String, ArrayList<repPerson>>() ;



        String[] comm = {"Hello", "World"};
        String[] bills = {"Hello", "World"};
        ArrayList<repPerson> mySenators_zip1 = new ArrayList<repPerson>();


        mySenators_zip1.add(new repPerson(comm,"Fadi","fadi_zip1@fadi.space","http://fadi.space","Independent", "YES WE CAN #FREEDOM", R.drawable.fred_160,bills));
        mySenators_zip1.add(new repPerson(comm,"Pascal","fadi@fadi.space","http://fadi1.space","Independent", "TODAY IS OUR DAY ", R.drawable.fred_160,bills));
        mySenators_zip1.add(new repPerson(comm,"Scala","fadi@fadi.space","http://fadi2.space","Independent", "MAKE AMERICA GREAT AGAIN", R.drawable.fred_160,bills));


        dataSource.put("94704",mySenators_zip1);

        ArrayList<repPerson> mySenators_zip2 = new ArrayList<repPerson>();

        mySenators_zip2.add(new repPerson(comm,"Antoine","fadi_zip1@fadi.space","http://fadi.space","Independent", "YES WE CAN #FREEDOM", R.drawable.fred_160,bills));
        mySenators_zip2.add(new repPerson(comm,"Rick","fadi@fadi.space","http://fadi.space","Independent", "WE ARE GETTING RID OF THE TYRANY OF OIL ONCE AND FOR ALL", R.drawable.fred_160,bills));

        dataSource.put("94703", mySenators_zip2);


    }


    ///SPINNER ADAPTER
}
