package com.intactaengenharia.intacta.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intactaengenharia.intacta.Beans.Document;
import com.intactaengenharia.intacta.Beans.Obra;
import com.intactaengenharia.intacta.Beans.User;
import com.intactaengenharia.intacta.Interfaces.ObraModelListener;
import java.util.ArrayList;
import java.util.Collections;


public class ObraModel {
    private static final String TAG = "ObraModelError";
    private User user;

    public ObraModel(User user){
        this.user = user;
    }
    public ObraModel(){
    }

    public void getObras(ObraModelListener.OnCompleteObrasListener myCallback ) {
        ArrayList<Obra> obralist = new ArrayList<>();
        Query databasereference;
        databasereference = FirebaseDatabase.getInstance().getReference()
                .child("obras")
                .orderByChild("user")
                .equalTo(user.getCnpj());

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
                myCallback.callbackObras(obralist);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    public void getObra(ObraModelListener.OnCompleteObraListener myCallback, String obrakey) {
        Query databasereference;
        databasereference = FirebaseDatabase.getInstance().getReference().child("obras").child(obrakey);
        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Obra obra;
                if (dataSnapshot.exists()) {
                    Obra o = dataSnapshot.getValue(Obra.class);
                    o.setKey(dataSnapshot.getKey());
                    obra = o;
                    myCallback.callbackObra(obra);
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
