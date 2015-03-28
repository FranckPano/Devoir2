package com.franckpano.devoir2;

import java.io.Serializable;

/**
 * Created by Franck on 26/03/2015.
 */
public class Data implements Serializable{
    private long id;
    private String name;

    public long getId() {
            return id;
        }

    public void setId(long id) {
            this.id = id;
        }
    public String getName() {
        return name;
    }

    public void setName(String content) {
        this.name= content;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
            return name;
        }
}