package com.example.user.serchbar.database;

/**
 * Created by Akshay.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "COUNTRIES";

    // Table columns
    public static final String _ID = "_id";


    public static final String X="x";
    public static final String Y= "y";
    public static final String HEADER= "header";

    public static final String DISPLAY= "display";



    // Database Information
    static final String DB_NAME = "DATAA.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + X + " TEXT ,"+Y+" TEXT,"+HEADER+" TEXT,"+DISPLAY+" TEXT);";


    private static final String CREATE_TABLE_TWO = "select * from COUNTRIES";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
