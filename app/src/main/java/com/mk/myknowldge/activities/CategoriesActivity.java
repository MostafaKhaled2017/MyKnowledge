package com.mk.myknowldge.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mk.myknowldge.R;
import com.mk.myknowldge.adapter.CategoriesAdapter;
import com.mk.myknowldge.helpers.DatabaseHelper;
import com.mk.myknowldge.model.Category;
import com.mk.myknowldge.utils.MyDividerItemDecoration;
import com.mk.myknowldge.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private CategoriesAdapter mAdapter;
    private List<Category> categoriesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noThingView;

    private DatabaseHelper db;

    //TODO : make a dialogu appear when return back from note without saving
    //TODO : use firebase or database on server to store databases of the app (search for that)
    //TODO : before publish add the project to bitBucket as private and remove it from git hub
    //TODO : enhance the design of the app and take ideas from memo app and edit the fab icon design
    //TODO : revise all the yellow and other marks
    //TODO : find a way to handle database lost on the old phone not the new and find a way to make a backup to it and allow user to export it
    //TODO : optimize code as possible multiple times
    //TODO : add welcome screen with the app name and icon (not nesecarry)
    //TODO : add the time and day to the date
    //TODO : remove unused files (as xml, java, photos, values ...)
    //TODO : the to-do(s) from notes on keep
    //TODO : add search engine (prefer after update)
    //TODO : add about app page and in it add contact developer by whats for example
    //TODO : Make keyboard appear when add notes activity opens
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTitle.setText("Main Categories");
        toolbarTitle.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_view);
        noThingView = findViewById(R.id.empty_view);

        db = new DatabaseHelper(this);

        categoriesList.addAll(db.getAllCategories());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryDialog(false, null, -1);
            }
        });

        mAdapter = new CategoriesAdapter(this, categoriesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyCategories();

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_add_activity; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Inserting new name in db
     * and refreshing the list
     */
    private void createCategory(String category) {
        // inserting name in db and getting
        // newly inserted name id
        long id = db.insertCategory(category);

        // get the newly inserted name from db
        Category n = db.getCategory(id);

        // Clear the list then add categories again to be sorted
        if (n != null) {
            categoriesList.clear();
            categoriesList.addAll(db.getAllCategories());

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyCategories();
        }
    }

    /**
     * Updating name in db and updating
     * item in the list by its position
     */
    private void updateCategory(String category, int position) {
        Category n = categoriesList.get(position);
        // updating name text
        n.setName(category);

        // updating name in db
        db.updateCategory(n);

        // refreshing the list
        categoriesList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyCategories();
    }

    /**
     * Deleting name from SQLite and removing the
     * item from the list by its position
     */
    private void deleteCategory(int position) {
        // deleting the name from db
        db.deleteCategory(categoriesList.get(position));

        // removing the name from the list
        categoriesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        if (categoriesList.isEmpty()) {
            long bookdId = db.insertCategory("Books");
            long coursesId = db.insertCategory("Courses");
            long lifeId = db.insertCategory("Life Lessons");
            long othersId = db.insertCategory("Others");
            categoriesList.add(db.getCategory(bookdId));
            categoriesList.add(db.getCategory(coursesId));
            categoriesList.add(db.getCategory(lifeId));
            categoriesList.add(db.getCategory(othersId));
        }
        toggleEmptyCategories();
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
                    showCategoryDialog(true, categoriesList.get(position), position);
                } else {
                    deleteCategory(position);
                }
            }
        });
        builder.show();
    }

    /**
     * Shows alert dialog with EditText options to enter / edit
     * a name.
     * when shouldUpdate=true, it automatically displays old name and changes the
     * button text to UPDATE
     */
    private void showCategoryDialog(final boolean shouldUpdate, final Category category, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(CategoriesActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputCategory = view.findViewById(R.id.dialog_value);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_category_name) : getString(R.string.lbl_edit_category_name));

        if (shouldUpdate && category != null) {
            inputCategory.setText(category.getName());
        }
        alertDialogBuilderUserInput
                .setCancelable(true)
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
                if (TextUtils.isEmpty(inputCategory.getText().toString())) {
                    Toast.makeText(CategoriesActivity.this, "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating name
                if (shouldUpdate && category != null) {
                    // update name by it's id
                    updateCategory(inputCategory.getText().toString(), position);
                } else {
                    // create new name
                    createCategory(inputCategory.getText().toString());
                }
            }
        });
    }

    /**
     * Toggling list and empty categories view
     */
    private void toggleEmptyCategories() {
        // you can check categoriesList.size() > 0

        if (db.getCategoriesCount() > 0) {
            noThingView.setVisibility(View.GONE);
        } else {
            noThingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}