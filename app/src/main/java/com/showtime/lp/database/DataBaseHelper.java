package com.showtime.lp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author: Trouble Maker
 * @date: 2017/9/1 0001
 * @Description:
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(Context context) {
        super(context, "Record.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS record" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age INTEGER, info TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
