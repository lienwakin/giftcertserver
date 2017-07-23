package com.burgers.raffy.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.models.Winners;
import com.burgers.raffy.utils.Constants;
import com.burgers.raffy.utils.DialogUtils;
import com.burgers.raffy.utils.FirebaseHelper;
import com.burgers.raffy.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ListActivity extends AppCompatActivity {
    private String TAG = ListActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        readFromDatabase();
    }

    private void readFromDatabase() {
        FirebaseHelper.getDatabaseRef().addListenerForSingleValueEvent(valueEventListener);
    }

    private void processData(Winners[] winners){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutList);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        String temp = "";
        for(int a=0;a<winners.length;a++) {
            temp = "";
            String name = winners[a].getName();
            String amount = winners[a].getAmount();
            String key = winners[a].getKey();
            temp += name + " " + amount;
            if (winners[a].isClaimed()) {
                temp += " claimed!";
            }
            Button button = new Button(this);
            button.setText(temp);
            button.setTextSize(20);
            setOnClickListener(button, name, amount, key);
            linearLayout.addView(button);
        }
    }
    private void setOnClickListener(Button button, final String name, final String amount, final String key){
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DialogUtils.createDialogList(ListActivity.this, ListActivity.this, name, amount, key).show();
            }
        });
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
            processData(winners);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Utils.log(TAG + " " + databaseError.toString());
        }
    };

}
