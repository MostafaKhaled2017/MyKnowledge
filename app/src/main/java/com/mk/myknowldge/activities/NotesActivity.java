package com.mk.myknowldge.activities;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mk.myknowldge.R;
import com.mk.myknowldge.adapter.NotesAdapter;
import com.mk.myknowldge.helpers.DatabaseHelper;
import com.mk.myknowldge.model.Note;
import com.mk.myknowldge.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    private NotesAdapter mAdapter;
    private List<Note> notesList = new ArrayList<>();
    boolean shouldUpdate;
    int position;
    Note note;
    private TextView noNotesView;

    private String categoryName = "My Knowledge";
    private int categoryId = -1;


    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null)
            //if (intent.getStringExtra("tag") == "categories") {
                categoryName = intent.getStringExtra("category_name");
                categoryId = intent.getIntExtra("category_id", -1);
            //}//TODO
            /*else
                handleIntent(intent);*/


                TextView toolbarTitle = findViewById(R.id.toolbar_title);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                toolbarTitle.setText(categoryName);
                if (getSupportActionBar() != null)
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                noNotesView = findViewById(R.id.empty_view);


                db = new DatabaseHelper(this);
                db.setCategoryId(categoryId);

                notesList.addAll(db.getAllNotes());

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setNoteData(false, null, -1);
                        Intent i = new Intent(NotesActivity.this, AddNoteActivity.class);
                        i.putExtra("category_name", categoryName);
                        i.putExtra("category_id", categoryId);
                        i.putExtra("should_update", shouldUpdate);
                        i.putExtra("position", position);
                        i.putExtra("title", "");
                        i.putExtra("content", "");
                        startActivity(i);
                    }
                });

                mAdapter = new NotesAdapter(this, notesList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);


                toggleEmptyNotes();

                /**
                 * On long press on RecyclerView item, open alert dialog
                 * with options to choose
                 * Edit and Delete
                 * */
                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                        recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        setNoteData(true, notesList.get(position), position);
                        sendIntent(position);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        showActionsDialog(position);
                    }
                }));
            }



    private void setNoteData(final boolean shouldUpdate, final Note note, final int position) {
        this.shouldUpdate = shouldUpdate;
        this.position = position;
        this.note = note;
    }

    /**
     * Deleting name from SQLite and removing the
     * item from the list by its position
     */
    private void deleteNote(int position) {
        // deleting the name from db
        db.deleteNote(notesList.get(position));

        // removing the name from the list
        notesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyNotes();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setNoteData(true, notesList.get(position), position);
                    sendIntent(position);

                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }
    //TODO : think about remove the fab icon and replace it with add icon in the bar

    public void sendIntent(int position) {
        Intent i = new Intent(NotesActivity.this, AddNoteActivity.class);
        i.putExtra("category_name", categoryName);
        i.putExtra("category_id", categoryId);
        i.putExtra("should_update", true);
        i.putExtra("position", position);
        i.putExtra("title", notesList.get(position).getTitle());
        i.putExtra("content", notesList.get(position).getContent());
        i.putExtra("id", notesList.get(position).getId());
        startActivity(i);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //if (intent.getStringExtra("tag") == "categories") {
            categoryName = intent.getStringExtra("category_name");
            categoryId = intent.getIntExtra("category_id", -1);
        /*} else
            handleIntent(intent);*/ //TODO

    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Toggling list and empty notes view
     */
    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0
        if (db.getNotesCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(NotesActivity.this, CategoriesActivity.class);
        startActivity(i);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        notesList.clear();
        notesList.addAll(db.getAllNotes());

        // refreshing the list
        mAdapter.notifyDataSetChanged();

        toggleEmptyNotes();
    }*/
}