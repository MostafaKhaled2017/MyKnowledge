package com.mk.myknowldge.model;

public class Note {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NOTE = "Name";
    public static final String COLUMN_TIMESTAMP = "Timestamp";
    public static final String CATEGORY_NAME = "CategoryName";


    private int id;
    private String note;
    private String timestamp;
    private String categoryName;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + CATEGORY_NAME + " TEXT"
                    + ")";

    public Note() {
    }

    public Note(int id, String note, String timestamp, String categoryName) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}