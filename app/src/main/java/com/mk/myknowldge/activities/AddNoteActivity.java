package com.mk.myknowldge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mk.myknowldge.R;
import com.mk.myknowldge.helpers.DatabaseHelper;
import com.mk.myknowldge.model.Note;

public class AddNoteActivity extends AppCompatActivity {

    private boolean shouldUpdate;
    private int position;
    private int categoryId;
    private EditText inputTitle, inputNote;
    private String categoryName;
    private DatabaseHelper db;
    private String title = "";
    private String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        inputTitle = findViewById(R.id.title);
        inputNote = findViewById(R.id.content);
        Intent intent = getIntent();
        if (intent != null) {
            categoryName = intent.getStringExtra("category_name");
            categoryId = intent.getIntExtra("category_id", -1);
            shouldUpdate = intent.getBooleanExtra("should_update", false);
            position = intent.getIntExtra("position", position);
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            inputTitle.setText(title);
            inputNote.setText(content);
        }
       /* if(shouldUpdate){
            inputTitle.setEnabled(false);
            inputNote.setEnabled(false);
        }*///TODO : make this work
        db = new DatabaseHelper(this);

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle.setText("Note"); //TODO : change this
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_activity, menu);
        return true;
    }

    //Handling Action Bar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.action_save:
                if (TextUtils.isEmpty(inputNote.getText().toString()) && TextUtils.isEmpty(inputTitle.getText().toString())) {
                    Toast.makeText(AddNoteActivity.this, "Enter your text!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                // check if user updating name
                if (!shouldUpdate) {
                    // create new title
                    createNote(inputTitle.getText().toString(), inputNote.getText().toString());
                } else {
                    // update name by it's id
                    updateNote(inputTitle.getText().toString(), inputNote.getText().toString());
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
        inputTitle.setText(title);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AddNoteActivity.this, NotesActivity.class);
        i.putExtra("category_name", categoryName);
        i.putExtra("category_id", categoryId);
        startActivity(i);
    }

}

