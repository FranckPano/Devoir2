package com.franckpano.devoir2;

/**
 * Created by Franck on 26/03/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataDAO {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_CONTENT, MySQLiteHelper.COLUMN_NAME };
/*
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TEXT };
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_CROQUIS };
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_VOIX };
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PHOTO };
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_VIDEO};*/



    public DataDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Data createData(String data, String name , String nameTable) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_CONTENT, data);
        long insertId = database.insert(nameTable, null,
                values);
        Cursor cursor = database.query(nameTable,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Data newComment = cursorToDataText(cursor);
        cursor.close();
        return newComment;
    }

    public Data createCroquisData(byte[] data, String name, String nameTable) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_CONTENT, data);
        long insertId = database.insert(nameTable, null,
                values);
        Cursor cursor = database.query(nameTable,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Data newComment = cursorToDataPicture(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Data comment, String nameTable) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(nameTable, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Data> getAllContents(String nameTable) {
        List<Data> contents = new ArrayList<Data>();

        Cursor cursor = database.query(nameTable,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Data data = null;
            switch(nameTable){
                case MySQLiteHelper.TABLE_TEXTS:
                    data = cursorToDataText(cursor);
                    break;
                case MySQLiteHelper.TABLE_CROQUIS:
                    data = cursorToDataPicture(cursor);
                    break;
                case MySQLiteHelper.TABLE_VOIX:
                    data = cursorToDataText(cursor);
                    break;
                case MySQLiteHelper.TABLE_PHOTOS:
                    data = cursorToDataPicture(cursor);
                    break;
                case MySQLiteHelper.TABLE_VIDEOS:
                    data = cursorToDataText(cursor);
                    break;
            }
            contents.add(data);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return contents;
    }

    private DataText cursorToDataText(Cursor cursor) {
        DataText data = new DataText();
        data.setId(cursor.getLong(0));
        data.setData(cursor.getString(1));
        data.setName(cursor.getString(2));
        return data;
    }

    private DataPicture cursorToDataPicture(Cursor cursor) {
        DataPicture data = new DataPicture();
        data.setId(cursor.getLong(0));
        data.setData(cursor.getBlob(1));
        data.setName(cursor.getString(2));
        return data;
    }
}

