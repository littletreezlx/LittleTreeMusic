package com.example.littletreemusic.activity.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.base.BaseFragment;
import com.example.littletreemusic.di.Component.navigation.DaggerNavLoginComponent;
import com.example.littletreemusic.di.Component.navigation.NavLoginComponent;
import com.example.littletreemusic.di.Component.navigation.NavLoginModule;
import com.example.littletreemusic.presenter.navigation.NavFMPresenter;
import com.example.littletreemusic.presenter.navigation.NavLoginContract;
import com.example.littletreemusic.presenter.navigation.NavLoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class NavLoginFragment extends BaseFragment implements NavLoginContract.INavLoginView {
//    public static final String baseurl = "http://192.168.1.104:8080/";

    @BindView(R.id.nav_login_tvtitle)
    TextView tv_Title;
    @BindView(R.id.nav_login_btnback)
    ImageButton btn_Back;
    @BindView(R.id.nav_login_edtaccount)
    EditText edt_Account;
    @BindView(R.id.nav_login_edtpassword)
    EditText edt_Password;
    @BindView(R.id.nav_login_cbremember)
    CheckBox cb_Remember;
    @BindView(R.id.nav_login_btnlogin)
    Button btn_login;
    @BindView(R.id.nav_login_btnregister)
    Button btn_register;
    @BindView(R.id.nav_login_btnalterpd)
    Button btn_alterpd;
    Unbinder unbinder;

    @Inject
    NavLoginPresenter navLoginPresenter;
    @Inject
    NavFMPresenter navFMPresenter;
    @Inject
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NavLoginComponent navLoginComponent = DaggerNavLoginComponent.builder()
                .mainActivityComponent(((MainActivity) getActivity()).getMainActivityComponent())
                .navLoginModule(new NavLoginModule(this))
                .build();
        navLoginComponent.inject(this);
        navLoginComponent.inject(navLoginPresenter);
        View view = inflater.inflate(R.layout.nav_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showLoginSucceed() {
        Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
        btn_login.setClickable(true);
        mainActivity.turnLoginToSign();

    }

    @Override
    public void showLoginFailedReason(String reason) {
        btn_login.setClickable(true);
        Toast.makeText(getActivity(), reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.nav_login_btnback, R.id.nav_login_btnlogin, R.id.nav_login_btnregister, R.id.nav_login_btnalterpd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_login_btnback:
                navFMPresenter.FromNavLogin();
                break;
            case R.id.nav_login_btnlogin:
                String account=edt_Account.getText().toString();
                String password=edt_Password.getText().toString();
                Boolean isChecked=cb_Remember.isChecked();
                navLoginPresenter.login(account,password,isChecked);
                btn_login.setClickable(false);
                break;
            case R.id.nav_login_btnregister:
                navFMPresenter.toNavRegister();
                break;
            case R.id.nav_login_btnalterpd:
                navFMPresenter.toNavAlterPD();
                break;
        }
    }

}
