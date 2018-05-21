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
public interface MainCategoryDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_MAIN_CATEGORIES)
    List<Category> getMainCategories();

    /*
    * Insert the object in database
    * @param Category, object to be inserted
    */
    @Insert
    long insertMainCategory(Category Category);

    /*
    * update the object in database
    * @param Category, object to be updated
    */
    @Update
    void updateMainCategory(Category repos);

    /*
    * delete the object from database
    * @param Category, object to be deleted
    */
    @Delete
    void deleteMainCategory(Category Category);

    // Category... is varargs, here Category is an array
    /*
    * delete list of objects from database
    * @param Category, array of oject to be deleted
    */
    @Delete
    void deleteMainCategories(Category... Category);

}
