package com.mk.myknowldge.activities;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mk.myknowldge.R;
import com.mk.myknowldge.adapter.NotesAdapter;
import com.mk.myknowldge.helpers.DatabaseHelper;
import com.mk.myknowldge.model.Category;
import com.mk.myknowldge.model.Note;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    private boolean shouldUpdate;
    private int position;
    private int categoryId;
    private EditText inputName,inputNote;
    private String categoryName;
    private DatabaseHelper db;
    private String title = "";
    private String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        inputName = findViewById(R.id.title);
        inputNote = findViewById(R.id.content);
        Intent intent = getIntent();
        if(intent != null) {
            categoryName = intent.getStringExtra("category_name");
            categoryId = intent.getIntExtra("category_id", -1);
            shouldUpdate = intent.getBooleanExtra("should_update", false);
            position = intent.getIntExtra("position", position);
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            inputName.setText(title);
            inputNote.setText(content);
        }
        db = new DatabaseHelper(this);

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle.setText("Note"); //TODO : change this
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Handling Action Bar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.action_save:
                if (TextUtils.isEmpty(inputNote.getText().toString()) && TextUtils.isEmpty(inputName.getText().toString())) {
                    Toast.makeText(AddNoteActivity.this, "Enter your text!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                // check if user updating name
                if(!shouldUpdate) {
                    // create new title
                    createNote(inputName.getText().toString(), inputNote.getText().toString());
                } else {
                    // update name by it's id
                    updateNote(inputName.getText().toString(), inputNote.getText().toString());
                }
                Intent i = new Intent(AddNoteActivity.this, NotesActivity.class);
                i.putExtra("category_name", categoryName);
                i.putExtra("category_id", categoryId);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
            categoryName = intent.getStringExtra("category_name");
            categoryId = intent.getIntExtra("category_id", -1);
            shouldUpdate = intent.getBooleanExtra("should_update", false);
            position = intent.getIntExtra("position", position);
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            inputName.setText(title);
            inputNote.setText(content);


    }

    void createNote(String name, String note) {
        // newly inserted name id
        db.insertNote(name, categoryId, note);
        }


    /**
     * Updating name in db and updating
     * item in the list by its position
     */
    void updateNote(String title, String content) {
        Note n = new Note();
        // updating name text
        n.setTitle(title);
        n.setContent(content);

        // updating name in db
        db.updateNote(n);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AddNoteActivity.this, NotesActivity.class);
        i.putExtra("category_name", categoryName);
        startActivity(i);
    }


    //TODO : remove extras from this function
    /*private void setNoteData(final boolean shouldUpdate, final Note title, final int position) {
        this.shouldUpdate = shouldUpdate;
        this.position = position;
        this.title = title;
        /*LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(NotesActivity.this);
        alertDialogBuilderUserInput.setView(view);


        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && title != null) {
            inputNote.setText(title.getContent());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    Toast.makeText(NotesActivity.this, "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating name
                if (shouldUpdate && title != null) {
                    // update name by it's id
                    updateNote(inputNote.getText().toString(), position);
                } else {
                    // create new name
                    createNote(inputName.getText().toString(), inputNote.getText().toString());
                }
            }
        });*
    }*/
}

