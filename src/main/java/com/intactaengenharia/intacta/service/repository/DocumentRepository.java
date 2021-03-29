package com.intactaengenharia.intacta.service.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intactaengenharia.intacta.service.listeners.OnCompleteListener;
import com.intactaengenharia.intacta.service.model.Document;
import com.intactaengenharia.intacta.service.model.Obra;

import java.util.ArrayList;
import java.util.List;

public class DocumentRepository {
    private static final String TAG = "DocumentModelError";
    private final String mKeyObra;


    public DocumentRepository(String keyobra, Context context) {
        this.mKeyObra = keyobra;
    }


    public void getDocuments(OnCompleteListener<List<Document>> callback) {
        ArrayList<Document> documentlist = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("obras")
                .child(mKeyObra).child("documents");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    documentlist.clear();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        if (d != null) {
                            Document auxdoc = d.getValue(Document.class);
                            documentlist.add(auxdoc);
                        }
                    }
                    callback.onSuccess(documentlist);
                    //System.out.println(diarios);
                } else {
                    Document doc = new Document();
                    doc.setTitle("Nenhum documento inserido");

                    documentlist.add(doc);
                    callback.onSuccess(documentlist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled:" + databaseError.getMessage());
            }
        });

    }

}
