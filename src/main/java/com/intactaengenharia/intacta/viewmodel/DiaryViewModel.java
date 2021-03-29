package com.intactaengenharia.intacta.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.intactaengenharia.intacta.service.listeners.OnCompleteListener;
import com.intactaengenharia.intacta.service.model.Diary;
import com.intactaengenharia.intacta.service.repository.DefaultRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DiaryViewModel extends AndroidViewModel {

    //private Context context;
    private final DefaultRepository mRepository;
    private final String mKeyObra;

    private final MutableLiveData<List<Diary>> mDiaries = new MutableLiveData<>();

    public LiveData<List<Diary>> getDiaries() {
        return mDiaries;
    }


    public DiaryViewModel(@NonNull Application application, String keyobra) {
        super(application);
        mKeyObra = keyobra;
        mRepository = new DefaultRepository();
    }

    public void onLoad() {
        Query query = FirebaseDatabase.getInstance().getReference().child("obras").child(mKeyObra).child("diary");
        List<Diary> list = new ArrayList<>();
        mRepository.list(query, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot result) {
                if (result.hasChildren()) {
                    list.clear();
                    for (DataSnapshot d : result.getChildren()) {
                        Diary aux = d.getValue(Diary.class);
                        if (aux != null) {
                            aux.setKey(d.getKey());

                        }
                        aux.setDia(aux.getData());
                        aux.setOperavel(aux.isOperavel());
                        list.add(aux);
                    }
                    Collections.sort(list, (o1, o2) -> o2.getDia().compareTo(o1.getDia()));
                }
                mDiaries.setValue(list);
            }

            @Override
            public void onFailure(String message) {
                mDiaries.setValue(list);
            }
        });
    }

}
