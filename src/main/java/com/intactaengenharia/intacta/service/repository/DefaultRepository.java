package com.intactaengenharia.intacta.service.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intactaengenharia.intacta.service.listeners.OnCompleteListener;
import com.intactaengenharia.intacta.service.model.Document;
import com.intactaengenharia.intacta.service.model.Obra;

import java.util.ArrayList;
import java.util.List;

public class DefaultRepository {
    private static final String TAG = "GenericModelError";

    public void list(Query reference, OnCompleteListener<DataSnapshot> callback) {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("obras")
//                .child(mKeyObra).child("documents");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    callback.onSuccess(dataSnapshot);
                } else {
                    callback.onFailure("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled:" + databaseError.getMessage());
                callback.onFailure("Um erro inesperado ocorreu. Tente novamente mais tarde.");
            }
        });

    }

    public void get(Query reference, OnCompleteListener<DataSnapshot> callback) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callback.onSuccess(dataSnapshot);
                } else {
                    callback.onFailure("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
                callback.onFailure("Um erro inesperado ocorreu. Tente novamente mais tarde.");
            }
        });

    }

}
