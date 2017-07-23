package com.burgers.raffy.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.burgers.raffy.activities.GenerateActivity;
import com.burgers.raffy.giftcertsserver.R;
import com.burgers.raffy.models.Winners;

/**
 * Created by Neil on 6/4/2017.
 */

public class DialogUtils {
    public static AlertDialog createDialogList(final Context context, Activity activity, final String name, final String amount, final String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(name+" "+amount)
                .setPositiveButton(R.string.Delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseHelper.deleteData(key);
                    }
                })
                .setNegativeButton(R.string.share, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Utils.shareImage(context, Utils.encodeAsBitmap(key));
                            Toast.makeText(context, "success", Toast.LENGTH_SHORT);
                        }catch(Exception e){
                            e.printStackTrace();
                            Toast.makeText(context, "fail", Toast.LENGTH_SHORT);
                        }
                    }
                });
        return builder.create();
    }
}
