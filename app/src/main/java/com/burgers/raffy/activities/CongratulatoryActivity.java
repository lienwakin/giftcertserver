package com.burgers.raffy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.utils.Constants;

public class CongratulatoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulatory);
        displayCongrats();
    }

    private void displayCongrats(){
        Intent intent = getIntent();
        String name = intent.getStringExtra(Constants.NAME).toString();
        String amount = intent.getStringExtra(Constants.AMOUNT).toString();

        TextView congratsName = (TextView) findViewById(R.id.congatsName);
        TextView congratsAmount = (TextView) findViewById(R.id.congatsAmount);
        congratsName.setText(name+"!");
        congratsAmount.setText("You have just won "+amount+" pesos in credits!");
    }
}
