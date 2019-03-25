package com.fryanramzkhar.cruddrink.UI.Main;

import android.content.Context;

import com.fryanramzkhar.cruddrink.Utils.SessionManager;

public class MainPresenter implements MainContract.Presenter {
    @Override
    public void logoutSession(Context context) {
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
