package com.fryanramzkhar.cruddrink.UI.Profil;

import android.content.Context;

import com.fryanramzkhar.cruddrink.Model.LoginData;

public interface ProfilContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showSuccesUpdateUser(String msg);
        void showDataUser(LoginData loginData);


    }

    interface Presenter{
        void updateDataUser(Context context, LoginData loginData);
        void getDataUser(Context context);
        void logoutSession(Context context);

    }
}
