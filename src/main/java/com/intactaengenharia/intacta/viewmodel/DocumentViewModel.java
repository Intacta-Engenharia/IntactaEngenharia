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
import com.intactaengenharia.intacta.service.model.Document;
import com.intactaengenharia.intacta.service.repository.DefaultRepository;

import java.util.ArrayList;
import java.util.List;

public class DocumentViewModel extends AndroidViewModel {

    //private Context context;
    private final DefaultRepository mRepository;
    private final String mKeyObra;

    private MutableLiveData<List<Document>> mDocuments = new MutableLiveData<>();

    public LiveData<List<Document>> getDcouments() {
        return mDocuments;
    }


    public DocumentViewModel(@NonNull Application application, String keyobra) {
        super(application);
        mKeyObra = keyobra;
        mRepository = new DefaultRepository();
        //this.context = application.getApplicationContext();//quando precisa do contexto
    }

    public void onLoad() {
        Query query = FirebaseDatabase.getInstance().getReference().child("obras")
                .child(mKeyObra).child("documents");
        List<Document> list = new ArrayList<>();
        mRepository.list(query, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot result) {
                if (result.hasChildren()) {
                    for (DataSnapshot d : result.getChildren()) {
                        if (d != null) {
                            Document aux = d.getValue(Document.class);
                            list.add(aux);
                        }
                    }
                    //System.out.println(diarios);
                }
                mDocuments.setValue(list);
            }

            @Override
            public void onFailure(String message) {
                mDocuments.setValue(list);
            }
        });
    }
}
