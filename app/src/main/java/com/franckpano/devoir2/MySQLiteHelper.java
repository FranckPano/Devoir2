package com.franckpano.devoir2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TEXTS = "texts";
    public static final String TABLE_CROQUIS = "croquis";
    public static final String TABLE_VOIX = "voix";
    public static final String TABLE_PHOTOS = "photos";
    public static final String TABLE_VIDEOS = "videos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CONTENT = "content";

    public static final String DATA_TYPE_TEXT = "text not null";
    public static final String DATA_TYPE_CROQUIS = "blob";
    public static final String DATA_TYPE_VOIX = "voix";
    public static final String DATA_TYPE_PHOTO = "blob";
    public static final String DATA_TYPE_VIDEO = "video";

    private static final String DATABASE_NAME = "notesdb.db";
    private static final int DATABASE_VERSION = 1;

    //Request parts
    private static final String REQUEST_PART1 = "create table ";
    private static final String REQUEST_PART2 = "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME + " text not null, " + COLUMN_CONTENT + " ";
    private static final String REQUEST_PART3 = ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(REQUEST_PART1 + TABLE_TEXTS + REQUEST_PART2 + DATA_TYPE_TEXT + REQUEST_PART3);
        database.execSQL(REQUEST_PART1 + TABLE_CROQUIS + REQUEST_PART2 + DATA_TYPE_CROQUIS + REQUEST_PART3);
        database.execSQL(REQUEST_PART1 + TABLE_VOIX + REQUEST_PART2 + DATA_TYPE_VOIX + REQUEST_PART3);
        database.execSQL(REQUEST_PART1 + TABLE_PHOTOS + REQUEST_PART2 + DATA_TYPE_PHOTO + REQUEST_PART3);
        database.execSQL(REQUEST_PART1 + TABLE_VIDEOS + REQUEST_PART2 + DATA_TYPE_VIDEO + REQUEST_PART3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEXTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CROQUIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOIX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS);

        onCreate(db);
    }

} 