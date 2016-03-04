
/**
 * Created by fadikfoury on 3/2/16.
 */


package com.cs160.joleary.catnip;

        import android.app.Fragment;

        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import android.view.LayoutInflater;
        import android.widget.ProgressBar;
        import android.widget.TextView;

/**
 * Created by fadikfoury on 3/1/16.
 */
public class election_fragment extends Fragment {


    private View my_view;
    private String senator_name_1;
    private String senator_name_2;
    private String county_name;
    private int democrates_percentage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        my_view = inflater.inflate(R.layout.election_view, container, false);
        // Setup handles to view objects here
        // etFoo = (EditText) view.findViewById(R.id.etFoo);




        TextView txtSenator_1 = (TextView) my_view.findViewById(R.id.sentor_1);
        txtSenator_1.setText(senator_name_1);

        TextView txtSenator_2 = (TextView) my_view.findViewById(R.id.senator_2);
        txtSenator_2.setText(senator_name_2);

        TextView txtCounty = (TextView) my_view.findViewById(R.id.county);
        txtCounty.setText(county_name);

        ProgressBar myProg = (ProgressBar) my_view.findViewById(R.id.progressBar);
        myProg.setProgress(democrates_percentage);




        return my_view;

    }

    public void initializeWithElectionProperties(String senator_1, String senator_2, String countyName, int d_percentage) {


        senator_name_1 = senator_1;

        senator_name_2 = senator_2;


        county_name = countyName;

        democrates_percentage = d_percentage;


    }



}
