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

    private static final int CREATING = 1;
    private static final int CHANGING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DataBase(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                NoteList = db.getNotes();
            }
        }).start();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterForNote(NoteList, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNote = new Intent(MainActivity.this, CreatingActivity.class);
                startActivityForResult(createNote, CREATING);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            Intent intent = getIntent();
            switch (requestCode) {
                case CREATING:
                {
                    long id = NoteList.get(NoteList.size() - 1).getID();
                    Note note = new Note( id, data.getStringExtra("Name"), data.getStringExtra("Date"), data.getStringExtra("Content"));
                    NoteList.add(note);

                    adapter.notifyDataSetChanged();

                    break;
                }
                case CHANGING:

                    Note note = new Note(data.getIntExtra("ID", 0), data.getStringExtra("Name"), data.getStringExtra("Date"), data.getStringExtra("Content"));
                    NoteList.set(data.getIntExtra("Position", 0), note);

                    adapter.notifyDataSetChanged();

                    break;
            }
        }
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
}
