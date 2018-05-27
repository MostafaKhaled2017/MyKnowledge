package com.mk.myknowldge.model;

public class Note {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "Timestamp";
    public static final String CATEGORY_ID = "CategoryId";


    private int id;
    private String title;
    private String content;
    private String timestamp;
    private String categoryId;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_CONTENT + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + CATEGORY_ID + " INTEGER"
                    + ")";

    public Note() {
    }

    public Note(int id, String title, String content, String timestamp, String categoryId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {this.title = title; }

    public void setCategoryId(int categoryName) {
        this.categoryId = categoryId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}