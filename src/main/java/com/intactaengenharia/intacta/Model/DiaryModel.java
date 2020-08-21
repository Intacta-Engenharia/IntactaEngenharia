package com.intactaengenharia.intacta.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intactaengenharia.intacta.Beans.Diary;
import com.intactaengenharia.intacta.Beans.Obra;
import com.intactaengenharia.intacta.Interfaces.DiaryModelListener;

import java.util.ArrayList;
import java.util.Collections;

public class DiaryModel {
    private static final String TAG = "DiaryModelError";
    //private boolean hasuploaded = false;
    private Obra obra;
    //private String keyobra;
    private int conttpush;

    public DiaryModel(Obra obra) {
        this.obra = obra;
    }


    public void getDiaries(DiaryModelListener.OnCompleteDiariesListener myCallback) {
        ArrayList<Diary> diarylist = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("obras")
                .child(obra.getKey()).child("diary");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    diarylist.clear();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Diary auxdiary = d.getValue(Diary.class);
                        auxdiary.setKey(dataSnapshot.getKey());
                        //di.setObra(obra);
                        auxdiary.setKey(auxdiary.getKey());
                        auxdiary.setDia(auxdiary.getData());
                        auxdiary.setOperavel(auxdiary.isOperavel());
                        //System.out.println( "diario " + di.getKey() + "\n" + di.getData() + di.getFiscal() + di.getLocal());
                        diarylist.add(auxdiary);
                    }
                    Collections.sort(diarylist, (o1, o2) -> o2.getDia().compareTo(o1.getDia()));
                    myCallback.callbackDiaries(diarylist);
                    //System.out.println(diarios);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled:" + databaseError.getMessage());
            }
        });

    }


}
