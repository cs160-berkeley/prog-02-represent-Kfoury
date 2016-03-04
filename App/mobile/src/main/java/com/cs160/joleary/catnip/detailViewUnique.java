package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class detailViewUnique extends Activity {

    private HashMap<String, ArrayList<repPerson>> dataSource;

    private String chosenZip;
    private int chosenPosition;
    private repPerson chosenSenate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view_unique);


        Intent myIntent = getIntent();

        chosenZip = myIntent.getStringExtra("ZIP");

        chosenPosition = myIntent.getIntExtra("POS", 0);




        populatePersons();

        chosenSenate = dataSource.get(chosenZip).get(chosenPosition);


        TextView txtName = (TextView) this.findViewById(R.id.txtSenatorName);
        txtName.setText(chosenSenate.getName());





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

}





