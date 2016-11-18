package com.kekaton.hackathon.Model;

/**
 * Created by User on 17.11.2016.
 */

public class Photo {

    private int id;
    private String url;

    public Photo(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

}
