package com.burgers.raffy.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Neil on 5/23/2017.
 */

public class SQLHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    private static final String GIFTCERTS_TABLE_CREATE =
            "CREATE TABLE " + Constants.TABLE_NAME + " (" +
                    Constants.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.COLUMN_NAME + " TEXT, " +
                    Constants.COLUMN_KEY + " TEXT, " +
                    Constants.COLUMN_COLLECT + " INTEGER DEFAULT 0, " +
                    Constants.COLUMN_AMOUNT + " TEXT);";
    public SQLHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GIFTCERTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
