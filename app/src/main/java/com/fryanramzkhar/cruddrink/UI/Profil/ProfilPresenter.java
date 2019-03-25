package com.fryanramzkhar.cruddrink.UI.Profil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fryanramzkhar.cruddrink.Data.Remote.ApiClient;
import com.fryanramzkhar.cruddrink.Data.Remote.ApiInterface;
import com.fryanramzkhar.cruddrink.Model.LoginData;
import com.fryanramzkhar.cruddrink.Model.LoginResponse;
import com.fryanramzkhar.cruddrink.Utils.Constant;
import com.fryanramzkhar.cruddrink.Utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilPresenter implements ProfilContract.Presenter {
    private final ProfilContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilPresenter(ProfilContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {
        view.showProgress();

        Call<LoginResponse> call = apiInterface.updateUser(Integer.valueOf(loginData.getId_user()), loginData.getNama_user(),
                loginData.getAlamat(), loginData.getJenkel(), loginData.getNo_telp());

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null){
                    if (response.body().getResult() == 1){
                        Log.i("cek", loginData.getJenkel());
                        //Setelah update ke server online, lalu update ke sharedPref
                        //membuat Objek sharedpref yang sudah ada di sessionManager
                        pref = context.getSharedPreferences(Constant.pref_name, 0);
                        //Mengubah mode SharedPref menjadi edit
                        SharedPreferences.Editor editor = pref.edit();
                        //Memasukkan data ke dalam sharedPref
                        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
                        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
                        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
                        // apply perubahan
                        editor.apply();
                        view.showSuccesUpdateUser(response.body().getMessage());
                    }else {
                        view.showSuccesUpdateUser(response.body().getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.showSuccesUpdateUser(t.getMessage());
            }
        });

        pref = context.getSharedPreferences(Constant.pref_name, 0);
        //Mengubah mode SharedPref menjadi edit
        SharedPreferences.Editor editor = pref.edit();
        //Memasukkan data ke dalam sharedPref
        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
        // apply perubahan
        editor.apply();
        view.showSuccesUpdateUser("Update Success");
    }

    @Override
    public void getDataUser(Context context) {
        pref = context.getSharedPreferences(Constant.pref_name, 0);
         LoginData loginData = new LoginData();

         loginData.setId_user(pref.getString(Constant.KEY_USER_ID,""));
         loginData.setUsername(pref.getString(Constant.KEY_USER_USERNAME,""));
         loginData.setAlamat(pref.getString(Constant.KEY_USER_ALAMAT,""));
         loginData.setNo_telp(pref.getString(Constant.KEY_USER_NOTELP,""));
         loginData.setJenkel(pref.getString(Constant.KEY_USER_JENKEL,""));

         view.showDataUser(loginData);
    }

    @Override
    public void logoutSession(Context context) {
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.logout();

    }
}
