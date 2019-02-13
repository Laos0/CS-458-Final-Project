package com.example.finalproject;

import android.database.sqlite.SQLiteOpenHelper;

//import andriod.content.Context;
//import andriod.database.sqlite.SQLiteDatabase;

public class SignUpDatabase extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "users.db";

    private static final String TABLE_NAME = "userinfo";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    //private static final String COLUMN_ID = "id";

    SQLiteDatabase database;

    public SignUpDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWriteableDatabase();
    }

    @Override
    public void onCreate(SignUpDatabase db){
        bd.execSQL("CREATE TABLE"+TABLE_NAME+" ( "+
                COLUMN_ID+" INTEGER PRIMARY KEY "+
                COLUMN_NAME+" TEXT ");
    }

    @Override
    public void onUpgrade(SignUpDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }
    public void onDowngrade(SignUpDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
