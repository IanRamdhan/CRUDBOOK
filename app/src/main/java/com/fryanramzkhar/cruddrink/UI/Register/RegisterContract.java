package com.fryanramzkhar.cruddrink.UI.Register;

import com.fryanramzkhar.cruddrink.Model.LoginData;

public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String msg);
        void showSuccess(String msg);

    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}
