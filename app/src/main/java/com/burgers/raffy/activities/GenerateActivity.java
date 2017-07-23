package com.burgers.raffy.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.models.Winners;
import com.burgers.raffy.utils.Constants;
import com.burgers.raffy.utils.FirebaseHelper;
import com.burgers.raffy.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import java.util.Random;

public class GenerateActivity extends AppCompatActivity {
    private static char[] symbols;
    private final Random random = new Random();
    private String randomKey = "";
    private static Button generateQR;
    private String TAG = GenerateActivity.class.getSimpleName();
    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        for (char ch = 'A'; ch <= 'Z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        generateQR = (Button)findViewById(R.id.generateQR);
        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEdit = (EditText) findViewById(R.id.nameEditText);
                EditText amountEdit = (EditText) findViewById(R.id.amountEditText);
                ImageView imageView = (ImageView) findViewById(R.id.qrCode);

                String name = nameEdit.getText().toString();
                String amount = amountEdit.getText().toString();

                randomKey = FirebaseHelper.getDatabaseRef().push().getKey();

                Utils.hideKeyboard(GenerateActivity.this);
                try {
                    Bitmap myLogo = BitmapFactory.decodeResource(getResources(), R.drawable.raffy);
                    final Bitmap bitmap = Utils.encodeAsBitmap(randomKey);
                    myLogo = Bitmap.createScaledBitmap(myLogo, 1000, 1000, true);
                    Bitmap merged = Utils.mergeBitmaps(bitmap,myLogo);
                    imageView.setImageBitmap(merged);
                    FirebaseHelper.updateData(new Winners(name, randomKey, amount, false), randomKey);
                    generateQR.setText(R.string.share);
                    generateQR.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            Utils.shareImage(GenerateActivity.this, bitmap);
                        }
                    });
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String generateRandomString(){
        String randomString = "";
        for(int a = 0; a < 10; a++){
            randomString += symbols[random.nextInt(symbols.length)];
        }
        return randomString;
    }
}
