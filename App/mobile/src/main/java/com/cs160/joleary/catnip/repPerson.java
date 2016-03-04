package com.cs160.joleary.catnip;

/**
 * Created by fadikfoury on 3/3/16.
 */


public class repPerson {
    private String name;
    private String email;
    private String website;
    private String party;
    private String tweet;
    private int iconID;
    private String[] bills;
    private String[] committees;


    public repPerson(String[] committees, String name, String email, String website, String party, String tweet, int iconID, String[] bills)

    {
        this.committees = committees;
        this.name = name;
        this.email = email;
        this.website = website;
        this.party = party;
        this.tweet = tweet;
        this.iconID = iconID;
        this.bills = bills;
    }




    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getParty() {
        return party;
    }

    public String getTweet() {
        return tweet;
    }

    public int getIconID() {
        return iconID;
    }

    public String[] getBills() {
        return bills;
    }

    public String[] getCommittees() {
        return committees;
    }






}

