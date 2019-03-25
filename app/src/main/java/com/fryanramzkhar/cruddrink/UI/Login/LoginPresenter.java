package com.fryanramzkhar.cruddrink.UI.Login;

import android.content.Context;

import com.fryanramzkhar.cruddrink.Data.Remote.ApiClient;
import com.fryanramzkhar.cruddrink.Data.Remote.ApiInterface;
import com.fryanramzkhar.cruddrink.Model.LoginData;
import com.fryanramzkhar.cruddrink.Model.LoginResponse;
import com.fryanramzkhar.cruddrink.Utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        if (username.isEmpty()){
            view.usernameError("Username tidak boleh kosong !");
            return;
        }
        if (password.isEmpty()){
            view.paswordError("Password tidak bolleh kosong !");
            return;
        }

        view.showProgress();

        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        if (response.body().getData() != null){
                            LoginData loginData = response.body().getData();
                            String msg = response.body().getMessage();
                            view.loginSuccess(msg, loginData);
                        }else {
                            view.loginFailure("Data kosong !");
                        }
                    }else {
                        view.loginFailure(response.body().getMessage());
                    }
                }else {
                    view.loginFailure("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.loginFailure(t.getMessage());
            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        //Membuat Object SessionManager
        mSessionManager = new SessionManager(context);
        // Save Data dengan menggunakan method dari class SessionManager
        mSessionManager.createSession(loginData);
    }

    @Override
    public void checkLogin(Context context) {
        //Membuat Object SessionManager
        mSessionManager = new SessionManager(context);
        //Mengambil data KEY_IS_LOGIN lalu memasukkan ke dalam variable isLogin
        Boolean isLogin = mSessionManager.isLogin();
        //Mengecek apakah KEY_IS_LOGIN bernilai true
        if (isLogin){
            //Menyuruh view untuk melakukan perpindahan ke MainActivity
            view.isLogin();
        }
    }
}
