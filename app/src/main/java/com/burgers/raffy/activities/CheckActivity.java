package com.burgers.raffy.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.utils.Constants;
import com.burgers.raffy.utils.DBUtils;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CheckActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        scanQR();
    }

    public void scanQR() {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    public void handleResult(Result rawResult) {
        String result = rawResult.getText().toString();

        // Filter results WHERE "title" = 'My Title'
        String selection = Constants.COLUMN_KEY + " = ? and " +
                Constants.COLUMN_COLLECT + " = ? ";
        String[] selectionArgs = { result, "0" };

        Cursor cursor = DBUtils.searchDB(this, selection, selectionArgs);

        if(cursor.getCount()>0){
            //start is null so move to next
            cursor.moveToNext();
            String name = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME));
            String amount = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_AMOUNT));
            Intent intent = new Intent(this, CongratulatoryActivity.class);
            intent.putExtra(Constants.NAME, name);
            intent.putExtra(Constants.AMOUNT, amount);
            startActivity(intent);

            String selectionNotClaimed = Constants.COLUMN_KEY + " = ?";
            String[] selectionArgsNotClaimed = { result };

            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_COLLECT, 1);

            DBUtils.updateDB(this, values, selectionNotClaimed, selectionArgsNotClaimed);
        }else Toast.makeText(this, "Not valid", Toast.LENGTH_LONG).show();
        finish();
    }
}
