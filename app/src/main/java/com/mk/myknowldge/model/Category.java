package com.mk.myknowldge.model;

public class Category {
    public static final String TABLE_NAME = "category";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "CategoryName";
    public static final String COLUMN_TIMESTAMP = "Timestamp";

    private int id;
    private String cName;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT"
                    + ")";

    public Category() {
    }

    public Category(int id, String cName) {
        this.id = id;
        this.cName = cName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return cName;
    }

    public void setName(String cName) {
        this.cName = cName;
    }

    public void setId(int id) {
        this.id = id;
    }

}