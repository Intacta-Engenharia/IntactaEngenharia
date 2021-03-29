package com.intactaengenharia.intacta.service.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intactaengenharia.intacta.service.constants.UserConstants;
import com.intactaengenharia.intacta.service.listeners.OnCompleteListener;
import com.intactaengenharia.intacta.service.model.Obra;
import com.intactaengenharia.intacta.service.utils.Preferences;

import java.util.ArrayList;
import java.util.List;


public class ObraRepository {
    private static final String TAG = "ObraModelError";
    private final String userCnpj;
    private final Preferences mPreferences;

    public ObraRepository(Context context) {
        mPreferences = new Preferences(context);
        userCnpj = mPreferences.get(UserConstants.PREFERENCES.USER_CNPJ);
    }

    public void getObras(OnCompleteListener<List<Obra>> listener) {
        List<Obra> obralist = new ArrayList<>();
        Query databasereference;
        databasereference = FirebaseDatabase.getInstance().getReference()
                .child("obras")
                .orderByChild("user")
                .equalTo(userCnpj);

        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                obralist.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Obra o = d.getValue(Obra.class);
                    if (o != null) {
                        o.setKey(d.getKey());
                        obralist.add(o);
                    } else {
                        Log.e(TAG, "Obras não encontradas");
                    }
                }
                listener.onSuccess(obralist);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    public void getObra(String key, OnCompleteListener<Obra> listener) {
        Query databasereference;
        databasereference = FirebaseDatabase.getInstance().getReference().child("obras").child(key);
        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Obra obra;
                if (dataSnapshot.exists()) {
                    Obra o = dataSnapshot.getValue(Obra.class);
                    o.setKey(dataSnapshot.getKey());
                    obra = o;
                    listener.onSuccess(obra);
                } else {
                    Log.e(TAG, "Obra não encontrada");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    /*public void getDocuments(ObraModelListener.OnCompleteDocumentsListener myCallback, Obra obra) {
        ArrayList<Document> documents = new ArrayList<>();
        DatabaseReference docsdb = FirebaseDatabase.getInstance()
                .getReference("obras")
                .child(obra.getKey());
        documents.add(new Document("Contrato", obra.getContracturl()));
        documents.add(new Document("ART", obra.getArt()));
        documents.add(new Document("ART(Execução)", obra.getArtexecution()));
        documents.add(new Document("Cronograma", obra.getCronogram()));
        documents.add(new Document("Ordem de serviço", obra.getServiceorder()));
        Collections.sort(documents, (o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
        //Log.i("Key", obra.getKey());
        myCallback.callbackDocument(documents);
    }*/


}
