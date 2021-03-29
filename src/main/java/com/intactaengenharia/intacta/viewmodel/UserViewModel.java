package com.intactaengenharia.intacta.viewmodel;

import android.app.Application;

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
import com.intactaengenharia.intacta.service.model.User;
import com.intactaengenharia.intacta.service.repository.DefaultRepository;
import com.intactaengenharia.intacta.service.repository.UserRepository;
import com.intactaengenharia.intacta.service.utils.Preferences;

public class UserViewModel extends AndroidViewModel {
    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DefaultRepository();
        mPreferences = new Preferences(application.getApplicationContext());
    }

    private final DefaultRepository mRepository;
    private final Preferences mPreferences;

    private final MutableLiveData<ValidationListener> mLogin = new MutableLiveData<>();

    public LiveData<ValidationListener> getLogin() {
        return mLogin;
    }

    private final MutableLiveData<Boolean> mLogged = new MutableLiveData<>();

    public LiveData<Boolean> getLogged() {
        return mLogged;
    }

    private final MutableLiveData<User> mUser = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return mUser;
    }

    public void doLogin(String cnpj) {
        if (cnpj.isEmpty()) {
            mLogin.setValue(new ValidationListener("Digite o cnpj"));
            return;
        }
        Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("cnpj").equalTo(cnpj);
        mRepository.get(query, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot result) {
                if (result.hasChildren()) {
                    for (DataSnapshot d : result.getChildren()) {
                        User aux = d.getValue(User.class);
                        mUser.setValue(aux);
                    }
                }else{
                    mLogin.setValue(new ValidationListener("Desculpe, usuário não foi encontrado. Por favor, tente novamente mais tarde."));
                }
            }

            @Override
            public void onFailure(String message) {
                if (message.isEmpty()) {
                    mLogin.setValue(new ValidationListener("Desculpe, usuário não foi encontrado. Por favor, tente novamente mais tarde."));
                } else {
                    mLogin.setValue(new ValidationListener(message));
                }
            }
        });
    }

    public void verifyPassword(String cnpj, String password, User user) {
        if (cnpj.isEmpty()) {
            mLogin.setValue(new ValidationListener("Digite o cnpj"));
            return;
        }
        if (password.isEmpty()) {
            mLogin.setValue(new ValidationListener("Digite a senha"));
            return;
        }
        if (cnpj.equals(user.getCnpj()) && password.equals(user.getPassword())) {
            mPreferences.store(UserConstants.PREFERENCES.USER_CNPJ, user.getCnpj());
            mPreferences.store(UserConstants.PREFERENCES.USER_NAME, user.getName());
            mLogin.setValue(new ValidationListener());
        } else {
            mLogin.setValue(new ValidationListener("Usuário ou senha incorretos."));
        }
    }

    public void verifyLoggedUser() {
        mLogged.setValue(!mPreferences.get(UserConstants.PREFERENCES.USER_CNPJ).equals(""));
    }

    public void doLogout() {
        mPreferences.remove(UserConstants.PREFERENCES.USER_CNPJ);
        mPreferences.remove(UserConstants.PREFERENCES.USER_NAME);
        mLogged.setValue(false);
    }

}
