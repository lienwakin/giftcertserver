package com.burgers.raffy.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

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
    private static FirebaseUser user;
    public FirebaseHelper(Context context){
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }
    public static FirebaseUser signIn(String email, String password) {
        try {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        user = mAuth.getCurrentUser();
                        firebaseData();
                        Toast.makeText(context, "Authentication success", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();

                    }
                }
            });
            return user;
        }catch(Exception e){
            Utils.log(TAG+" "+e.getMessage());
        }
        return null;
    }

    public static void firebaseData(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("winners");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Utils.log(dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Winners winner = new Winners("ipe", "654");
        mDatabase.child("1").setValue(winner);
    }
}
