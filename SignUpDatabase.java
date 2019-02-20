
package com.example.finalproject;

import android.database.sqlite.SQLiteOpenHelper;

import andriod.content.Context;
import andriod.database.sqlite.SQLiteDatabase;

public class SignUpDatabase extends SQLiteOpenHelper
{

    private static final in DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "users.db";

    private static final String TABLE_NAME = "username";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    //private static final String COLMN_ID = "id";

    SQLiteDatabase database;

    public DatabaseHandler(Context context){
        super(context, name, factory, version);
        database = getWriteableDatabase();
    }

    @Override
    public void onCreate(SignUpDatabase db){
        bd.execSQL("CREATE TABLE"+TABLE_NAME+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY "+COLUMN_NAME+" TEXT ")
    }

    @Override
    public void onUpgrade(SignUpDatabasedb, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE EXISTS")
    }
}

