package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

    private Button btnUseCurrentLocation;
    private Button btnNext;
    private TextView txtZipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


                    //HARDCODED ZIP 94704

                    //go the next level
                // the name of the receiving activity is declared in the Intent Constructor
                Intent intent = new Intent(getApplicationContext(), SenatorsListUniqueActivity.class);

                String sendMessage = "94704";
                //put the text inside the intent and send it to another Activity
                intent.putExtra("ZIP", sendMessage);
                //start the activity
                startActivity(intent);


                Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
                sendIntent.putExtra("ZIP", "94704");
                startService(sendIntent);





            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtZipCode.getText().toString().length() > 4)
                {
                    //go the next level

                    Intent intent = new Intent(getApplicationContext(), SenatorsListUniqueActivity.class);

                    String sendMessage = txtZipCode.getText().toString();
                    //put the text inside the intent and send it to another Activity
                    intent.putExtra("ZIP", sendMessage);
                    //start the activity
                    startActivity(intent);


                    Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
                    sendIntent.putExtra("ZIP", sendMessage);
                    startService(sendIntent);



                }else
                {
                    //Halt
                }


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
}
