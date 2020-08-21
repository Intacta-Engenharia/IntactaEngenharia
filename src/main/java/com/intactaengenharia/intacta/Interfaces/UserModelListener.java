package com.intactaengenharia.intacta.Interfaces;

import com.intactaengenharia.intacta.Beans.User;

public interface UserModelListener {

    interface OnCompleteLoginListener {
        void callbackLogin(User user);
    }
    interface OnCompleteUserListener {
        void callbackUser(User user);
    }

}
