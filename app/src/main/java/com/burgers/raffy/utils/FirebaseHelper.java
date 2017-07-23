package com.burgers.raffy.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.burgers.raffy.activities.MainActivity;
import com.burgers.raffy.models.Winners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Neil on 7/16/2017.
 */

public class FirebaseHelper {
    private static FirebaseAuth mAuth;
    private static Context context;
    private static final String TAG = FirebaseHelper.class.getSimpleName();
    private static DatabaseReference mDatabase;
    public FirebaseHelper(Context context){
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }
    public static void signIn(String email, String password) {
        try {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mDatabase = FirebaseDatabase.getInstance().getReference(Constants.WINNERS);
                    Toast.makeText(context, "Authentication success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setAction(Constants.SET_MAIN_ACTIVITY);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();

                }
                }
            });
        }catch(Exception e){
            Utils.log(TAG+" "+e.getMessage());
        }
    }

    public static void updateData(Winners winner, String key){
        mDatabase.child(key).setValue(winner);
    }

    public static DatabaseReference getDatabaseRef(){
        return mDatabase;
    }

    public static void deleteData(String key){
        mDatabase.child(key).removeValue();
    }
}
