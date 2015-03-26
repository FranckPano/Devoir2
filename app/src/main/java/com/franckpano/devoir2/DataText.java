package com.franckpano.devoir2;

/**
 * Created by Franck on 26/03/2015.
 */
public class DataText {
    private long id;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getData() {
        return content;
    }

    public void setData(String comment) {
        this.content = comment;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return content;
    }
}