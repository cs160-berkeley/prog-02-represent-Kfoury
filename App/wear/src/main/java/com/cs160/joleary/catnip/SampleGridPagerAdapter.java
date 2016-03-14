package com.cs160.joleary.catnip;
import android.content.Intent;
/**
 * Created by fadikfoury on 3/1/16.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.content.res.Resources;

import android.content.res.Resources;


import android.view.Gravity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.wearable.view.GridViewPager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

    private  Context mContext;

    public void setDataSource(JSONArray dataSource) {
        this.dataSource = dataSource;
    }

    private List mRows;

    public void setVotesData(JSONObject votesData) {
        this.votesData = votesData;
    }

    private String currentZip;

    private JSONArray dataSource  ;
    private JSONObject votesData  ;



    public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;



    }

    static final int[] BG_IMAGES = new int[] {

};

// A simple container for static data in each page
private static class Page {
    // static resources
    int titleRes;
    int textRes;
    int iconRes;

}


    public void setCurrentZip(String currentZip) {
        this.currentZip = currentZip;
    }

    // Create a static set of pages in a 2D array
private final Page[][] PAGES = {  };

        // Override methods in FragmentGridPagerAdapter

    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        // Page page = PAGES[row][col];


        if (row == 0) {
            senator_fragment fragment = new senator_fragment();


            JSONObject chosen_senator = null;
            try {
                chosen_senator = (JSONObject) dataSource.get(col);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                fragment.initializeWithSenatorName(chosen_senator.getString("name") + " - "+chosen_senator.getString("party"), R.drawable.senator_test, col, mContext);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Advanced settings (card gravity, card expansion/scrolling)









            return fragment;
        } else

        {
            //Load the election view

            election_fragment fragment = new election_fragment();


            try {
                fragment.initializeWithElectionProperties("Obama", "Romney", currentZip, (int) votesData.getDouble("obama"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Advanced settings (card gravity, card expansion/scrolling)



            return fragment;


        }

    }

    // Obtain the background image for the row


    // Obtain the number of pages (vertical)
    @Override
    public int getRowCount() {
        return 2;

    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {

        if (rowNum == 0)
        {


                return dataSource.length();



        }
        else
        {
            return 1;

        }


    }


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
//        mySenators_zip1.add(new repPerson(comm,"Fadi","fadi_zip1@fadi.space","http://fadi.space","Independent", "YES WE CAN #FREEDOM", R.drawable.food_bg_160,bills));
//        mySenators_zip1.add(new repPerson(comm,"Pascal","fadi@fadi.space","http://fadi1.space","Independent", "TODAY IS OUR DAY ", R.drawable.food_bg_160,bills));
//        mySenators_zip1.add(new repPerson(comm,"Scala","fadi@fadi.space","http://fadi2.space","Independent", "MAKE AMERICA GREAT AGAIN", R.drawable.food_bg_160,bills));
//
//
//        dataSource.put("94704",mySenators_zip1);
//
//        ArrayList<repPerson> mySenators_zip2 = new ArrayList<repPerson>();
//
//        mySenators_zip2.add(new repPerson(comm,"Antoine","fadi_zip1@fadi.space","http://fadi.space","Independent", "YES WE CAN #FREEDOM", R.drawable.food_bg_160,bills));
//        mySenators_zip2.add(new repPerson(comm, "Rick", "fadi@fadi.space", "http://fadi.space", "Independent", "WE ARE GETTING RID OF THE TYRANY OF OIL ONCE AND FOR ALL", R.drawable.food_bg_160, bills));
//
//        dataSource.put("94703", mySenators_zip2);
//
//
//    }




        }
