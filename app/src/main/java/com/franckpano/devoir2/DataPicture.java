package com.franckpano.devoir2;

/**
 * Created by Franck on 26/03/2015.
 */
public class DataPicture extends Data{
    private long id;
    private byte[] croquis;


    public byte[] getData() {
        return croquis;
    }

    public void setData(byte[] data) {
        this.croquis = data;
    }

}
