package com.intactaengenharia.intacta.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.intactaengenharia.intacta.service.constants.UserConstants;
import com.intactaengenharia.intacta.service.listeners.OnCompleteListener;
import com.intactaengenharia.intacta.service.listeners.ValidationListener;
import com.intactaengenharia.intacta.service.model.Obra;
import com.intactaengenharia.intacta.service.repository.DefaultRepository;
import com.intactaengenharia.intacta.service.repository.ObraRepository;
import com.intactaengenharia.intacta.service.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

public class ObraViewModel extends AndroidViewModel {

    //private Context context;
    private final DefaultRepository mRepository;
    private final String userCnpj;

    private final MutableLiveData<List<Obra>> mObras = new MutableLiveData<>();
    private final MutableLiveData<Obra> mObra = new MutableLiveData<>();
    private final MutableLiveData<ValidationListener> mValidation = new MutableLiveData<>();

    public LiveData<List<Obra>> getObras() {
        return mObras;
    }

    public LiveData<Obra> getObra() {
        return mObra;
    }

    public LiveData<ValidationListener> getValidation() {
        return mValidation;
    }

    public ObraViewModel(@NonNull Application application) {
        super(application);
        Preferences mPreferences = new Preferences(application.getApplicationContext());
        userCnpj = mPreferences.get(UserConstants.PREFERENCES.USER_CNPJ);
        mRepository = new DefaultRepository();
        //this.context = application.getApplicationContext();//quando precisa do contexto
    }

    public void onLoad() {
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("obras")
                .orderByChild("user")
                .equalTo(userCnpj);
        List<Obra> list = new ArrayList<>();
        mRepository.list(query, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot result) {
                if (result.hasChildren()) {
                    list.clear();
                    for (DataSnapshot d : result.getChildren()) {
                        Obra aux = d.getValue(Obra.class);
                        if (aux != null) {
                            aux.setKey(d.getKey());
                        }
                        list.add(aux);
                    }
                }
                mObras.setValue(list);
            }

            @Override
            public void onFailure(String message) {
                mObras.setValue(list);
                if (message.isEmpty()) {
                    mValidation.setValue(new ValidationListener("Desculpe, não foram encontradas obras deste cliente. Por favor, tente novamente mais tarde."));
                } else {
                    mValidation.setValue(new ValidationListener(message));
                }
            }
        });
    }


    public void get(String key) {
        Query query = FirebaseDatabase.getInstance().getReference().child("obras").child(key);
        mRepository.list(query, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot result) {
                Obra aux = result.getValue(Obra.class);
                if (aux != null) {
                    aux.setKey(result.getKey());
                }
                mObra.setValue(aux);
            }

            @Override
            public void onFailure(String message) {
                if (message.isEmpty()) {
                    mValidation.setValue(new ValidationListener("Desculpe, está obra não foi encontrada. Por favor, tente novamente mais tarde."));
                } else {
                    mValidation.setValue(new ValidationListener(message));
                }
            }
        });
    }

}
