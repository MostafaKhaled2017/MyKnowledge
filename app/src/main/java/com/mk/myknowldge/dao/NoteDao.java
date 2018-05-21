package com.mk.myknowldge.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mk.myknowldge.model.Category;
import com.mk.myknowldge.utils.Constants;

import java.util.List;

/**
 * Created by Pavneet_Singh on 12/31/17.
 */

@Dao
public interface NoteDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_NOTE)
    List<Category> getNotes();

    /*
    * Insert the object in database
    * @param category, object to be inserted
    */
    @Insert
    long insertNote(Category category);

    /*
    * update the object in database
    * @param name, object to be updated
    */
    @Update
    void updateNote(Category repos);

    /*
    * delete the object from database
    * @param category, object to be deleted
    */
    @Delete
    void deleteNote(Category category);

    // Category... is varargs, here category is an array
    /*
    * delete list of objects from database
    * @param category, array of oject to be deleted
    */
    @Delete
    void deleteNotes(Category... category);

}
