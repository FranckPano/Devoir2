package com.franckpano.devoir2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    //public static final String[] TABLE_NAME = {"texts","croquis","voix","photos","videos"};

    public static final String TABLE_TEXTS = "texts";
    public static final String TABLE_CROQUIS = "croquis";
    public static final String TABLE_VOIX = "voix";
    public static final String TABLE_PHOTOS = "photos";
    public static final String TABLE_VIDEOS = "videos";


    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "comment";

    public static final String COLUMN_CONTENT = "content";

//ToKill
    /*public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_CROQUIS = "croquis";
    public static final String COLUMN_VOIX = "voix";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_VIDEO = "video";*/

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

    //Request parts
    private static final String REQUEST_PART1 = "create table ";
    private static final String REQUEST_PART2 = "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_CONTENT
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(REQUEST_PART1 + TABLE_TEXTS + REQUEST_PART2);
        database.execSQL(REQUEST_PART1 + TABLE_CROQUIS + REQUEST_PART2);
        database.execSQL(REQUEST_PART1 + TABLE_VOIX + REQUEST_PART2);
        database.execSQL(REQUEST_PART1 + TABLE_PHOTOS + REQUEST_PART2);
        database.execSQL(REQUEST_PART1 + TABLE_VIDEOS + REQUEST_PART2);
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