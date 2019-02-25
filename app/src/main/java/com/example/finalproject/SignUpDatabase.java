package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SignUpDatabase extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "users.db";

    private static final String TABLE_NAME = "userinfo";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    //private static final String COLUMN_ID = "id";

    SQLiteDatabase database;

    public SignUpDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //database = getWriteableDatabase();
    }

    public void onDowngrade(SignUpDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(database, oldVersion, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY ," + COLUMN_NAME + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }
}
