package com.burgers.raffy.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.utils.Constants;
import com.burgers.raffy.utils.DBUtils;
import com.burgers.raffy.utils.DialogUtils;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        readFromDatabase();
    }

    private void readFromDatabase(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutList);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        Cursor cursor = DBUtils.searchDB(this, null, null);

        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME));
            String amount = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_AMOUNT));
            String key = cursor.getString(cursor.getColumnIndex(Constants.COLUMN_KEY));

            String temp = "";
            temp = "" + cursor.getInt(cursor.getColumnIndex(Constants.TABLE_ID));
            temp +=  " " + name;
            temp += " " + amount;
            if(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_COLLECT)) == 1){
                temp += " claimed";
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
}
