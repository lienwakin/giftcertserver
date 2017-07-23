package com.burgers.raffy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.models.Winners;
import com.burgers.raffy.utils.Constants;
import com.burgers.raffy.utils.FirebaseHelper;
import com.burgers.raffy.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CheckActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String result;
    private String TAG = CheckActivity.class.getSimpleName();
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
        result = rawResult.getText().toString();

        FirebaseHelper.getDatabaseRef().addListenerForSingleValueEvent(valueEventListener);
    }

    public void processResult(Winners[] winners){
        for(int a=0;a<winners.length;a++){
            if(result.equals(winners[a].getKey()) && winners[a].isClaimed()){
                String name = winners[a].getName();
                String amount = winners[a].getAmount();

                winners[a].setClaimed(true);
                FirebaseHelper.updateData(winners[a],winners[a].getKey());

                Intent intent = new Intent(this, CongratulatoryActivity.class);
                intent.putExtra(Constants.NAME, name);
                intent.putExtra(Constants.AMOUNT, amount);
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(this, "Not valid", Toast.LENGTH_LONG).show();
        return;
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int a=0;
            Winners[] winners = new Winners[(int)dataSnapshot.getChildrenCount()];
            for(DataSnapshot data : dataSnapshot.getChildren()){
                winners[a] = new Winners((String)data.child(Constants.NAME).getValue(), (String)data.child(Constants.KEY).getValue(),
                        (String)data.child(Constants.AMOUNT).getValue(), (boolean)data.child(Constants.CLAIMED).getValue());
                a+=1;
            }
            processResult(winners);
            finish();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Utils.log(TAG + " " + databaseError.toString());
        }
    };
}
