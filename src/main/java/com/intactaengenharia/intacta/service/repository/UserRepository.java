package com.intactaengenharia.intacta.service.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intactaengenharia.intacta.R;
import com.intactaengenharia.intacta.service.listeners.OnCompleteListener;
import com.intactaengenharia.intacta.service.listeners.ValidationListener;
import com.intactaengenharia.intacta.service.model.User;

public class UserRepository {
    private static final String TAG = "UserModelError";
    private final DatabaseReference mDatabase;
    private final Context context;

    public UserRepository(Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.context = context;
    }

    public void login(String cnpj, OnCompleteListener<User> myCallback) {
        mDatabase.child("users").orderByChild("cnpj").equalTo(cnpj)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                System.out.println(child);
                                User u = child.getValue(User.class);
                                if (u != null) {
                                    myCallback.onSuccess(u);
                                }
                            }

                        } else {
                            myCallback.onFailure("Nenhum usuário encontrado");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        myCallback.onFailure(context.getString(R.string.error_login_default_error));
                    }

                });
    }
}
