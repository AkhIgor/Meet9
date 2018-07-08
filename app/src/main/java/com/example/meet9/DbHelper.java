package com.example.meet9;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Игорь on 05.07.2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "my_tag";
    public static final String TABLE_NAME = "Notes";

    public static final String _ID = "_ID";
    public static final String NAME = "Name";
    public static final String DATE = "Date";
    public static final String INFO = "Info";

    private static final String DATABASE_NAME = "MyNotes";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT," +
                DATE + " TEXT," +
                INFO + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
