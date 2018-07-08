package com.example.meet9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Игорь on 05.07.2018.
 */

public class DataBase {

    private static final String LOG_TAG = "my_tag";

    DbHelper dbHelper;
    Context context;
    Cursor cursor;
    SQLiteDatabase db;
    List<Note> NoteList;

    public DataBase(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public int getItemCount() {
        db = dbHelper.getReadableDatabase();
        cursor = db.query(DbHelper.TABLE_NAME,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void deleteItem(long id) {
        try {
            db = dbHelper.getWritableDatabase();

            db.beginTransaction();
            db.delete(DbHelper.TABLE_NAME, DbHelper._ID + "=" + id,
                    null);

            db.setTransactionSuccessful();
        } catch (Exception igored) {}
        finally {
            if (db != null) {
                if (db.inTransaction()) {
                    db.endTransaction();
                }
                db.close();
            }
        }
    }

    public void addItem(String name, String date, String info) {

        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.NAME, name);
            contentValues.put(DbHelper.DATE, date);
            contentValues.put(DbHelper.INFO, info);
            db.insert(DbHelper.TABLE_NAME, null, contentValues);

            db.setTransactionSuccessful();
        } catch (Exception ignored) {}
        finally {
            if (db != null) {
                if (db.inTransaction()) {
                    db.endTransaction();
                }
                db.close();
            }
        }
    }

    public Note getItem(long id) {

        Note note = new Note();

        db = dbHelper.getReadableDatabase();
        cursor = db.query(DbHelper.TABLE_NAME,
                new String[]{DbHelper.NAME, DbHelper.DATE, DbHelper.INFO},
                DbHelper._ID + "=" + id,
                null,
                null,
                null,
                null);

        note.setID(Long.parseLong(cursor.getString(cursor.getColumnIndex(DbHelper._ID))));
        note.setName(cursor.getString(cursor.getColumnIndex(DbHelper.NAME)));
        note.setDate(cursor.getString(cursor.getColumnIndex(DbHelper.DATE)));
        note.setInfo(cursor.getString(cursor.getColumnIndex(DbHelper.INFO)));

        cursor.close();
        db.close();
        return note;
    }

    public void updateItem(long id, String name, String date, String info) {
        try {
        db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DbHelper.NAME, name);
        contentValues.put(DbHelper.DATE, date);
        contentValues.put(DbHelper.INFO, info);

        String[] ID = new String[] {Long.toString(id)};

        db.update(dbHelper.TABLE_NAME, contentValues, "_ID = ?", ID);

        db.setTransactionSuccessful();
        } catch (Exception ignored) {}
        finally {
        if (db != null) {
            if (db.inTransaction()) {
                db.endTransaction();
            }
            db.close();
        }
    }
    }

    public List<Note> getNotes() {
        db = dbHelper.getReadableDatabase();

        cursor = db.query(DbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        NoteList = new ArrayList<Note>();

        if (cursor.moveToFirst()) {

            int idColInd = cursor.getColumnIndex(DbHelper._ID);
            int nameColInd = cursor.getColumnIndex(DbHelper.NAME);
            int dateColInd = cursor.getColumnIndex(DbHelper.DATE);
            int infoColInd = cursor.getColumnIndex(DbHelper.INFO);

            do {
                Note note = new Note(cursor.getInt(idColInd),
                        cursor.getString(nameColInd),
                        cursor.getString(dateColInd),
                        cursor.getString(infoColInd));
                NoteList.add(note);
            } while (cursor.moveToNext());

        } else {
            Log.d(LOG_TAG, "В базе нет данных!");
        }

        cursor.close();
        db.close();
        return NoteList;
    }

    public void close() {
        dbHelper.close();
        db.close();
    }
}
