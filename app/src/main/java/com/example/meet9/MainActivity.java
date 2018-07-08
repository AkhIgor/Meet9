package com.example.meet9;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private DataBase db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Note> NoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DataBase(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteList = db.getNotes();

        adapter = new AdapterForNote(NoteList, this);
        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNote = new Intent(MainActivity.this, CreatingActivity.class);
                startActivity(createNote);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        NoteList = db.getNotes();

        adapter = new AdapterForNote(NoteList, this);
        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

//        SharedPreferences prefs = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        float fSize = Float.parseFloat(prefs.getString(
//                getString(R.string.pref_size), "20"));
//// применяем настройки в текстовом поле
//        mEditText.setTextSize(fSize);
//
//        String regular = prefs.getString(getString(R.string.pref_style), "");
//        int typeface = Typeface.NORMAL;
//
//        if (regular.contains("Полужирный"))
//            typeface += Typeface.BOLD;
//
//        if (regular.contains("Курсив"))
//            typeface += Typeface.ITALIC;
//
//// меняем настройки в EditText
//        mEditText.setTypeface(null, typeface);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (db != null)
            db.close();
    }

//    @Override
//    public void openEditor(String text, String color, long id, int position) {
//
//    }
//
//    @Override
//    public void delete(final long id) {
//
//    }
}
