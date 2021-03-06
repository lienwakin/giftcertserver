package com.burgers.raffy.activities;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.utils.Constants;
import com.burgers.raffy.utils.DBUtils;
import com.burgers.raffy.utils.Utils;
import com.google.zxing.WriterException;

import java.util.Random;

public class GenerateActivity extends AppCompatActivity {
    private static char[] symbols;
    private final Random random = new Random();
    private String randomKey = "";
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
        Button generateQR = (Button)findViewById(R.id.generateQR);
        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEdit = (EditText)findViewById(R.id.nameEditText);
                EditText amountEdit = (EditText)findViewById(R.id.amountEditText);
                ImageView imageView = (ImageView) findViewById(R.id.qrCode);

                String name = nameEdit.getText().toString();
                String amount = amountEdit.getText().toString();

                randomKey = generateRandomString();
                while(isThereSameKey(randomKey)){
                    randomKey = generateRandomString();
                }

                Utils.hideKeyboard(GenerateActivity.this);
                try {
                    Bitmap bitmap = Utils.encodeAsBitmap(randomKey);
                    imageView.setImageBitmap(bitmap);
                    DBUtils.addToDB(GenerateActivity.this, name, amount, randomKey);
                    Utils.shareImage(GenerateActivity.this, bitmap);
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

    private boolean isThereSameKey(String key){
        // Filter results WHERE "title" = 'My Title'
        String selection = Constants.COLUMN_KEY + " = ? ";
        String[] selectionArgs = { key };

        Cursor cursor = DBUtils.searchDB(this, selection, selectionArgs);

        if(cursor.getCount()==0){
            return false;
        }else return true;
    }
}
