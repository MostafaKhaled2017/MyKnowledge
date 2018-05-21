package com.mk.myknowldge.model;

public class Category {
    public static final String TABLE_NAME = "category";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "cName";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String cName;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
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

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public void setId(int id) {
        this.id = id;
    }

}