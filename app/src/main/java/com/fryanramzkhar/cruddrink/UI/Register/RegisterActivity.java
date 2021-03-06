package com.fryanramzkhar.cruddrink.UI.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fryanramzkhar.cruddrink.Model.LoginData;
import com.fryanramzkhar.cruddrink.R;
import com.fryanramzkhar.cruddrink.UI.Login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @BindView(R.id.edtNama)
    EditText edtNama;
    @BindView(R.id.edtAlamat)
    EditText edtAlamat;
    @BindView(R.id.edtnotelp)
    EditText edtnotelp;
    @BindView(R.id.radioLaki)
    RadioButton radioLaki;
    @BindView(R.id.radioPerempuan)
    RadioButton radioPerempuan;
    @BindView(R.id.edtusername)
    EditText edtusername;
    @BindView(R.id.edtpassword)
    TextInputEditText edtpassword;
    @BindView(R.id.edtpasswordconfirm)
    TextInputEditText edtpasswordconfirm;
    @BindView(R.id.radioAdmin)
    RadioButton radioAdmin;
    @BindView(R.id.radioUserbiasa)
    RadioButton radioUserbiasa;
    @BindView(R.id.btnregister)
    Button btnregister;

    private ProgressDialog progressDialog;
    private RegisterPresenter registerPresenter = new RegisterPresenter(this);
    private String level;
    private String jenkel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setRadio();
    }

    private void setRadio() {
        if (radioAdmin.isChecked()){
            level = "1";
        }else {
            level = "0";
        }

        if (radioLaki.isChecked()){
            jenkel = "Laki Laki";
        }else {
            jenkel = "Perempuan";
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String msg) {
        Toasty.error(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(String msg) {
        Toasty.success(this, msg, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick({R.id.radioLaki, R.id.radioPerempuan, R.id.radioAdmin, R.id.radioUserbiasa, R.id.btnregister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioLaki:
                jenkel = "Laki Laki";
                break;
            case R.id.radioPerempuan:
                jenkel = "Perempuan";
                break;
            case R.id.radioAdmin:
                level = "1";
                break;
            case R.id.radioUserbiasa:
                level = "0";
                break;
            case R.id.btnregister:

                LoginData mLoginData = new LoginData();

                mLoginData.setUsername(edtusername.getText().toString());
                mLoginData.setAlamat(edtAlamat.getText().toString());
                mLoginData.setPassword(edtpassword.getText().toString());
                mLoginData.setNo_telp(edtnotelp.getText().toString());
                mLoginData.setNama_user(edtNama.getText().toString());
                mLoginData.setJenkel(jenkel);
                mLoginData.setLevel(level);

                registerPresenter.doRegisterUser(mLoginData);
                break;
        }
    }
}
