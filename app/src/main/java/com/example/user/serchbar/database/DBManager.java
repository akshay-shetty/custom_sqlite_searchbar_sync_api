package com.example.user.serchbar.database;

/**
 * Created by Akshay.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.serchbar.network.model.ShowSqliteData;

import java.util.ArrayList;

import static com.example.user.serchbar.database.DatabaseHelper.TABLE_NAME;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String x, String y, String header, String display) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.X, x);
        contentValue.put(DatabaseHelper.Y, y);
        contentValue.put(DatabaseHelper.HEADER, header);
        contentValue.put(DatabaseHelper.DISPLAY,display );


        database.insert(TABLE_NAME, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.X, DatabaseHelper.Y, DatabaseHelper.HEADER,DatabaseHelper.DISPLAY};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public ArrayList<ShowSqliteData> getAllRecords() {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<ShowSqliteData> contacts = new ArrayList<ShowSqliteData>();
        ShowSqliteData dataList;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                dataList = new ShowSqliteData();
                dataList.setId(cursor.getString(0));

                dataList.setX(cursor.getString(1));
                dataList.setY(cursor.getString(2));
                dataList.setHeader(cursor.getString(3));
                dataList.setDisplay(cursor.getString(4));

                contacts.add(dataList);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public int update(long _id, String name, String desc,String nameVAl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.X, name);
        contentValues.put(DatabaseHelper.Y, desc);
        contentValues.put(DatabaseHelper.DISPLAY, nameVAl);

        int i = database.update(TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
