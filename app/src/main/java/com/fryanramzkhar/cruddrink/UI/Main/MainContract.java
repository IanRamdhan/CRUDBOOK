package com.fryanramzkhar.cruddrink.UI.Main;

import android.content.Context;

public interface MainContract {
    interface View{

    }

    interface Presenter{
        void logoutSession(Context context);
    }
}
