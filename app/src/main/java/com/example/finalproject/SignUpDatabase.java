package com.example.finalproject;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class SignUpDatabase extends SQLiteOpenHelper{

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "users.db";

    // table name
    private static final String TABLE_NAME = "userinfo";

    // table fields
    private static final String COLUMN_ID = "ID";
    String COLUMN_NAME = "name";
    String COLUMN_EMAIL = "email";
    String COLUMN_PASS = "password";


    // constructor
    SignUpDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create user information table
    // contains name, email, and password
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASS + " TEXT )");
    }

    // Upgrade user information
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add user information to database
    public boolean addUserInfo(String userName, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("email", email);
        contentValues.put("password", password);
        db.insert("userinfo", null, contentValues);
        return true;
    }

    // Search user information
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    // Update user information
    public boolean updateUserInfo(Integer id, String name, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        db.update("userinfo", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    // Delete user information
    public Integer deleteUserInfo(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("unserinfo",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllUserInfo() {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from userinfo", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}