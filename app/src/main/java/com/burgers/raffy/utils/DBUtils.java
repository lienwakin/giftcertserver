package com.burgers.raffy.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Neil on 6/3/2017.
 */

public class DBUtils {
    public static void addToDB(Context context, String name, String amount, String randomKey) {
        SQLHelper sqlHelper = new SQLHelper(context);
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME, name);
        values.put(Constants.COLUMN_AMOUNT, amount);
        values.put(Constants.COLUMN_KEY, randomKey);

        db.insert(Constants.TABLE_NAME, null, values);
    }

    public static Cursor searchDB(Context context, String selection, String[] selectionArgs){
        SQLHelper sqlHelper = new SQLHelper(context);
        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        String[] projection = {
                Constants.TABLE_ID,
                Constants.COLUMN_NAME,
                Constants.COLUMN_AMOUNT,
                Constants.COLUMN_COLLECT,
                Constants.COLUMN_KEY
        };

        Cursor cursor = db.query(
                Constants.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        return cursor;
    }

    public static void updateDB(Context context, ContentValues values, String selection, String[] selectionArgs){
        SQLHelper sqlHelper = new SQLHelper(context);
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        db.update(Constants.TABLE_NAME, values, selection, selectionArgs);
    }
}
