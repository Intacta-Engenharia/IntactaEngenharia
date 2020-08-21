package com.intactaengenharia.intacta.Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intactaengenharia.intacta.Beans.User;
import com.intactaengenharia.intacta.Interfaces.UserModelListener;

public class UserModel {
    private static final String TAG = "UserModelError";

    public UserModel() {
    }

    public void getLogin(UserModelListener.OnCompleteLoginListener myCallback, String cnpj) {
        Query reference = FirebaseDatabase.getInstance()
                .getReference().child("users").orderByChild("cnpj")
                .equalTo(cnpj);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        User u = d.getValue(User.class);
                        if (u != null) {
                            if (cnpj.equals(u.getCnpj())) {
                                myCallback.callbackLogin(u);
                            }
                        }
                    }
                } else {
                    myCallback.callbackLogin(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                myCallback.callbackLogin(null);

            }
        });

    }

    public void getUser(UserModelListener.OnCompleteUserListener myCallback, String cnpj) {
        Query reference = FirebaseDatabase.getInstance()
                .getReference().child("users").orderByChild("cnpj")
                .equalTo(cnpj);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        User u = d.getValue(User.class);
                        if (u != null) {
                            myCallback.callbackUser(u);

                        }
                        myCallback.callbackUser(null);
                    }
                } else {
                    myCallback.callbackUser(null);
                    //Toast.makeText(activity, "Erro ao localizar dados...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
