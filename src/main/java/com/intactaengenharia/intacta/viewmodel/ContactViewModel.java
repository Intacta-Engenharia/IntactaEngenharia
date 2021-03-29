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
import com.intactaengenharia.intacta.service.model.Contact;
import com.intactaengenharia.intacta.service.model.Document;
import com.intactaengenharia.intacta.service.repository.DefaultRepository;
import com.intactaengenharia.intacta.service.repository.DocumentRepository;
import com.intactaengenharia.intacta.service.utils.Alerts;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {


    private final DefaultRepository mRepository;
    private final String mKeyObra;

    private MutableLiveData<List<Contact>> mContacts = new MutableLiveData<>();

    public LiveData<List<Contact>> getContacts() {
        return mContacts;
    }


    public ContactViewModel(@NonNull Application application, String keyobra) {
        super(application);
        mRepository = new DefaultRepository();
        mKeyObra = keyobra;
        //this.context = application.getApplicationContext();//quando precisa do contexto
    }

    public void onLoad() {
        Query query = FirebaseDatabase.getInstance().getReference().child("obras").child(mKeyObra).child("contacts");
        List<Contact> list = new ArrayList<>();
        mRepository.list(query, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot result) {
                if (result.hasChildren()) {
                    for (DataSnapshot d : result.getChildren()) {
                        Contact aux = d.getValue(Contact.class);
                        list.add(aux);
                    }
                }
                mContacts.setValue(list);

            }

            @Override
            public void onFailure(String message) {
                mContacts.setValue(list);
            }
        });
    }

}
