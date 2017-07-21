package com.burgers.raffy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.utils.Constants;
import com.burgers.raffy.utils.FirebaseHelper;
import com.burgers.raffy.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button generate = (Button) findViewById(R.id.generate);
        Button check = (Button) findViewById(R.id.check);
        Button list = (Button) findViewById(R.id.list);
//        Button delete = (Button) findViewById(R.id.delete);
        clickListener(generate, new Intent(this, GenerateActivity.class));
        clickListener(check, new Intent(this, CheckActivity.class));
        clickListener(list, new Intent(this, ListActivity.class));
//        clickListener(delete, new Intent(this, DeleteEntryActivity.class));
        FirebaseHelper firebase = new FirebaseHelper(this);
        firebase.signIn(Constants.ADMIN,Constants.ADMIN_PASSWORD);

    }

    private void clickListener(Button button, final Intent intent){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
