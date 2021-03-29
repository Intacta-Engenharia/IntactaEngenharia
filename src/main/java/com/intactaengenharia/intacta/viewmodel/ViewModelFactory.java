package com.intactaengenharia.intacta.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.intactaengenharia.intacta.service.model.Document;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;


    public ViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //return (T) new modelClass(mApplication, mParam);
        if (modelClass == DiaryViewModel.class) {
            return (T) new DiaryViewModel(mApplication, mParam);

        } else if(modelClass == ContactViewModel.class){
            return (T) new ContactViewModel(mApplication, mParam);
        }else {
            return (T) new DocumentViewModel(mApplication, mParam);
        }
        //return modelClass.getConstructor(DiaryViewModel.class, int.class).newInstance(mApplication, mParam);

    }
}
